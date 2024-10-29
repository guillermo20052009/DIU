package Agenda.controller;

import Agenda.modelo.AgendaModelo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Agenda.Persona;
import Agenda.util.DateUtil;

public class ControllerDialogo {
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellido;
    @FXML
    private TextField calle;
    @FXML
    private TextField postal;
    @FXML
    private TextField ciudad;
    @FXML
    private TextField cumpleaños;


    private AgendaModelo modelo;
    private Stage dialogStage;
    private Persona person;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param person
     */

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    public void setPerson(Persona person) {
        this.person = person;
        nombre.setText(person.getNombre());
        apellido.setText(person.getApellido());
        calle.setText(person.getDireccion());
        ciudad.setText(person.getCiudad());
        postal.setText(person.getCodigoPostal());
        cumpleaños.setText(person.getFechaNacimiento());
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setNombre(nombre.getText());
            person.setApellido(apellido.getText());
            person.setDireccion(calle.getText());
            person.setCiudad(ciudad.getText());
            person.setCodigoPostal(postal.getText());
            person.setFechaNacimiento(cumpleaños.getText());

            okClicked = true;
            dialogStage.close();
        }
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setController(AgendaModelo modelo) {
        this.modelo = modelo;
    }
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (nombre.getText() == null || nombre.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (apellido.getText() == null || apellido.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (calle.getText() == null || calle.getText().length() == 0) {
            errorMessage += "No valid street!\n";
        }
        // try to parse the postal code into an int.
        if (postal.getText() == null || postal.getText().length() == 0) {
            errorMessage += "No valid Codigo Postal\n";
        }

        if (ciudad.getText() == null || ciudad.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }

        if (cumpleaños.getText() == null || cumpleaños.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
            alert.show();
            return false;
        }
    }
}
