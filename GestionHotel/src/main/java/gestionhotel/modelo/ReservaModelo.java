package gestionhotel.modelo;

import gestionhotel.Persona;
import gestionhotel.Reserva;
import gestionhotel.modelo.repository.ExcepcionPersona;
import gestionhotel.modelo.repository.ExcepcionReserva;
import gestionhotel.modelo.repository.PersonaRepository;
import gestionhotel.modelo.repository.ReservaRepository;
import gestionhotel.util.PersonaUtil;
import gestionhotel.util.ReservaUtil;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;
import java.util.Iterator;


// Clase usada para toda la lógica de negocio y la manipulación de la base de datos
public class ReservaModelo {
    ReservaRepository reservaRepository;
    DoubleProperty numeroDobles = new SimpleDoubleProperty(50);
    DoubleProperty numeroDoblesInd = new SimpleDoubleProperty(50);
    DoubleProperty numeroJSuite = new SimpleDoubleProperty(50);
    DoubleProperty numeroSuite = new SimpleDoubleProperty(50);

    // Constructor vacío para inicializar
    public ReservaModelo() {
    }

    // Inyectar el repository correspondiente
    public void setReservaRepository(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    // Obtener los registros de la base de datos
    public ArrayList<Reserva> obtenerListaReserva(String dni) throws ExcepcionReserva {
        ArrayList<ReservaVO> reservas = new ArrayList<>();
        reservas = reservaRepository.ObtenerListaReservas();
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
    public void eliminarReserva(int id, String dni) throws ExcepcionReserva {
        reservaRepository.deleteReserva(id);
    }

    // Actualizar registro en la base de datos
    public void actualizar(Reserva reserva) throws ExcepcionReserva {
        reservaRepository.editReserva(ReservaUtil.convertirReservaVO(reserva));
    }

    // Añadir registro en la base de datos
    public void addReserva(Reserva reserva) throws ExcepcionReserva {
        reservaRepository.addReserva(ReservaUtil.convertirReservaVO(reserva));
        java.time.LocalDate fechaActual = java.time.LocalDate.now();
        // Comprobación de fechas
        if (reserva.getFechaLlegada().toLocalDate().isEqual(fechaActual)) {
            incrementar(reserva.getTipoHabitacion());
        }
    }


    // Obtener el último ID de la base de datos
    public int getLastId() throws ExcepcionReserva {
        return reservaRepository.lastId();
    }

    public void contarOcupadas (){
            double ocupadas = reservaRepository.countDobles();
            numeroDobles.set(numeroDobles.get() - ocupadas);

            ocupadas = reservaRepository.countDoblesInd();
            numeroDoblesInd.set(numeroDoblesInd.get() - ocupadas);

            ocupadas = reservaRepository.countJSuite();
            numeroJSuite.set(numeroJSuite.get() - ocupadas);

            ocupadas = reservaRepository.countSuite();
            numeroSuite.set(numeroSuite.get() - ocupadas);
    }

    // Getters para las propiedades observables
    public DoubleProperty numeroDoblesProperty() {
        return numeroDobles;
    }

    public DoubleProperty numeroDoblesIndProperty() {
        return numeroDoblesInd;
    }

    public DoubleProperty numeroJSuiteProperty() {
        return numeroJSuite;
    }

    public DoubleProperty numeroSuiteProperty() {
        return numeroSuite;
    }


    public void setNumeroSuite(double valor) {
        numeroSuite.set(valor);
    }
    public void decrementar(tipo_habitacion tipoHabitacion) throws ExcepcionReserva {
        switch (tipoHabitacion) {
            case DOBLE:
                numeroDobles.set(numeroDobles.get() + 1);
                break;

            case DOBLE_INDIVIDUAL:
                numeroDoblesInd.set(numeroDoblesInd.get() + 1);
                break;

            case JUNIOR_SUITE:
                numeroJSuite.set(numeroJSuite.get() + 1);
                break;

            case SUITE:
                numeroSuite.set(numeroSuite.get() + 1);
                break;

            default:
                throw new IllegalArgumentException("Tipo de habitación no reconocido: " + tipoHabitacion);
        }
    }

    public void incrementar(tipo_habitacion tipoHabitacion){
        switch (tipoHabitacion) {
            case DOBLE:
                numeroDobles.set(numeroDobles.get() - 1);
                break;

            case DOBLE_INDIVIDUAL:
                numeroDoblesInd.set(numeroDoblesInd.get() - 1);
                break;

            case JUNIOR_SUITE:
                numeroJSuite.set(numeroJSuite.get() - 1);
                break;

            case SUITE:
                numeroSuite.set(numeroSuite.get() - 1);
                break;

            default:
                throw new IllegalArgumentException("Tipo de habitación no reconocido: " + tipoHabitacion);
        }
    }
    public int[] contarReservasPorMes(String tipoHabitacion) throws ExcepcionReserva {
        return reservaRepository.countMonthsByType(tipoHabitacion);
    }

}
