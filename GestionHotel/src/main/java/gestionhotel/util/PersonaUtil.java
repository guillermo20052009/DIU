package gestionhotel.util;

import gestionhotel.Persona;
import gestionhotel.modelo.PersonaVO;

import java.util.ArrayList;
// Esta clase se encargará de hacer las transformaciones pertinentes entre Persona y PersonaVO
public class PersonaUtil {
    static ArrayList<Persona> personas = new ArrayList<>();

    // Convierte la lista que recogemos de la base de datos a Persona
    public static ArrayList<Persona> convertirVo(ArrayList<PersonaVO> personas2) {
        for (PersonaVO sujeto : personas2) {
            personas.add(new Persona(sujeto.getDNI(),sujeto.getNombre(),sujeto.getApellidos(),sujeto.getDireccion(),sujeto.getLocalidad(),sujeto.getProvincia()));
        }
        return personas;
    }
    // Convierte una instancia de Persona a PersonaVO
    public static PersonaVO convertirVo(Persona persona) {
       return new PersonaVO(persona.getDNI(),persona.getNombre(),persona.getApellidos(),persona.getDireccion(),persona.getLocalidad(),persona.getProvincia());
    }
}
