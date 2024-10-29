package Agenda.controller;

import Agenda.Main;
import Agenda.Persona;
import Agenda.modelo.AgendaModelo;
import Agenda.modelo.ExcepcionPersona;
import Agenda.util.DateUtil;
import Agenda.util.PersonaUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class ControllerPersonas {
    private AgendaModelo modelo;
    private ArrayList<Persona> personas;
    private boolean okclicked;
    @FXML
    private TableColumn<Persona, String> nombre;
    @FXML
    private TableColumn<Persona, String> apellido;
    @FXML
    private TableView<Persona> tabla;
    @FXML
    private Label nombreEtiqueta;
    @FXML
    private Label apellidoEtiqueta;
    @FXML
    private Label calleEtiqueta;
    @FXML
    private Label ciudadEtiqueta;
    @FXML
    private Label postalEtiqueta;
    @FXML
    private Label cumpleañosEtiqueta;

    Main main;

    private void showPersonDetails(Persona person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            nombreEtiqueta.setText(person.getNombre());
            apellidoEtiqueta.setText(person.getApellido());
            calleEtiqueta.setText(person.getDireccion());
            ciudadEtiqueta.setText(person.getCiudad());
            postalEtiqueta.setText(String.valueOf(person.getCodigoPostal()));
            cumpleañosEtiqueta.setText(person.getFechaNacimiento());


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
    public void setclick(boolean hola){
        this.okclicked = hola;
    }

    @FXML
    private void initialize() {
        nombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        apellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        tabla.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));

    }

    public void setController(AgendaModelo modelo) {
        this.modelo = modelo;
    }
    public void setMain(Main main) {
        this.main = main;
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
    @FXML
    private void handleDeletePerson() throws ExcepcionPersona {
        int selectedIndex = tabla.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            modelo.deletePersona(tabla.getItems().get(selectedIndex));
            tabla.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Debes tener seleccionada a una persona para borrrarla");
            alert.show();
        }
    }
    @FXML
    private void handleNewPerson() throws ExcepcionPersona {
        personas.clear();
        Persona tempPerson = new Persona();
        main.showPersonEditDialog(tempPerson);
        if (okclicked) {
           modelo.addPersona(tempPerson);
           main.reiniciarlista();

        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() throws ExcepcionPersona {
        Persona selectedPerson = tabla.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            main.showPersonEditDialog(selectedPerson);
            if (okclicked) {
                modelo.editPersona(PersonaUtil.convertirVo(selectedPerson));
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.ERROR, "Debes tener seleccionada a una persona para borrrarla");
            alert.show();
        }
    }

}
