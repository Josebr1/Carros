package br.com.android.google.carros.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

import br.com.android.google.carros.R;
import br.com.android.google.carros.domain.Carro;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends BaseFragment implements OnMapReadyCallback {

    // Objeto que controla o Google Maps
    GoogleMap map;
    private Carro carro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        // Recupera o fragement que está no Layout
        // Utiliza o getChildFragmentManager() pois é um fragment dentro de outro
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        //Inicia o Google Maps dentro do Fragment
        mapFragment.getMapAsync(this);
        this.carro = Parcels.unwrap(getArguments().getParcelable("carro"));

        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // O método onMapReady(map) é chamado quando a inicialização do mapa estiver OK.
        this.map = map;
        boolean car = carro == null;

        if (carro != null) {
            Log.d("tag", "OK");
            // Ativa o botão para mostrar minha localização
            map.setMyLocationEnabled(true);
            // Cria o objeto LatLng com a coordenada da fabrica
            LatLng location = new LatLng(Double.parseDouble(carro.latitude), Double.parseDouble(carro.longitude));
            Log.d("Lat", "OK - " + carro.latitude);
            Log.d("Lng", "OK - " + carro.longitude);
            // Posiciona o mapa na coordenada da fábrica (zoom = 13)
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location, 13);
            // map.moveCamera(update);
            map.animateCamera(update, 10000, new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    Toast.makeText(getContext(), "Mapa centralizado.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(getContext(), "Animação cancelada.", Toast.LENGTH_SHORT).show();
                }
            });
            // Marca no local da fábrica
            map.addMarker(new MarkerOptions()
                    .title(carro.nome)
                    .snippet(carro.desc)
                    .position(location));
            // Tipo do mapa
            // (normal, satélite, terreno ou híbrido)
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }


}
