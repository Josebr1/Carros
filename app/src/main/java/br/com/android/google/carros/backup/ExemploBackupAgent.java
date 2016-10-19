package br.com.android.google.carros.backup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.SharedPreferencesBackupHelper;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.IOException;

import livroandroid.lib.utils.Prefs;

/**
 * Created by jose on 18/10/2016.
 */

public class ExemploBackupAgent extends BackupAgentHelper {
    @Override
    public void onCreate() {
        super.onCreate();
        // Cria um helper. Fará backup dos dados utilizando a chave Prefs.PREF_ID.
        SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(this, Prefs.PREF_ID);
        // Adiciona o helper ao agente de backups
        addHelper("livroAndroid", helper);
    }

    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data, ParcelFileDescriptor newState) throws IOException {
        super.onBackup(oldState, data, newState);
        Log.d("backup", "Backup efetuada com sucesso.");
    }

    @Override
    public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState) throws IOException {
        super.onRestore(data, appVersionCode, newState);
        Log.d("backup", "Backup restaurado com sucesso.");
    }
}
