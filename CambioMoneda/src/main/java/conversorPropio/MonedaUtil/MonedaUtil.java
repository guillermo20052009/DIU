package conversorPropio.MonedaUtil;

import Modelo.MonedaVO;
import conversorPropio.Moneda;

import java.util.ArrayList;

public class MonedaUtil {
    static ArrayList<Moneda> monedas1 = new ArrayList<>();

    public MonedaUtil(){}

    public static ArrayList<Moneda> convertir(ArrayList<MonedaVO> monedas) {
        for (MonedaVO moneda : monedas) {
            monedas1.add(new Moneda(moneda.getMultiplicador(),moneda.getNombre()));
        }
        return monedas1;
    }
}
