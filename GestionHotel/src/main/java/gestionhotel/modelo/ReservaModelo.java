package gestionhotel.modelo;

import gestionhotel.Persona;
import gestionhotel.Reserva;
import gestionhotel.modelo.repository.ExcepcionPersona;
import gestionhotel.modelo.repository.ExcepcionReserva;
import gestionhotel.modelo.repository.PersonaRepository;
import gestionhotel.modelo.repository.ReservaRepository;
import gestionhotel.util.PersonaUtil;
import gestionhotel.util.ReservaUtil;

import java.util.ArrayList;
import java.util.Iterator;

// Clase usada para toda la logica de negocio y la manipulacion de la base de datos
public class ReservaModelo {
    ReservaRepository reservaRepository;

    // Constructor vacio para inicializar
    public ReservaModelo() {
    }

    // Inyectar el repository correspondiente
    public void setPersonaRepository(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    //Obtener los registros de la base de datos
    public ArrayList<Reserva> obtenerListaReserva(String dni) throws ExcepcionReserva {
        ArrayList<ReservaVO> reservas = new ArrayList<>();
        reservas=reservaRepository.ObtenerListaReservas();
        Iterator<ReservaVO> iterator = reservas.iterator();
        while (iterator.hasNext()) {
            ReservaVO reservaVO = iterator.next();
            if (!reservaVO.getDni_cliente().equals(dni)) {
                iterator.remove();
            }
        }
        return ReservaUtil.convertirVOaReserva(reservas);
    }

    // Eliminar de la base de datos
    public void eliminarReserva(int id,String dni) throws ExcepcionReserva {
        reservaRepository.deleteReserva(id);
    }
    //Actualizar registro en la base de datos
    public void actualizar(Reserva reserva) throws ExcepcionReserva {
        reservaRepository.editReserva(ReservaUtil.convertirReservaVO(reserva));
    }
    // Añadir registro en la base de datos
    public void addReserva(Reserva reserva) throws ExcepcionReserva {
       reservaRepository.addReserva(ReservaUtil.convertirReservaVO(reserva));
    }
    //Obtener el ultimo ID de la base de datos
    public int getLastId() throws ExcepcionReserva {
        return reservaRepository.lastId();
    }
}
