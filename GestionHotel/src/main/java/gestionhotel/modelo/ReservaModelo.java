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

// Clase usada para toda la logica de negocio y la manipulacion de la base de datos
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
    }

    // Obtener el último ID de la base de datos
    public int getLastId() throws ExcepcionReserva {
        return reservaRepository.lastId();
    }

    // Contar habitaciones Dobles ocupadas actualmente
    public void contarDoblesOcupadas() throws ExcepcionReserva {
        double ocupadas = reservaRepository.countDobles();
        numeroDobles.set(numeroDobles.get() - ocupadas);
    }

    // Contar habitaciones Dobles Individuales ocupadas actualmente
    public void contarDoblesIndOcupadas() throws ExcepcionReserva {
        double ocupadas = reservaRepository.countDoblesInd();
        numeroDoblesInd.set(numeroDoblesInd.get() - ocupadas);
    }

    // Contar Junior Suites ocupadas actualmente
    public void contarJSuitesOcupadas() throws ExcepcionReserva {
        double ocupadas = reservaRepository.countJSuite();
        numeroJSuite.set(numeroJSuite.get() - ocupadas);
    }

    // Contar Suites ocupadas actualmente
    public void contarSuitesOcupadas() throws ExcepcionReserva {
        double ocupadas = reservaRepository.countSuite();
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

    // Métodos para obtener y establecer los valores (opcional)
    public double getNumeroDobles() {
        return numeroDobles.get();
    }

    public void setNumeroDobles(double valor) {
        numeroDobles.set(valor);
    }

    public double getNumeroDoblesInd() {
        return numeroDoblesInd.get();
    }

    public void setNumeroDoblesInd(double valor) {
        numeroDoblesInd.set(valor);
    }

    public double getNumeroJSuite() {
        return numeroJSuite.get();
    }

    public void setNumeroJSuite(double valor) {
        numeroJSuite.set(valor);
    }

    public double getNumeroSuite() {
        return numeroSuite.get();
    }

    public void setNumeroSuite(double valor) {
        numeroSuite.set(valor);
    }
}
