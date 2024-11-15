package gestionhotel.controller;

import gestionhotel.Main;
import gestionhotel.Persona;
import gestionhotel.Reserva;
import gestionhotel.modelo.PersonaModelo;
import gestionhotel.modelo.repository.ExcepcionPersona;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.reflect.Method;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button reservasButton;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private VBox userCardContainer;
    @FXML
    private ScrollPane scrollPane;

    private VBox lastSelectedVBox;
    private PersonaModelo personaModelo;
    private ArrayList<Persona> personas;
    private Main main;

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
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    eliminar(deleteButton.getId());
                } catch (ExcepcionPersona e) {
                    throw new RuntimeException(e);
                }
            }
        });
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    editar(getPersona(editButton.getId()));
                } catch (ExcepcionPersona e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        reservasButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    verReserva(reservasButton.getId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public void setMain(Main main) {
        this.main = main;
    }

    public void setPersonaModelo(PersonaModelo personaModelo) {
        this.personaModelo = personaModelo;
    }

    public void setPersonas() throws ExcepcionPersona {
        personas = personaModelo.obtenerListaPersonas();
        setData();  // Llama a setData para generar las tarjetas
    }
    public Persona getPersona(String id){
        for (Persona persona : personas) {
            if (persona.getDNI().equals(id)) {
                return persona;
            }
        }
        return null;
    }

    // Función que crea una nueva tarjeta para una persona
    public VBox nuevaTarjeta(Persona persona) {
        VBox userCard = new VBox();
        userCard.setStyle("-fx-border-color: #6E7C7F; -fx-background-color: #A2B9C0; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10;");
        userCard.setOnMouseClicked( (Event e) -> {
            try {
                Seleccionar(e);  // Pasa el evento como parámetro
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        userCard.setId(persona.getDNI());
        // Almacena el DNI como dato asociado a la tarjeta
        userCard.setUserData(persona.getDNI());

        // Crear las etiquetas para DNI, Nombre y Dirección
        Label dniLabel = new Label("DNI: " + persona.getDNI());
        dniLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        dniLabel.setId("dniLabel");
        Label nameLabel = new Label("Nombre: " + persona.getNombre() + " " + persona.getApellidos());
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        nameLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        nameLabel.setId("nameLabel");

        Label addressLabel = new Label("Dirección: " + persona.getDireccion() + ", "
                + persona.getLocalidad() + ", "
                + persona.getProvincia());
        addressLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        addressLabel.setId("addressLabel");

        // Agregar los nodos a la tarjeta
        userCard.getChildren().addAll(dniLabel, nameLabel, addressLabel);

        return userCard;  // Devolver la tarjeta creada
    }

    // Función que establece los datos en el contenedor de tarjetas
    public void setData() {
      // Limpiar las tarjetas antes de agregar nuevas
        for (Persona persona : personas) {
            VBox userCard = nuevaTarjeta(persona);  // Llamar a la función que crea la tarjeta
            userCardContainer.getChildren().add(userCard);  // Agregar la tarjeta al contenedor
        }
    }
    public void setData(VBox userCard) {
        // Limpiar las tarjetas antes de agregar nuevas
            userCardContainer.getChildren().add(userCard);  // Agregar la tarjeta al contenedor
    }


    @FXML
    private void buscarPorDNI() {
        boolean encontrado = false;
        String dniBusqueda = searchField.getText().trim();// Verifica lo que se está buscando

        for (javafx.scene.Node node : userCardContainer.getChildren()) {
            VBox userCard = (VBox) node;
            String dniTarjeta = (String) userCard.getUserData();  // Obtener el DNI almacenado en la tarjeta
            if (dniBusqueda.equals(dniTarjeta)) {
                encontrado = true;
                userCard.setStyle("-fx-border-color: #6E7C7F; -fx-background-color: #2689a6; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10;");  // Resaltar tarjeta
                double targetScrollValue = userCardContainer.getChildren().indexOf(userCard) / (double) userCardContainer.getChildren().size();

                lastSelectedVBox = userCard;
                String id = userCard.getId();
                deleteButton.setId(id);
                editButton.setId(id);
                reservasButton.setId(id);
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

    public void eliminar(String id) throws ExcepcionPersona {
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
        }
            if (!encontrado) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se ha podido eliminar");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Eliminado con exito");
                alert.show();
            }
    }


    public void editar(Persona persona) throws ExcepcionPersona, IOException {
        if (lastSelectedVBox!=null)
            main.EditarONuevo(persona);
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Debe tener a un usuario seleccionado");
            alert.show();
        }
    }
    public void añadirUsuario() throws ExcepcionPersona, IOException {
        main.EditarONuevo();
    }
    public void verReserva(String id) throws IOException {
        if (lastSelectedVBox!=null)
            main.verReserva(id);
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Debe tener a un usuario seleccionado");
            alert.show();
        }
    }

    public void Seleccionar(Event event) throws IOException {
        VBox vbox = (VBox) event.getSource();
        if (lastSelectedVBox != null && lastSelectedVBox != vbox) {
            lastSelectedVBox.setStyle("-fx-border-color: #6E7C7F; -fx-background-color: #A2B9C0; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10;");
        }
        vbox.setStyle("-fx-border-color: #6E7C7F; -fx-background-color: #2689a6; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10;");
        lastSelectedVBox = vbox;
        String id = vbox.getId();
        deleteButton.setId(id);
        editButton.setId(id);
        reservasButton.setId(id);
    }

    public void actualizarCard(Persona persona) {
        boolean encontrado = false;
        ObservableList<Node> children = userCardContainer.getChildren();
        Iterator<Node> iterator = children.iterator();
        while (iterator.hasNext() && !encontrado) {
            VBox userCard = (VBox) iterator.next();
            String dniTarjeta = (String) userCard.getUserData();  // Obtener el DNI almacenado en la tarjeta
            if (persona.getDNI().equals(dniTarjeta)) {
                encontrado = true;
                // Recorre los nodos dentro de la tarjeta y actualiza los `Label` correspondientes
                for (Node node : userCard.getChildren()) {
                    if (node instanceof Label) {
                        Label label = (Label) node;
                        // Actualiza el contenido según el `id` de cada `Label`
                        switch (label.getId()) {
                            case "dniLabel":
                                label.setText("DNI: "+persona.getDNI());
                                break;
                            case "nameLabel":
                                label.setText("Nombre: " + persona.getNombre() + " " + persona.getApellidos());
                                break;
                            case "addressLabel":
                                label.setText("Dirección: " + persona.getDireccion() + ", "
                                        + persona.getLocalidad() + ", "
                                        + persona.getProvincia());
                                break;
                            // Añade más casos si tienes otros campos en la tarjeta
                        }
                    }
                }
            }
        }
    }
}



