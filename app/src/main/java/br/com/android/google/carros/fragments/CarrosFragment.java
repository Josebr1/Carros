package br.com.android.google.carros.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import br.com.android.google.carros.CarrosApplication;
import br.com.android.google.carros.R;
import br.com.android.google.carros.activity.CarroActivity;
import br.com.android.google.carros.adapter.CarroAdapter;
import br.com.android.google.carros.domain.Carro;
import br.com.android.google.carros.domain.CarroBD;
import br.com.android.google.carros.domain.CarroService;
import livroandroid.lib.utils.AndroidUtils;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class CarrosFragment extends BaseFragment {

    private int mTipo;
    protected RecyclerView mRecyclerView;
    private List<Carro> mCarros;
    private SwipeRefreshLayout mSwipeLayout;
    private ActionMode mActionMode;
    private Intent mShareIntent;

    // Método para instanciar esse fragment pelo tipo
    public static CarrosFragment newInstance(int tipo){
        Bundle args = new Bundle();
        args.putInt("tipo", tipo);
        CarrosFragment f = new CarrosFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            // Lê o tipo dos argumentos
            this.mTipo = getArguments().getInt("tipo");
        }
        // Registra a classe para receber eventos
        CarrosApplication.getInstance().getBus().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cancela o recebimento de eventos
        CarrosApplication.getInstance().getBus().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carros, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        // Swipe to Refresh
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeLayout.setOnRefreshListener(OnRefreshListener());
        mSwipeLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3
        );

        return view;
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener(){
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Valida se existe conexão ao fazer o gesto de Pull to Refresh
                if(AndroidUtils.isNetworkAvailable(getContext())){
                    taskCarros(true);
                }else{
                    mSwipeLayout.setRefreshing(false);
                    snack(mRecyclerView, R.string.error_conexao_indisponivel);
                }
            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskCarros(false);
    }

    private void taskCarros(boolean pullToRefresh){
        // Busca os carros: Dispara a Task
        startTask("carros", new GetCarrosTask(pullToRefresh), pullToRefresh ? R.id.swipeToRefresh : R.id.progress);
    }

    // Task para buscar os carros
    private class GetCarrosTask implements TaskListener<List<Carro>>{
        private boolean refresh;
        public GetCarrosTask(boolean refresh){
            this.refresh = refresh;
        }

        @Override
        public List<Carro> execute() throws Exception {
            // Busca os carros em background (Thread)
            return CarroService.getCarros(getContext(), mTipo, refresh);
        }

        @Override
        public void updateView(List<Carro> carros) {
            if(carros != null){
                //Salva a lista de carros no atributo da classe
                CarrosFragment.this.mCarros = carros;
                // Atualiza a view UI Thread
                mRecyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarros()));
            }
        }

        @Override
        public void onError(Exception exception) {
            // Qualquer exceção lançada no método execute vai cair aqui
            alert("Ocorreu algum erro ao buscar os dados.");
        }

        @Override
        public void onCancelled(String cod) {

        }
    }
    private CarroAdapter.CarroOnClickListener onClickCarros(){
        return new CarroAdapter.CarroOnClickListener() {
            @Override
            public void onClickCarro(View view, int idx) {
                //Toast.makeText(getContext(), "Carro: " + c.nome, Toast.LENGTH_SHORT).show();
                Carro c = mCarros.get(idx);
                if(mActionMode == null){
                    Intent intent = new Intent(getContext(), CarroActivity.class);
                    intent.putExtra("carro", Parcels.wrap(c));// Converte o objeto para Parcelable
                    startActivity(intent);
                }else{// Se a CAB está ativada
                    // Seleciona o carro
                    c.selected = !c.selected;
                    // Atualiza o título com a quantidade de carros selecionados
                    updateActionModeTitle();
                    //Redesenha a lista
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                }

            }

            @Override
            public void onLongClickCarro(View view, int idx) {
                if(mActionMode != null){
                    return;
                }

                // Liga a action bar de contexto
                mActionMode = getAppCompatActivity().startSupportActionMode(getActionModeCallback());
                Carro carro = mCarros.get(idx);
                carro.selected = true; // Seleciona o carro
                // Solicita ao Android para desenha a lista novamente
                mRecyclerView.getAdapter().notifyDataSetChanged();
                // Atualiza o título para mostrar a quantidade de carros selecionados
                updateActionModeTitle();
            }
        };
    }

    // Atualiza o título da action bar (CAB)
    private void updateActionModeTitle(){
        if(mActionMode != null){
            mActionMode.setTitle("Selecione os carros.");
            mActionMode.setSubtitle(null);
            List<Carro> selectedCarros = getSelectedCarros();
            if(selectedCarros.size() == 1){
                mActionMode.setSubtitle("1 carro selecionado");
            }else if(selectedCarros.size() > 1){
                mActionMode.setSubtitle(selectedCarros.size() + " carros selecionados");
            }
            updateShareIntent(selectedCarros);
        }
    }

    // Atualiza a share intent com os carros selecionados
    private void updateShareIntent(List<Carro> selectedCarros){
        if(mShareIntent != null){
            // Texto com os carros
            mShareIntent.putExtra(Intent.EXTRA_TEXT, "Carros: " + selectedCarros);
        }
    }

    // Retorna a lista de carros selecionados
    private List<Carro> getSelectedCarros(){
        List<Carro> list = new ArrayList<>();
        for(Carro c : mCarros){
            if(c.selected){
                list.add(c);
            }
        }
        return list;
    }

    // Método chamado quando o evento for disparado
    @Subscribe
    public void onBusAtualizarListaCarros(String refresh){
        // Recebeu o evento, atualiza a lista
        taskCarros(false);
    }

    private ActionMode.Callback getActionModeCallback(){
        return new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Infla o menu específico da action bar de contexto (CAB)
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.menu_frag_carros_context, menu);
                MenuItem shareItem = menu.findItem(R.id.action_share);
                ShareActionProvider share = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
                mShareIntent = new Intent(Intent.ACTION_SEND);
                mShareIntent.setType("text/*");
                share.setShareIntent(mShareIntent);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                List<Carro> selectedCarros = getSelectedCarros();
                if(item.getItemId() == R.id.action_remove){
                    //toast("Remover " + selectedCarros);
                    CarroBD db = new CarroBD(getContext());
                    try{
                        for(Carro c: selectedCarros){
                            db.delete(c); // Deleta o carro do banco
                            mCarros.remove(c); // Remove da lista
                        }
                    }finally {
                        db.close();
                    }
                    snack(mRecyclerView, "Carros excluídos com sucesso.");
                }else if(item.getItemId() == R.id.action_share){
                    toast("Compartilhar: " + selectedCarros);
                }
                // Encerra o action mode
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Limpa o estado
                mActionMode = null;
                //Configura todos os carros para não selecionados
                for(Carro c : mCarros){
                    c.selected = false;
                }
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        };
    }

}
