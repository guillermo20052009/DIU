package conversorPropio.modelo;

import Modelo.ExcepcionMoneda;
import Modelo.MonedaVO;
import Modelo.repository.MonedaRepository;
import conversorPropio.Moneda;
import conversorPropio.MonedaUtil.MonedaUtil;
import eu.hansolo.toolbox.properties.FloatProperty;
import eu.hansolo.toolbox.properties.IntegerProperty;

import java.util.ArrayList;

public class ConversorModelo {
    MonedaRepository monedaRepository;

    public ConversorModelo() {
    }

    public void SetConversorModelo(MonedaRepository impl) throws ExcepcionMoneda {
        this.monedaRepository=impl;
    }
    public ArrayList<Moneda> setMoneda() throws ExcepcionMoneda {
        ArrayList<MonedaVO> monedaVOs = this.monedaRepository.ObtenerListaMonedas();
        return MonedaUtil.convertir(monedaVOs);
    }

    public float conversor(Float euros,ArrayList<Moneda> monedas) throws ExcepcionMoneda {
        return (euros*monedas.getFirst().getmultiplicador());
     }

}
