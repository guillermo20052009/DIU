package com.example.conversorexamen.util;

import Modelo.MonedaVO;
import com.example.conversorexamen.Moneda;

import java.util.ArrayList;

public class MonedaUtil {

    public static ArrayList<Moneda> ToMoneda(ArrayList<MonedaVO> lista) {
        if (lista == null) {
            return null;
        } else {
            ArrayList<Moneda> personas = new ArrayList<>();
            for (MonedaVO mon : lista) {
                personas.add(new Moneda(mon.getNombre(),mon.getCodigo(), mon.getMultiplicador()));
            }
            return personas;
        }
    }
}
