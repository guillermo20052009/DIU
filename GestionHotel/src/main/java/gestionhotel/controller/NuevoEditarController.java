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



    @FXML
    private void actualizarPersona(KeyEvent event) {
        // Crea un objeto persona con los datos de los TextField
        if (persona != null) {
            persona.setDNI(dniField.getText());
            persona.setNombre(nombreField.getText());
            persona.setApellidos(apellidosField.getText());
            persona.setDireccion(direccionField.getText());
            persona.setLocalidad(localidadField.getText());
            persona.setProvincia(provinciaField.getText());
        }
    }

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
            // Validar campos y crear o actualizar el cliente
            if (comprobarCampos()) {
                if (persona == null) {
                    // Crear nuevo cliente
                    persona = new Persona(dniField.getText(), nombreField.getText(), apellidosField.getText(),
                            direccionField.getText(), localidadField.getText(), provinciaField.getText());
                    if (dniField.isEditable())
                        personaModelo.addPersona(persona);
                    else
                        personaModelo.actualizarPersona(persona);
                } else {
                    // Actualizar cliente existente
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
                    mainController.nuevaTarjeta(persona);
                    mainController.setData();
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
        // Verifica que ninguno de los campos de texto esté vacío
        if (nombreField.getText().isEmpty() ||
                apellidosField.getText().isEmpty() ||
                direccionField.getText().isEmpty() ||
                localidadField.getText().isEmpty() ||
                provinciaField.getText().isEmpty()) {

            // Si alguno de los campos está vacío, muestra un mensaje de alerta
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos incompletos");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, complete todos los campos.");
            alerta.showAndWait();

            // Devuelve falso si algún campo está vacío
            return false;
        }
        // Devuelve verdadero si todos los campos están completos
        return true;
    }

}