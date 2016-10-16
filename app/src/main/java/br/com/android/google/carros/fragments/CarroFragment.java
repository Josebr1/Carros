package br.com.android.google.carros.fragments;


import android.media.Image;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import br.com.android.google.carros.R;
import br.com.android.google.carros.domain.Carro;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarroFragment extends BaseFragment {

    private Carro mCarro;

    public CarroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carro, container, false);
        mCarro = Parcels.unwrap(getArguments().getParcelable("carro"));

        setHasOptionsMenu(true); // Precisa informar o Android que este fragment contém menu

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Atualiza a view do fragment com os dados do carro
        setTextString(R.id.tDesc, mCarro.desc);
        final ImageView imgView = (ImageView) getView().findViewById(R.id.img);
        Picasso.with(getContext()).load(mCarro.urlFoto).fit().into(imgView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_flag_carro, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_edit){
            toast("Editar: " + mCarro.nome);
            return true;
        }else if(item.getItemId() == R.id.action_delete){
            toast("Delete: " + mCarro.nome);
            return true;
        }else if(item.getItemId() == R.id.action_share){
            toast("Compartilhar");
            return true;
        }else if(item.getItemId() == R.id.action_maps){
            toast("Mapa");
            return true;
        }else if(item.getItemId() == R.id.action_video){
            toast("Vídeo");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
