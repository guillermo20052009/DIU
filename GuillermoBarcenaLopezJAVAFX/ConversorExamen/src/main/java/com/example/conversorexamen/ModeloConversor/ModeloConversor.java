package com.example.conversorexamen.ModeloConversor;

import Modelo.ExcepcionMoneda;
import Modelo.MonedaVO;
import Modelo.repository.MonedaRepository;
import com.example.conversorexamen.Moneda;
import com.example.conversorexamen.util.MonedaUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class ModeloConversor {
    MonedaRepository repository;
    IntegerProperty cantidad;

    public ModeloConversor() {
    }

    public void setRepository(MonedaRepository repository) {
        this.repository = repository;
    }
    public ArrayList<Moneda> obtenerListaMonedas() throws ExcepcionMoneda {
        cantidad = new SimpleIntegerProperty(repository.ObtenerListaMonedas().size());
        return MonedaUtil.ToMoneda(repository.ObtenerListaMonedas());
    }
    public String obtenerValorMonedaEuros(Moneda moneda,String valor) throws ExcepcionMoneda  {
        Float multiplicador= moneda.getMultiplicador();
        Float valorConvertido= Float.parseFloat(valor);
        return String.valueOf(valorConvertido*multiplicador);
    }
    public String obtenerValorMonedaOtra(Moneda moneda,String valor) throws ExcepcionMoneda  {
        Float multiplicador= moneda.getMultiplicador();
        Float valorConvertido= Float.parseFloat(valor);
        return String.valueOf(valorConvertido*(2-multiplicador));
    }
    public void EliminarMoneda(Integer id) throws ExcepcionMoneda {
        repository.deleteMoneda(id);
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public IntegerProperty cantidadProperty() {
        return cantidad;
    }
    public void decrementarCantidad() {
        cantidad.set(cantidad.get() - 1);
    }

}
