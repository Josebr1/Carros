package br.com.android.google.carros.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.List;

import br.com.android.google.carros.R;
import br.com.android.google.carros.activity.CarroActivity;
import br.com.android.google.carros.adapter.CarroAdapter;
import br.com.android.google.carros.domain.Carro;
import br.com.android.google.carros.domain.CarroService;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class CarrosFragment extends BaseFragment {

    private int mTipo;
    protected RecyclerView mRecyclerView;
    private List<Carro> mCarros;

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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskCarros();
    }

    private void taskCarros(){
        // Busca os carros pelo tipo
        this.mCarros = CarroService.getCarros(getContext(), mTipo);
        //Atualiza a lista
        mRecyclerView.setAdapter(new CarroAdapter(getContext(), mCarros, onClickCarros()));
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
