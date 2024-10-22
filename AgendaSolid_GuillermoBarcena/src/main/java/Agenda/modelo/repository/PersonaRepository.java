package Agenda.modelo.repository;

import Agenda.modelo.PersonaVO;
import Agenda.modelo.ExcepcionPersona;


import java.util.ArrayList;


public interface PersonaRepository {
    ArrayList<PersonaVO> ObtenerListaMonedas() throws ExcepcionPersona;

    void addMoneda(PersonaVO var1) throws ExcepcionPersona;

    void deleteMoneda(Integer var1) throws ExcepcionPersona;

    void editMoneda(PersonaVO var1) throws ExcepcionPersona;

    int lastId() throws ExcepcionPersona;
}
