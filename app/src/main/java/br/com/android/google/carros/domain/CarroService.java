package br.com.android.google.carros.domain;

import android.content.Context;
import android.icu.util.RangeValueIterator;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.android.google.carros.R;
import livroandroid.lib.utils.FileUtils;
import livroandroid.lib.utils.XMLUtils;

/**
 * Created by jose on 07/08/2016.
 */
public class CarroService {

    private static final boolean LOG_ON = false;
    private static final String TAG = "CarroService";

    public static List<Carro> getCarros(Context context, int tipo) throws IOException{
        String json = readFile(context, tipo);
        List<Carro> carros = parserJSON(context, json);

        return carros;
    }

    //Faz a leitura do arquivo que está na pasta /res/raw
    private static String readFile(Context context, int tipo) throws IOException{
        if(tipo == R.string.classicos){
            return FileUtils.readRawFileString(context, R.raw.carros_classicos, "UTF-8");
        }else if(tipo == R.string.esportivos){
            return FileUtils.readRawFileString(context, R.raw.carros_esportivos, "UTF-8");
        }

        return FileUtils.readRawFileString(context, R.raw.carros_luxo, "UTF-8");
    }

    //Faz o parser do XML e cria a lista de carros
    private static List<Carro> parserJSON(Context context, String json) throws IOException{
        List<Carro> carros = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("carros");
            JSONArray jsonCarros = obj.getJSONArray("carro");
            //Insere cada carro na lista
            for(int i = 0; i < jsonCarros.length(); i++){
                JSONObject jsonCarro = jsonCarros.getJSONObject(i);
                Carro c = new Carro();
                // Lê as informações de cada carro
                c.nome = jsonCarro.optString("nome");
                c.desc = jsonCarro.optString("desc");
                c.urlFoto = jsonCarro.optString("url_foto");
                c.urlInfo = jsonCarro.optString("url_info");
                c.urlVideo = jsonCarro.optString("url_video");
                c.latitude = jsonCarro.optString("latitude");
                c.longitude = jsonCarro.optString("longitude");
                if(LOG_ON){
                    Log.d(TAG, "Carro " + c.nome + " > " + c.urlVideo);
                }
                carros.add(c);
            }

            if(LOG_ON){
                Log.d(TAG, carros.size() + " encontrados");
            }
        } catch (JSONException e) {
            throw new IOException(e.getMessage(), e);
        }
        return carros;
    }
}
