package br.com.android.google.carros.activity;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import org.parceler.Parcels;

import br.com.android.google.carros.R;
import br.com.android.google.carros.domain.Carro;
import br.com.android.google.carros.fragments.MapaFragment;

public class MapaActivity extends BaseActivity {

    private Carro mCarro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Configura a Toolbar como a action bar
        setUpToolbar();
        mCarro = Parcels.unwrap(getIntent().getParcelableExtra("carro"));
        getSupportActionBar().setTitle(mCarro.nome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            // Adiciona o fragment no layout da activity
            MapaFragment mapaFragment = new MapaFragment();
            mapaFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragLayout, mapaFragment).commit();

           /* MapaFragment mapaFragment = new MapaFragment();
            mapaFragment.setArguments(getArguments());
            getChildFragmentManager().beginTransaction().replace(R.id.mapFragment, mapaFragment).commit();*/
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Volta para a activity CarroActivity
        // Mesmo sódigo que colocamos na classe VideoActivity
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(getActivity());
                intent.putExtra("carro", Parcels.wrap(mCarro));
                NavUtils.navigateUpTo(getActivity(), intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
