package br.com.android.google.carros.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.android.google.carros.R;
import br.com.android.google.carros.adapter.TabsAdapter;
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
        // Tabs + ViewPager
        setupViewPagerTabs();
        // FAB
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snack(view, "Exemplo de FAB Button");
            }
        });
    }

    // Configura as Tabs + ViewPager
    private void setupViewPagerTabs(){
        //ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new TabsAdapter(getContext(), getSupportFragmentManager()));

        //Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //Cria as tabs com o mesmo adapter utilizado pelo viewpager
        tabLayout.setupWithViewPager(viewPager);
        int cor = ContextCompat.getColor(getContext(), R.color.white);
        //Cor branca no texto (o fundo azul definido no layout)
        tabLayout.setTabTextColors(cor, cor);
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
