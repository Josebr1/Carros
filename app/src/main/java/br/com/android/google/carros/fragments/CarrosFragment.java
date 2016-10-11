package br.com.android.google.carros.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;

import br.com.android.google.carros.R;
import br.com.android.google.carros.activity.CarroActivity;
import br.com.android.google.carros.adapter.CarroAdapter;
import br.com.android.google.carros.domain.Carro;
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

    private void taskCarros(boolean pullToResfresh){
        // Busca os carros: Dispara a Task
        startTask("carros", new GetCarrosTask(pullToResfresh), pullToResfresh ? R.id.swipeToRefresh : R.id.progress);
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
                Intent intent = new Intent(getContext(), CarroActivity.class);
                intent.putExtra("carro", Parcels.wrap(c));// Converte o objeto para Parcelable
                startActivity(intent);
            }
        };
    }





}
