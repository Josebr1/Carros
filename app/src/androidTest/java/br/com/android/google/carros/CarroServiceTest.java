package br.com.android.google.carros;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.util.List;

import br.com.android.google.carros.domain.Carro;
import br.com.android.google.carros.domain.CarroService;

/**
 * Created by jose on 21/08/2016.
 */
public class CarroServiceTest extends AndroidTestCase{
    public void testGetCarros() throws IOException{
        List<Carro> carros = CarroService.getCarros(getContext(), R.string.esportivos);
        assertNotNull(carros);
        //Precisa retornar dez carros esportivos
        assertTrue(carros.size() == 10);
        //Valida as informações do 1° carro
        Carro c0 = carros.get(0);
        assertEquals("Ferrari FF", c0.nome);
        assertEquals("44.532218", c0.latitude);
        assertEquals("10.864019", c0.longitude);
        //Valida as informações do último carro
        Carro c9 = carros.get(9);
        assertEquals("MERCEDES-BENZ C63 AMG", c9.nome);
        assertEquals("-23.564224", c9.latitude);
        assertEquals("-46.653156", c9.longitude);
    }

}
