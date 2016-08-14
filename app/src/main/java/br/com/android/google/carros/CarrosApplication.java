package br.com.android.google.carros;

import android.app.Application;
import android.util.Log;

/**
 * Created by jose on 7/29/2016.
 *
 * Sempre que precisamos acessar essa classe para salvar ou ler informações globais
 * basta utilizar esta linha de código:
 * CarrosApplication app = CarrosApplication.getInstance
 *
 */
public class CarrosApplication extends Application {

    private static final String TAG = "CarrosApplication";
    private static CarrosApplication mInstance = null;

    public static CarrosApplication getInstance(){
        return mInstance; // Singleton
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "CarrosApplication.onCreate");
        // Salva a intância para termos acesso como Singleton
        mInstance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "CarrosApplication.onTerminate");
    }
}
