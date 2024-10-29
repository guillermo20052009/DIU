package Agenda.modelo;

import Agenda.Persona;
import Agenda.modelo.repository.PersonaRepository;
import Agenda.util.PersonaUtil;
import Modelo.ExcepcionMoneda;
import Modelo.MonedaVO;
import Modelo.repository.MonedaRepository;

import java.util.ArrayList;

public class AgendaModelo {
    PersonaRepository personaRepository;

    public AgendaModelo() {
    }

    public void SetConversorModelo(PersonaRepository impl) throws ExcepcionPersona {
        this.personaRepository=impl;
    }
    public ArrayList<Persona> setPersona() throws ExcepcionPersona {
        ArrayList<PersonaVO> monedaVOs = this.personaRepository.ObtenerListaPersonas();
        return PersonaUtil.convertirVo(monedaVOs);
    }
    public void deletePersona(Persona persona) throws ExcepcionPersona {
        personaRepository.deletePersona(persona.getCodigo());
    }
    public void addPersona(Persona persona) throws ExcepcionPersona {
        personaRepository.addPersona(PersonaUtil.convertirVo(persona));
    }
    public void editPersona(PersonaVO p) throws ExcepcionPersona {
        personaRepository.editPersona(p);
    }

}
