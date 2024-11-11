package gestionhotel.util;

import gestionhotel.Persona;
import gestionhotel.modelo.PersonaVO;

import java.util.ArrayList;

public class PersonaUtil {
    static ArrayList<Persona> personas = new ArrayList<>();

    public static ArrayList<Persona> convertirVo(ArrayList<PersonaVO> personas2) {
        for (PersonaVO sujeto : personas2) {
            personas.add(new Persona(sujeto.getDNI(),sujeto.getNombre(),sujeto.getApellidos(),sujeto.getDireccion(),sujeto.getLocalidad(),sujeto.getProvincia()));
        }
        return personas;
    }
}
