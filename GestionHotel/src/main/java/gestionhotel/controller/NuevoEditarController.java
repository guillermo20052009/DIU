package gestionhotel.controller;

import gestionhotel.modelo.PersonaModelo;
import gestionhotel.Persona;
import gestionhotel.modelo.repository.ExcepcionPersona;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class NuevoEditarController {

    @FXML
    private TextField dniField;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidosField;
    @FXML
    private TextField direccionField;
    @FXML
    private TextField localidadField;
    @FXML
    private TextField provinciaField;
    @FXML
    private Button guardarButton;

    private PersonaModelo personaModelo;
    private Persona persona;

    // Mtodo para inicializar el controlador con los datos del cliente (para edición)
    public void setPersona(Persona persona) {
        this.persona = persona;
        if (persona != null) {
            dniField.setText(persona.getDNI());
            nombreField.setText(persona.getNombre());
            apellidosField.setText(persona.getApellidos());
            direccionField.setText(persona.getDireccion());
            localidadField.setText(persona.getLocalidad());
            provinciaField.setText(persona.getProvincia());
            dniField.setEditable(false); // Hacer el campo DNI no editable
        }
    }
    public void setPersonaModelo(PersonaModelo personaModelo) {
        this.personaModelo = personaModelo;
    }
    @FXML
    private void guardarCliente() {

        try {
            // Validar campos y crear o actualizar el cliente
            if (persona == null) {
                // Crear nuevo cliente
                persona = new Persona(dniField.getText(), nombreField.getText(), apellidosField.getText(),
                        direccionField.getText(), localidadField.getText(), provinciaField.getText());
                personaModelo.actualizarPersona(persona);
            } else {
                // Actualizar cliente existente
                persona.setNombre(nombreField.getText());
                persona.setApellidos(apellidosField.getText());
                persona.setDireccion(direccionField.getText());
                persona.setLocalidad(localidadField.getText());
                persona.setProvincia(provinciaField.getText());
                personaModelo.actualizarPersona(persona);
            }
        } catch (ExcepcionPersona e) {

        }
    }
}