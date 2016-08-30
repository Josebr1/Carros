package br.com.android.google.carros.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jose on 29/08/2016.
 */
public class PrefsUtils {
    // Verifica se o usuário mercou o checkbox de Push ON nas configurações
    public static boolean isCheckPushOn(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("PREF_CHECK_PUSH", false);
    }
}
