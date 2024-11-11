package gestionhotel.controller;

import gestionhotel.Persona;
import gestionhotel.modelo.PersonaModelo;
import gestionhotel.modelo.repository.ExcepcionPersona;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MainController {

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private VBox userCardContainer;
    @FXML
    private ScrollPane scrollPane;

    private PersonaModelo personaModelo;
    private ArrayList<Persona> personas;

    public void initialize() {
        // Ajustar la sensibilidad del desplazamiento para hacerlo más suave
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY();  // Obtiene la dirección y magnitud del desplazamiento
            double currentVvalue = scrollPane.getVvalue();
            double newVvalue = currentVvalue - deltaY * 0.005;  // Reduce el multiplicador para suavizar más el desplazamiento

            // Limita el nuevo valor dentro del rango [0, 1] para evitar que el scroll se salga del límite
            scrollPane.setVvalue(Math.min(Math.max(newVvalue, 0), 1));

            event.consume();
        });
    }


    public void setPersonaModelo(PersonaModelo personaModelo) {
        this.personaModelo = personaModelo;
    }

    public void setPersonas() throws ExcepcionPersona {
        personas = personaModelo.obtenerListaPersonas();
        setData();  // Llama a setData para generar las tarjetas
    }

    public void setData() {
        userCardContainer.getChildren().clear();  // Limpiar las tarjetas antes de agregar nuevas
        for (Persona persona : personas) {
            VBox userCard = new VBox();
            userCard.setStyle("-fx-border-color: #6E7C7F; -fx-background-color: #A2B9C0; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10;");

            // Asegúrate de que el DNI esté almacenado correctamente
            userCard.setUserData(persona.getDNI());  // Almacena el DNI como dato asociado a la tarjeta
            System.out.println("DNI almacenado: " + persona.getDNI());  // Verifica que el DNI esté correctamente asignado

            Label dniLabel = new Label("DNI: " + persona.getDNI());
            dniLabel.setTextFill(javafx.scene.paint.Color.WHITE);

            Label nameLabel = new Label("Nombre: " + persona.getNombre() + " " + persona.getApellidos());
            nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            nameLabel.setTextFill(javafx.scene.paint.Color.WHITE);

            Label addressLabel = new Label("Dirección: " + persona.getDireccion() + ", "
                    + persona.getLocalidad() + ", "
                    + persona.getProvincia());
            addressLabel.setTextFill(javafx.scene.paint.Color.WHITE);

            userCard.getChildren().addAll(dniLabel, nameLabel, addressLabel);
            userCardContainer.getChildren().add(userCard);
        }
    }

    @FXML
    private void buscarPorDNI() {
        boolean encontrado=false;
        String dniBusqueda = searchField.getText().trim();
        System.out.println("DNI Buscado: " + dniBusqueda);  // Verifica lo que se está buscando

        for (javafx.scene.Node node : userCardContainer.getChildren()) {
            VBox userCard = (VBox) node;
            String dniTarjeta = (String) userCard.getUserData();  // Obtener el DNI almacenado en la tarjeta

            // Verifica el DNI que se obtiene de la tarjeta
            System.out.println("DNI en tarjeta: " + dniTarjeta);  // Depura para ver si el DNI está correctamente recuperado

            // Si el DNI coincide, cambia el color de la tarjeta
            if (dniBusqueda.equals(dniTarjeta)) {
                encontrado=true;
                userCard.setStyle("-fx-border-color: #6E7C7F; -fx-background-color: #FFDD57; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10;");  // Resaltar tarjeta
                double targetScrollValue = userCardContainer.getChildren().indexOf(userCard) / (double) userCardContainer.getChildren().size();
                scrollPane.setVvalue(targetScrollValue);
            } else {
                userCard.setStyle("-fx-border-color: #6E7C7F; -fx-background-color: #A2B9C0; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10;");  // Color normal
            }
        }
        if (!encontrado) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Este DNI no existe");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "DNI encontrado");
            alert.show();
        }
    }

}


