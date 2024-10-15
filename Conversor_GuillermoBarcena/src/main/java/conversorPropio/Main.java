package conversorPropio;

import Modelo.MonedaVO;
import Modelo.ExcepcionMoneda;
import Modelo.repository.impl.MonedaRepositoryImpl;


public class Main {
    public Main() {}


    public static void main(String[] args) {
        try {
            MonedaRepositoryImpl monedaRepository = new MonedaRepositoryImpl();
            MonedaVO monedaPrueba = new MonedaVO("Prueba", 1.2F);
            monedaRepository.addMoneda(monedaPrueba);
        } catch (ExcepcionMoneda e) {

        }
    }

}
