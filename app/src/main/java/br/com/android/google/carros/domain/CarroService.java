package br.com.android.google.carros.domain;

import android.content.Context;
import android.icu.util.RangeValueIterator;
import android.util.Log;

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
        String xml = readFile(context, tipo);
        List<Carro> carros = parserXML(context, xml);

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
    private static List<Carro> parserXML(Context context, String xml){
        List<Carro> carros = new ArrayList<>();
        Element root = XMLUtils.getRoot(xml, "UTF-8");
        //Lê todas as tags <carros>
        List<Node> nodeCarros = XMLUtils.getChildren(root, "carro");
        //Insere cada carro na lista
        for(Node node : nodeCarros){
            Carro c = new Carro();
            // Lê as informações de cada carro
            c.nome = XMLUtils.getText(node, "nome");
            c.desc = XMLUtils.getText(node, "desc");
            c.urlFoto = XMLUtils.getText(node, "url_foto");
            c.urlInfo = XMLUtils.getText(node, "url_info");
            c.urlVideo = XMLUtils.getText(node, "url_video");
            c.latitude = XMLUtils.getText(node, "latitude");
            c.longitude = XMLUtils.getText(node, "longitude");
            if(LOG_ON){
                Log.d(TAG, "Carro " + c.nome + " > " + c.urlVideo);
            }
            carros.add(c);
        }

        if(LOG_ON){
            Log.d(TAG, carros.size() + " encontrados");
        }

        return carros;
    }
}
