package br.com.android.google.carros.activity.prefs;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import br.com.android.google.carros.R;
import br.com.android.google.carros.activity.BaseActivity;

public class ConfiguracoesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Adiciona o fragment de configurações
        if(savedInstanceState == null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, new PrefsFragment());
            ft.commit();
        }
    }

    //Fragment que carrega o layout com as configurações
    public static class PrefsFragment extends PreferenceFragmentCompat{

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            // Carrega as configurações
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
