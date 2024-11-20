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

public class ReservaModelo {
    ReservaRepository reservaRepository;
    public ReservaModelo() {
    }

    public void setPersonaRepository(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }
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
    public void eliminarReserva(int id,String dni) throws ExcepcionPersona {
        reservaRepository.deleteReserva(id);
    }
    public void actualizarPersona(Persona persona) throws ExcepcionPersona {
    }
    public void addReserva(Reserva reserva) throws ExcepcionPersona {
       reservaRepository.addReserva(ReservaUtil.convertirReservaVO(reserva));
    }
    public int getLastId() throws ExcepcionReserva {
        return reservaRepository.lastId();
    }
}
