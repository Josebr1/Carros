package br.com.android.google.carros.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.com.android.google.carros.R;
import br.com.android.google.carros.fragments.AboutDialog;
import br.com.android.google.carros.fragments.CarrosFragment;
import br.com.android.google.carros.fragments.CarrosTabFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ativando a Toolbar
        setUpToolbar();
        // Inicializa a NavigationDrawer
        setupNavDrawer();
        // Inicializa o layout principal com o fragment dos carros
        replaceFragment(new CarrosTabFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_about){
            AboutDialog.showAbout(getSupportFragmentManager());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
