package Agenda.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Agenda.Modelo.Persona;
import Agenda.utiles.DateUtil;

public class Dialogo {
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
    public void setPerson(Persona person) {
        this.person = person;

        nombre.setText(person.getFirstName());
        apellido.setText(person.getLastName());
        calle.setText(person.getStreet());
        postal.setText(Integer.toString(person.getPostalCode()));
        ciudad.setText(person.getCity());
        cumpleaños.setText(DateUtil.format(person.getBirthday()));
        cumpleaños.setPromptText("dd.mm.yyyy");
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(nombre.getText());
            person.setLastName(apellido.getText());
            person.setStreet(calle.getText());
            person.setPostalCode(Integer.parseInt(postal.getText()));
            person.setCity(ciudad.getText());
            person.setBirthday(DateUtil.parse(cumpleaños.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
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

        if (calle.getText() == null || calle.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postal.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (ciudad.getText() == null || ciudad.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }

        if (cumpleaños.getText() == null || cumpleaños.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(cumpleaños.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
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

