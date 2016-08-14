package br.com.android.google.carros.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.android.google.carros.R;
import br.com.android.google.carros.fragments.CarrosFragment;
import br.com.android.google.carros.fragments.CarrosTabFragment;
import br.com.android.google.carros.fragments.SiteLivroFragment;
import livroandroid.lib.view.RoundedImageView;

/**
 * Created by jose on 7/29/2016.
 *
 */
public class BaseActivity extends livroandroid.lib.activity.BaseActivity {

    protected DrawerLayout mDrawerLayout;

    //Para ativar a Toolbar em todas as classe filha de BaseActivity
    protected void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

    // Configura o nav drawer
    protected void setupNavDrawer(){
        // Drawer Layout
        final ActionBar actionBar = getSupportActionBar();
        // Ícone do menu Nav Drawer
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView != null && mDrawerLayout != null){
            // Atualiza a imagem e os texto do header
            setNavViewValues(navigationView, R.string.nav_drawer_username,
                    R.string.nav_drawer_email, R.mipmap.ic_launcher);
            // Trata o evento de clique do menu
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    // Seleciona a linha
                    item.setChecked(true);
                    // Fecha o menu
                    mDrawerLayout.closeDrawers();
                    // Trata o evento do menu
                    onNavDrawerItemSelected(item);
                    return true;
                }
            });

        }
    }

    // Atualiza os dados do header do Navigation Viewpublic
    private static void setNavViewValues(NavigationView navView, int nome, int email, int img){
        View headerView = navView.getHeaderView(0);
        TextView tNome = (TextView) headerView.findViewById(R.id.tUserName);
        TextView tEmail = (TextView) headerView.findViewById(R.id.tUserEmail);
        RoundedImageView imgView = (RoundedImageView) headerView.findViewById(R.id.imgUserPhoto);
        tNome.setText(nome);
        tEmail.setText(email);
        imgView.setImageResource(img);
    }

    // Trata o evento do menu lateral
    private void onNavDrawerItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_item_carros_todos:
                // Nada aqui pois somente a MainActivity possui menu lateral
                break;
            case R.id.nav_item_carros_classicos:
                Intent intent = new Intent(getContext(), CarrosActivity.class);
                intent.putExtra("tipo", R.string.classicos);
                startActivity(intent);
                break;
            case R.id.nav_item_carros_esportivos:
                intent = new Intent(getContext(), CarrosActivity.class);
                intent.putExtra("tipo", R.string.esportivos);
                startActivity(intent);
                break;
            case R.id.nav_item_carros_luxo:
                intent = new Intent(getContext(), CarrosActivity.class);
                intent.putExtra("tipo", R.string.luxo);
                startActivity(intent);
                break;
            case R.id.nav_item_site_livro:
                startActivity(new Intent(getContext(), SiteLivroActivity.class));
                break;
            case R.id.nav_item_settings:
                toast("Clicou em configurações");
                break;
        }
    }

    //  Adiciona o fragment ao centro da tela
    protected void replaceFragment(Fragment fragment){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                // Trata o clique no botão que abre o menu
                if(mDrawerLayout != null){
                    openDrawer();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    // Abre o menu lateral
    protected void openDrawer(){
        if(mDrawerLayout != null) {mDrawerLayout.openDrawer(GravityCompat.START);}
    }

    //Fecha o menu lateral
    protected void closeDrawer(){
        if(mDrawerLayout != null) {mDrawerLayout.closeDrawer(GravityCompat.START);}
    }
}
