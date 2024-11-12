package gestionhotel.controller;

import gestionhotel.Persona;
import gestionhotel.modelo.PersonaModelo;
import gestionhotel.modelo.repository.ExcepcionPersona;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.reflect.Method;
import javafx.scene.control.Button;

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
            double newVvalue = currentVvalue - deltaY * 0.0005;  // Reduce el multiplicador para suavizar más el desplazamiento

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
        int cont=0;
        for (Persona persona : personas) {
            VBox userCard = new VBox();
            userCard.setStyle("-fx-border-color: #6E7C7F; -fx-background-color: #A2B9C0; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10;");

            // Asegúrate de que el DNI esté almacenado correctamente
            userCard.setUserData(persona.getDNI());  // Almacena el DNI como dato asociado a la tarjeta
            // Verifica que el DNI esté correctamente asignado

            Label dniLabel = new Label("DNI: " + persona.getDNI());
            dniLabel.setTextFill(javafx.scene.paint.Color.WHITE);

            Label nameLabel = new Label("Nombre: " + persona.getNombre() + " " + persona.getApellidos());
            nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            nameLabel.setTextFill(javafx.scene.paint.Color.WHITE);

            Label addressLabel = new Label("Dirección: " + persona.getDireccion() + ", "
                    + persona.getLocalidad() + ", "
                    + persona.getProvincia());
            addressLabel.setTextFill(javafx.scene.paint.Color.WHITE);

            Button eliminar = new Button("Eliminar");
            eliminar.setStyle("-fx-background-color: #4D8FAC; -fx-text-fill: WHITE;");
            eliminar.setId(persona.getDNI());
            eliminar.setOnAction(event -> {
                try {
                    eliminar(eliminar.getId());
                } catch (ExcepcionPersona e) {
                    throw new RuntimeException(e);
                }
            });

            HBox separacion = new HBox();
            separacion.setPadding(new Insets(3));

            Button editar = new Button("Editar");
            editar.setStyle("-fx-background-color: #4D8FAC; -fx-text-fill: WHITE");
            eliminar.setId(persona.getDNI());
            eliminar.setOnAction(event -> {
                try {
                    editar(persona);
                } catch (ExcepcionPersona e) {
                    throw new RuntimeException(e);
                }
            });


            HBox separacion2 = new HBox();
            separacion2.setPadding(new Insets(3));

            Button reservas = new Button("Ver Reservas");
            reservas.setStyle("-fx-background-color: #4D8FAC; -fx-text-fill: WHITE");

            userCard.getChildren().addAll(dniLabel, nameLabel, addressLabel,eliminar,separacion,editar,separacion2,reservas);
            userCardContainer.getChildren().add(userCard);
        }

    }
    @FXML
    private void buscarPorDNI() {
        boolean encontrado=false;
        String dniBusqueda = searchField.getText().trim();// Verifica lo que se está buscando

        for (javafx.scene.Node node : userCardContainer.getChildren()) {
            VBox userCard = (VBox) node;
            String dniTarjeta = (String) userCard.getUserData();  // Obtener el DNI almacenado en la tarjeta

            // Verifica el DNI que se obtiene de la tarjeta
            // Depura para ver si el DNI está correctamente recuperado

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
    public void eliminar (String id) throws ExcepcionPersona {
        personaModelo.eliminarPersona(id);
        eliminarCard(id);
    }

    public void eliminarCard(String id) {
        boolean encontrado = false;
        ObservableList<Node> children = userCardContainer.getChildren();
        Iterator<Node> iterator = children.iterator(); // Usamos un iterador

        while (iterator.hasNext() && !encontrado) {
            VBox userCard = (VBox) iterator.next();
            String dniTarjeta = (String) userCard.getUserData();  // Obtener el DNI almacenado en la tarjeta

            // Si el DNI coincide, elimina la tarjeta
            if (id.equals(dniTarjeta)) {
                encontrado = true;
                iterator.remove(); // Eliminar el nodo de la lista de manera segura
            }
            if (!encontrado) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se ha podido eliminar");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Eliminado con exito");
                alert.show();
            }
        }
    }
    public void editar(Persona persona) throws ExcepcionPersona {

    }

}


