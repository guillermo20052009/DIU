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
    int[] maximo = {20,80,15,5};
    DoubleProperty numeroDobles = new SimpleDoubleProperty(maximo[1]);
    DoubleProperty numeroDoblesInd = new SimpleDoubleProperty(maximo[0]);
    DoubleProperty numeroJSuite = new SimpleDoubleProperty(maximo[2]);
    DoubleProperty numeroSuite = new SimpleDoubleProperty(maximo[3]);

    // Constructor vacío para inicializar
    public ReservaModelo() {
    }

    public void inicializarPropiedades() {
        numeroDobles.set(maximo[1]);
        numeroDoblesInd.set(maximo[0]);
        numeroJSuite.set(maximo[2]);
        numeroSuite.set(maximo[3]);
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

    public void contarOcupadas() {
        inicializarPropiedades();
        int[] actuales = reservaRepository.countActuales();
        for (int i = 0; i < actuales.length; i++) {
            System.out.println(actuales[i]);
        }
        numeroDoblesInd.set(actuales[1]);
        numeroDobles.set(actuales[0]);
        numeroJSuite.set(actuales[2]);
        numeroSuite.set(actuales[3]);
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

    public double getNumeroDobles() {
        return numeroDobles.get();
    }
    public double getNumeroDoblesInd() {
        return numeroDoblesInd.get();
    }
    public double getNumeroJSuite() {
        return numeroJSuite.get();
    }
    public double getNumeroSuite() {
        return numeroSuite.get();
    }

    public int getMaximo(tipo_habitacion tipoHabitacion) {
        return tipoHabitacion == tipo_habitacion.DOBLE ? maximo[1] :
                tipoHabitacion == tipo_habitacion.DOBLE_INDIVIDUAL ? maximo[0] :
                        tipoHabitacion == tipo_habitacion.JUNIOR_SUITE ? maximo[2] :
                                tipoHabitacion == tipo_habitacion.SUITE ? maximo[3] :
                                        -1; // Devuelve -1 si no coincide ningún tipo
    }
    public void setNumeroSuite(double valor) {
        numeroSuite.set(valor);
    }
    public void decrementar(tipo_habitacion tipoHabitacion) throws ExcepcionReserva {
        ajustarValor(-1,tipoHabitacion);
    }

    public void incrementar(tipo_habitacion tipoHabitacion){
        ajustarValor(1,tipoHabitacion);
    }
    public void ajustarValor(double valor,tipo_habitacion tipoHabitacion) throws ExcepcionReserva {
        switch (tipoHabitacion) {
            case DOBLE:
                numeroDobles.set(numeroDobles.get() + valor);
                break;

            case DOBLE_INDIVIDUAL:
                numeroDoblesInd.set(numeroDoblesInd.get() + valor);
                break;

            case JUNIOR_SUITE:
                numeroJSuite.set(numeroJSuite.get() + valor);
                break;

            case SUITE:
                numeroSuite.set(numeroSuite.get() + valor);
                break;

            default:
                throw new IllegalArgumentException("Tipo de habitación no reconocido: " + tipoHabitacion);
        }
    }

    public int[] contarReservasPorMes(String tipoHabitacion) throws ExcepcionReserva {
        return reservaRepository.countMonthsByType(tipoHabitacion);
    }
    public int contarReservasFecha(Reserva reserva) throws ExcepcionReserva {
        return reservaRepository.countConcretasByType(ReservaUtil.convertirReservaVO(reserva));
    }

}
