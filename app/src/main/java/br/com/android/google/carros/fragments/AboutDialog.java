package br.com.android.google.carros.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.widget.TextView;

import br.com.android.google.carros.R;
import livroandroid.lib.utils.AndroidUtils;

/**
 * Created by jose on 7/29/2016.
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AboutDialog extends DialogFragment {

    // Método utilitário para mostrar o dialog
    public static void showAbout(android.support.v4.app.FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        new AboutDialog().show(ft, "dialog_about");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Cria o HTML com o texto de sobre o aplicativo
        SpannableStringBuilder aboutBody = new SpannableStringBuilder();
        // Versão do aplicativo
        String versionName = AndroidUtils.getVersionName(getActivity());
        // Converte o texto do string.xml para html
        aboutBody.append(Html.fromHtml(getString(R.string.about_dialog_text, versionName)));
        // Infla o layout
        LayoutInflater inflate = LayoutInflater.from(getActivity());
        TextView view = (TextView) inflate.inflate(R.layout.dialog_about, null);
        view.setText(aboutBody);
        view.setMovementMethod(new LinkMovementMethod());
        //Cria o dialog customizado
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.about_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }
                )
                .create();
    }
}
