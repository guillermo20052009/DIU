package gestionhotel.modelo;

import gestionhotel.Persona;
import gestionhotel.modelo.repository.ExcepcionPersona;
import gestionhotel.modelo.repository.PersonaRepository;
import gestionhotel.util.PersonaUtil;

import java.util.ArrayList;

public class PersonaModelo {
    PersonaRepository personaRepository;


    public PersonaModelo() {
    }

    public void setPersonaRepository(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }
    public ArrayList<Persona> obtenerListaPersonas() throws ExcepcionPersona {
        ArrayList<PersonaVO> personas = new ArrayList<>();
        personas=personaRepository.ObtenerListaPersonas();
        return PersonaUtil.convertirVo(personas);
    }

}
