package gestionhotel.util;
import gestionhotel.Reserva;
import gestionhotel.modelo.Regimen;
import gestionhotel.modelo.ReservaVO;
import gestionhotel.modelo.tipo_habitacion;

import java.util.ArrayList;

// Esta clase se encargará de hacer las transformaciones pertinentes entre Reserva y ReservaVO
public class ReservaUtil {

    // Convierte la lista que recogemos de la base de datos a Reserva
    public static ArrayList<Reserva> convertirVOaReserva(ArrayList<ReservaVO> reservas) {
        ArrayList<Reserva> reservasConvertida = new ArrayList<>();

        for (ReservaVO reservaVO : reservas) {
            reservasConvertida.add(new Reserva(
                    reservaVO.getIdReserva(),
                    reservaVO.getFechaLlegada(),
                    reservaVO.getFechaSalida(),
                    tipo_habitacion.valueOf(reservaVO.getTipo_habitacion().replaceAll("\\s", "_")), // Convertimos el String a tipo_habitacion
                    reservaVO.isFumador(),
                    Regimen.valueOf(reservaVO.getRegimen().replaceAll("\\s", "_")), // Convertimos el String a Regimen
                    reservaVO.getNumero_habitaciones(),
                    reservaVO.getDni_cliente()
            ));
        }
        return reservasConvertida;
    }

    // Convierte una instancia de Reserva a ReservaVO
    public static ReservaVO convertirReservaVO(Reserva reserva) {
        return new ReservaVO(reserva.getIdReserva(),reserva.getNumero_habitaciones(),String.valueOf(reserva.getRegimen()),reserva.isFumador(),String.valueOf(reserva.getTipoHabitacion()),reserva.getFechaSalida(),reserva.getFechaLlegada(),reserva.getDni_cliente());
    }

    // Convierte una instancia de ResevaVO a Reserva
    public static Reserva convertirReserva(ReservaVO reservaVO) {
        return new Reserva(
                reservaVO.getIdReserva(),
                reservaVO.getFechaLlegada(),
                reservaVO.getFechaSalida(),
                tipo_habitacion.valueOf(reservaVO.getTipo_habitacion().replaceAll("\\s", "_")), // Convertimos el String a tipo_habitacion
                reservaVO.isFumador(),
                Regimen.valueOf(reservaVO.getRegimen().replaceAll("\\s", "_")), // Convertimos el String a Regimen
                reservaVO.getNumero_habitaciones(),
                reservaVO.getDni_cliente()
        );
    }
}
