package gestionhotel.modelo.repository;

import gestionhotel.Reserva;
import gestionhotel.modelo.PersonaVO;
import gestionhotel.modelo.ReservaVO;

import java.util.ArrayList;

// Interfaz que maneja las funciones de acceso a la base de datos, en concreto a la tabla reserva
public interface ReservaRepository {
    ArrayList<ReservaVO> ObtenerListaReservas() throws ExcepcionReserva;

    void addReserva(ReservaVO var1) throws ExcepcionReserva;

    void deleteReserva(int var1) throws ExcepcionReserva;

    void editReserva(ReservaVO var1) throws ExcepcionReserva;

    int lastId() throws ExcepcionReserva;

    int[] countActuales() throws ExcepcionReserva;

    int[] countMonthsByType(String tipo) throws ExcepcionReserva;

    int countConcretasByType(ReservaVO reserva) throws ExcepcionReserva;

}
