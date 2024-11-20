package gestionhotel.controller;

import gestionhotel.Main;
import gestionhotel.modelo.PersonaModelo;
import gestionhotel.Persona;
import gestionhotel.modelo.repository.ExcepcionPersona;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


// Esta clase será la encargada de controlar la vista de la ventana de editar y añadir nuevo usuario
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
    private MainController mainController;


// Este metodo se encargará de ir recogiendo lo que escribamos en los editText
    @FXML
    private void actualizarPersona(KeyEvent event) {
        if (persona != null) {
            persona.setDNI(dniField.getText());
            persona.setNombre(nombreField.getText());
            persona.setApellidos(apellidosField.getText());
            persona.setDireccion(direccionField.getText());
            persona.setLocalidad(localidadField.getText());
            persona.setProvincia(provinciaField.getText());
        }
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
        if (persona != null) {
            dniField.setText(persona.getDNI());
            nombreField.setText(persona.getNombre());
            apellidosField.setText(persona.getApellidos());
            direccionField.setText(persona.getDireccion());
            localidadField.setText(persona.getLocalidad());
            provinciaField.setText(persona.getProvincia());
            dniField.setEditable(dniField.getText().equals("Introduce Dni"));
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPersonaModelo(PersonaModelo personaModelo) {
        this.personaModelo = personaModelo;
    }

    @FXML
    private void guardarCliente() {
        try {
            if (comprobarCampos()) {
                if (persona == null) {
                    persona = new Persona(dniField.getText(), nombreField.getText(), apellidosField.getText(),
                            direccionField.getText(), localidadField.getText(), provinciaField.getText());
                    if (dniField.isEditable())
                        personaModelo.addPersona(persona);
                    else
                        personaModelo.actualizarPersona(persona);
                } else {
                    persona.setNombre(nombreField.getText());
                    persona.setApellidos(apellidosField.getText());
                    persona.setDireccion(direccionField.getText());
                    persona.setLocalidad(localidadField.getText());
                    persona.setProvincia(provinciaField.getText());
                    if (dniField.isEditable())
                        personaModelo.addPersona(persona);
                    else
                        personaModelo.actualizarPersona(persona);
                }

                if (dniField.isEditable()){
                    mainController.setData(mainController.nuevaTarjeta(persona));
                } else {
                    mainController.actualizarCard(persona);
                }
                Stage stage = (Stage) guardarButton.getScene().getWindow();
                stage.close();
            }
        } catch (ExcepcionPersona e) {
        }
    }

    public boolean comprobarCampos() {
        if (nombreField.getText().isEmpty() ||
                apellidosField.getText().isEmpty() ||
                direccionField.getText().isEmpty() ||
                localidadField.getText().isEmpty() ||
                provinciaField.getText().isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos incompletos");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, complete todos los campos.");
            alerta.showAndWait();

            return false;
        }
        return true;
    }
}
