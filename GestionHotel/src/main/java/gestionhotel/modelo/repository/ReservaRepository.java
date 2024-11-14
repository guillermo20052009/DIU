package gestionhotel.modelo.repository;

import gestionhotel.modelo.PersonaVO;
import gestionhotel.modelo.ReservaVO;

import java.util.ArrayList;

public interface ReservaRepository {
    ArrayList<ReservaVO> ObtenerListaReservas() throws ExcepcionReserva;

    void addReserva(ReservaVO var1) throws ExcepcionReserva;

    void deleteReserva(int var1) throws ExcepcionReserva;

    void editReserva(ReservaVO var1) throws ExcepcionReserva;

    int lastId() throws ExcepcionReserva;
}
