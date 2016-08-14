package br.com.android.google.carros.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import br.com.android.google.carros.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SiteLivroFragment extends BaseFragment {

    private static final String URL_SOBRE = "http://www.livroandroid.com.br/index.php";
    private WebView mWebView;
    private ProgressBar mProgress;
    protected SwipeRefreshLayout mSwipeLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_site_livro, container, false);

        // WebView
        mWebView = (WebView) view.findViewById(R.id.webView);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
        setWebViewClient(mWebView);
        // Carrega a página
        mWebView.loadUrl(URL_SOBRE);

        /*
        //JavaScript
        configJavaScript();
        */

        // Swipe to Refresh
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeLayout.setOnRefreshListener(onRefreshListener());
        //Cores da animação
        mSwipeLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3
        );
        return view;
    }


    // Swipe to Refresh
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener(){
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.reload();// Atualiza a página
            }
        };
    }

    // WebView
    private void setWebViewClient(WebView webView){
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Liga o progress
                mProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Desliga o progress
                mProgress.setVisibility(View.INVISIBLE);
                // Termina a animação do Swipe to Refresh
                mSwipeLayout.setRefreshing(false);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("livroandroid", "webview: " + url);
                if(url != null && url.endsWith("index.php")){
                    AboutDialog.showAbout(getFragmentManager());
                    // Retorna true para informar que interptamos o evento
                    return true;

                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    /*

    Código que não executa falta o código JavaScript

    private void configJavaScript(){
        WebSettings settings = webView.getSettings();
        // Ativa o JavaScript na página
        settings.setJavaScriptEnabled(true);
        // Publica a intergace para o JavaScript
        webView.addJavascriptInterface(new LivroAndroidInterface(), "livroAndroid");
    }

    class LivroAndroidInterface{
        @JavascriptInterface
        public void sobre(){
            toast("Clicou na figura do livro");
        }
    }*/

}
