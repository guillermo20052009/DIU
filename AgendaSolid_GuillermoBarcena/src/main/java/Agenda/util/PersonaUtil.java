package Agenda.util;

import Agenda.Persona;
import Agenda.modelo.PersonaVO;
import Modelo.ExcepcionMoneda;
import Modelo.MonedaVO;
import Modelo.repository.MonedaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PersonaUtil {
    static ArrayList<Persona> personas = new ArrayList<>();

    public PersonaUtil(){}

    public static ArrayList<Persona> convertirVo(ArrayList<PersonaVO> personas2) {
        for (PersonaVO sujeto : personas2) {
            personas.add(new Persona(sujeto.getCodigo(),sujeto.getNombre(),sujeto.getApellido(),sujeto.getDireccion(),sujeto.getCiudad(),sujeto.getCodigoPostal(),sujeto.getFechaNacimiento()));
        }
        return personas;
    }

    public static PersonaVO convertirVo(Persona persona) {
        System.out.println(persona.getCodigo());
         return new PersonaVO(persona.getCodigo(),persona.getNombre(),persona.getApellido(),persona.getDireccion(),persona.getCiudad(),persona.getCodigoPostal(),persona.getFechaNacimiento());
    }
}