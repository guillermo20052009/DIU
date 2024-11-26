package gestionhotel.modelo.repository;

import gestionhotel.modelo.PersonaVO;

import java.util.ArrayList;

// Interfaz que maneja las funciones de acceso a la base de datos, en concreto a la tabla Persona
public interface PersonaRepository {
    ArrayList<PersonaVO> ObtenerListaPersonas() throws ExcepcionPersona;

    void addPersona(PersonaVO var1) throws ExcepcionPersona;

    void deletePersona(String var1) throws ExcepcionPersona;

    void editPersona(PersonaVO var1) throws ExcepcionPersona;

    int lastId() throws ExcepcionPersona;
}
