package br.com.android.google.carros.activity;

import android.os.Bundle;

import br.com.android.google.carros.R;
import br.com.android.google.carros.fragments.SiteLivroFragment;

public class SiteLivroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_livro);

        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Adiciona o fragment
        if(savedInstanceState == null){
            SiteLivroFragment frag = new SiteLivroFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
        }
    }
}
