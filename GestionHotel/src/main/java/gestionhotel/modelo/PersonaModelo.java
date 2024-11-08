package gestionhotel.modelo;

import gestionhotel.Persona;
import gestionhotel.modelo.repository.ExcepcionPersona;
import gestionhotel.modelo.repository.PersonaRepository;

import java.util.ArrayList;

public class PersonaModelo {
    PersonaRepository personaRepository;


    public PersonaModelo() {
    }

    public void setPersonaRepository(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }
    public void obtenerListaPersonas() throws ExcepcionPersona {
        ArrayList<PersonaVO> personas = new ArrayList<>();
        personas=personaRepository.ObtenerListaPersonas();
    }

}
