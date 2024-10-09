package Agenda.controller;

import Agenda.Modelo.Persona;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Agenda.controller.Agenda;
import Agenda.Modelo.Persona;
import Agenda.utiles.DateUtil;

public class PersonOverviewController {
    @FXML
    private TableView<Persona> personas;
    @FXML
    private TableColumn<Persona, String> nombre;
    @FXML
    private TableColumn<Persona, String> apellido;

    @FXML
    private Label nombreEtiqueta;
    @FXML
    private Label apellidoEtiqueta;
    @FXML
    private Label calleEtiqueta;
    @FXML
    private Label postalEtiqueta;
    @FXML
    private Label ciudadEtiqueta;
    @FXML
    private Label cumpleañosEtiqueta;

    // Reference to the main application.
    private Agenda agenda;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    private void showPersonDetails(Persona person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            nombreEtiqueta.setText(person.getFirstName());
            apellidoEtiqueta.setText(person.getLastName());
            calleEtiqueta.setText(person.getStreet());
            postalEtiqueta.setText(Integer.toString(person.getPostalCode()));
            cumpleañosEtiqueta.setText(DateUtil.format(person.getBirthday()));
            ciudadEtiqueta.setText(person.getCity());

            // TODO: We need a way to convert the birthday into a String!
            // birthdayLabel.setText(...);
        } else {
            // Person is null, remove all the text.
            nombreEtiqueta.setText("");
            apellidoEtiqueta.setText("");
            calleEtiqueta.setText("");
            postalEtiqueta.setText("");
            ciudadEtiqueta.setText("");
            cumpleañosEtiqueta.setText("");
        }
    }
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personas.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personas.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Debes tener seleccionada a una persona para borrrarla");
            alert.show();
        }
    }
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        nombre.setCellValueFactory(
                cellData -> cellData.getValue().firstNameProperty());
        apellido.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty());

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param agenda
     */
    public void setMainApp(Agenda agenda) {
        this.agenda = agenda;

        // Add observable list data to the table
        personas.setItems(agenda.getPersonData());
    }
}