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
                String dni = dniField.getText().trim();

                // Comprobar si el DNI es válido
                if (!validarDni(dni)) {
                    return; // Si el DNI no es válido, no seguimos
                }

                // Si la persona es nueva o se está actualizando
                if (persona == null) {
                    persona = new Persona(dni, nombreField.getText(), apellidosField.getText(),
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

                if (dniField.isEditable()) {
                    mainController.setData(mainController.nuevaTarjeta(persona));
                } else {
                    mainController.actualizarCard(persona);
                }

                // Cerrar la ventana de edición
                Stage stage = (Stage) guardarButton.getScene().getWindow();
                stage.close();
            }
        } catch (ExcepcionPersona e) {
            e.printStackTrace();
        }
    }

    public boolean comprobarCampos() {
        if (nombreField.getText().isEmpty() ||
                apellidosField.getText().isEmpty() ||
                direccionField.getText().isEmpty() ||
                localidadField.getText().isEmpty() ||
                provinciaField.getText().isEmpty()) {

            mostrarAlerta("Campos incompletos", "Por favor, complete todos los campos.");
            return false;
        }
        return true;
    }

    // Agregar la función para verificar si el DNI ya existe en el ArrayList
    private boolean dniRepetido(String dni) {
        // Asumiendo que personaModelo.getPersonas() devuelve el ArrayList de personas
        for (Persona p : mainController.getPersonas()) {
            if (p.getDNI().equals(dni)) {
                return true; // El DNI ya está en el sistema
            }
        }
        return false; // El DNI no está repetido
    }

    // Modificar la función validarDni para llamar a dniRepetido
    private boolean validarDni(String dni) {
        if (dni == null || dni.length() != 9) {
            mostrarAlerta("DNI inválido", "El DNI introducido no es válido.");
            return false;
        }

        String numero = dni.substring(0, 8);
        String letra = dni.substring(8).toUpperCase();

        // Verificar si el DNI ya está repetido
        if (dniRepetido(dni)) {
            mostrarAlerta("DNI repetido", "El DNI ya existe en el sistema.");
            return false;
        }

        try {
            long num = Long.parseLong(numero);
        } catch (NumberFormatException e) {
            return false; // Si no es un número, no es válido
        }

        char[] letras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        char letraEsperada = letras[(int) (Long.parseLong(numero) % 23)];
        return letra.equals(String.valueOf(letraEsperada));
    }


    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
