package gestionhotel.controller;

import gestionhotel.Persona;
import gestionhotel.modelo.PersonaModelo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class MainController {
   @FXML
    private TextField searchField;
   @FXML
    private Button searchButton;

    private PersonaModelo personaModelo;
    private ArrayList<Persona> personas;
}