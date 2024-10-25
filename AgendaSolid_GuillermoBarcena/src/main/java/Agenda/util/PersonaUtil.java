package Agenda.util;

import Agenda.Persona;
import Agenda.modelo.PersonaVO;
import Modelo.ExcepcionMoneda;
import Modelo.MonedaVO;
import Modelo.repository.MonedaRepository;

import java.util.ArrayList;

public class PersonaUtil {
    static ArrayList<Persona> personas = new ArrayList<>();

    public PersonaUtil(){}

    public static ArrayList<Persona> convertir(ArrayList<PersonaVO> personas2) {
        for (PersonaVO sujeto : personas2) {
            personas.add(new Persona(sujeto.getNombre(),sujeto.getApellido(),sujeto.getDireccion(),sujeto.getCiudad(),sujeto.getCodigoPostal(),sujeto.getFechaNacimiento()));
        }
        return personas;
    }
}