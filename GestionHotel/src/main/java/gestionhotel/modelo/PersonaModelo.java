package gestionhotel.modelo;

import gestionhotel.Persona;
import gestionhotel.modelo.repository.ExcepcionPersona;
import gestionhotel.modelo.repository.PersonaRepository;
import gestionhotel.util.PersonaUtil;

import java.util.ArrayList;


// Las clases modelo se encargan de toda la lógica de negocio y de recoger los datos de la BDD.
public class PersonaModelo {
    PersonaRepository personaRepository;

    // Constructor vacio para inicializarlo
    public PersonaModelo() {
    }

    // Inyectamos el repository que corresponda
    public void setPersonaRepository(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // Obtenemos las personas de la base de datos
    public ArrayList<Persona> obtenerListaPersonas() throws ExcepcionPersona {
        ArrayList<PersonaVO> personas = new ArrayList<>();
        personas=personaRepository.ObtenerListaPersonas();
        return PersonaUtil.convertirVo(personas);
    }

    // Eliminamos a una persona de la base de datos
    public void eliminarPersona(String dni) throws ExcepcionPersona {
        personaRepository.deletePersona(dni);
    }

    // Actualizamos una persona de la base de datos
    public void actualizarPersona(Persona persona) throws ExcepcionPersona {
        personaRepository.editPersona(PersonaUtil.convertirVo(persona));
    }

    // Añadir registro a la base de datos
    public void addPersona(Persona persona) throws ExcepcionPersona {
        personaRepository.addPersona(PersonaUtil.convertirVo(persona));
    }

}
