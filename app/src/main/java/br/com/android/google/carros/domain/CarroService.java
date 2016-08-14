package br.com.android.google.carros.domain;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 07/08/2016.
 */
public class CarroService {

    public static List<Carro> getCarros(Context context, int tipo){
        String tipoString = context.getString(tipo);
        List<Carro> carros = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            Carro c = new Carro();
            c.nome = "Carro " + tipoString + ": " + i; //Nome dinâmico conforme o tipo
            c.desc = "Desc " + i;
            c.urlFoto = "http://www.livroandroid.com.br/livro/carros/esportivos/Ferrari_FF.png";
            carros.add(c);
        }
        return carros;
    }
}
