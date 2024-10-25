package Agenda.controller;

import Agenda.Persona;
import Agenda.modelo.AgendaModelo;
import Agenda.modelo.ExcepcionPersona;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class ControllerPersonas {
    private AgendaModelo modelo;
    private ArrayList<Persona> personas;
    @FXML
    private TableColumn<Persona, String> nombre;
    @FXML
    private TableColumn<Persona, String> apellido;
    @FXML
    private TableView<Persona> tabla;

    public void setController(AgendaModelo modelo) {
        this.modelo = modelo;
    }
    public void setPersona() throws ExcepcionPersona {
        personas = modelo.setPersona();
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }
    public void setData(ObservableList<Persona> lista){
        tabla.setItems(lista);
    }
}
