package gestionhotel.util;
import gestionhotel.Reserva;
import gestionhotel.modelo.Regimen;
import gestionhotel.modelo.ReservaVO;
import gestionhotel.modelo.tipo_habitacion;

import java.util.ArrayList;

public class ReservaUtil {

    public static ArrayList<Reserva> convertirVOaReserva(ArrayList<ReservaVO> reservas) {
        ArrayList<Reserva> reservasConvertida = new ArrayList<>();

        for (ReservaVO reservaVO : reservas) {
            reservasConvertida.add(new Reserva(
                    reservaVO.getIdReserva(),
                    reservaVO.getFechaLlegada(),
                    reservaVO.getFechaSalida(),
                    tipo_habitacion.valueOf(reservaVO.getTipo_habitacion().replaceAll("\\s", "")), // Convertimos el String a tipo_habitacion
                    reservaVO.isFumador(),
                    Regimen.valueOf(reservaVO.getRegimen().replaceAll("\\s", "")), // Convertimos el String a Regimen
                    reservaVO.getNumero_habitaciones(),
                    reservaVO.getDni_cliente()
            ));
        }
        return reservasConvertida;
    }
}
