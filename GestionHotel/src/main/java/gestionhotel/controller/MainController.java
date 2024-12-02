package gestionhotel.controller;

import gestionhotel.Main;
import gestionhotel.Persona;
import gestionhotel.modelo.PersonaModelo;
import gestionhotel.modelo.repository.ExcepcionPersona;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.control.Button;

// Esta clase es la encargada de controlar el comportamiento de la primera ventana que se abre, es decir la vista de los clientes
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


    // vamos a controlar la velocidad del scroll y vamos a añadirle a los botones los "SetOnAction", lo hago aquí
    // y no en el FXML porque van a tener un parámetro cosa que desde el FXML no se puede gestionar
    public void initialize() {
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY();
            double currentVvalue = scrollPane.getVvalue();
            double newVvalue = currentVvalue - deltaY * 0.0005;

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

    //Inyectar el main
    public void setMain(Main main) {
        this.main = main;
    }

    //Inyectar el Modelo
    public void setPersonaModelo(PersonaModelo personaModelo) {
        this.personaModelo = personaModelo;
    }

    //Inyectar la lista de personas
    public void setPersonas() throws ExcepcionPersona {
        personas = personaModelo.obtenerListaPersonas();
        setData();
    }


    // Función que busca a una persona en la lista
    public Persona getPersona(String id){
        for (Persona persona : personas) {
            if (persona.getDNI().equals(id)) {
                return persona;
            }
        }
        return null;
    }


    // Este metodo Construye una tarjeta de cliente, a partir de un Objeto Persona y la añade a la vista
    public VBox nuevaTarjeta(Persona persona) {
        if (!personas.contains(persona))
            personas.add(persona);
        VBox userCard = new VBox();
        userCard.setStyle("-fx-border-color: #6E7C7F; -fx-background-color: #A2B9C0; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10;");
        userCard.setOnMouseClicked( (Event e) -> {
            try {
                Seleccionar(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        userCard.setId(persona.getDNI());
        userCard.setUserData(persona.getDNI());

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

        userCard.getChildren().addAll(dniLabel, nameLabel, addressLabel);

        return userCard;
    }

    // Itera sobre la lista y crea una tarjeta por cada elemento del ArrayList
    public void setData() {
        for (Persona persona : personas) {
            VBox userCard = nuevaTarjeta(persona);
            userCardContainer.getChildren().add(userCard);
        }
    }

    // Esto lo usaremos para añadir una tarjeta nueva, una sola.
    public void setData(VBox userCard) {
        userCardContainer.getChildren().add(userCard);
    }


    // Esta función se encarga de iterar todas las cards hasta que encuentra una coincidencia en el dni y selecciona esa tarjeta
    @FXML
    private void buscarPorDNI() {
        boolean encontrado = false;
        String dniBusqueda = searchField.getText().trim();

        for (javafx.scene.Node node : userCardContainer.getChildren()) {
            VBox userCard = (VBox) node;
            String dniTarjeta = (String) userCard.getUserData();
            if (dniBusqueda.equals(dniTarjeta)) {
                encontrado = true;
                userCard.setStyle("-fx-border-color: #6E7C7F; -fx-background-color: #2689a6; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10;");
                double targetScrollValue = userCardContainer.getChildren().indexOf(userCard) / (double) userCardContainer.getChildren().size();

                lastSelectedVBox = userCard;
                String id = userCard.getId();
                deleteButton.setId(id);
                editButton.setId(id);
                reservasButton.setId(id);
                scrollPane.setVvalue(targetScrollValue);
            } else {
                userCard.setStyle("-fx-border-color: #6E7C7F; -fx-background-color: #A2B9C0; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10;");
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


    // Elimina la persona seleccionada de la base de datos y luego llama una función que elimina esa card
    public void eliminar(String id) throws ExcepcionPersona {
        personaModelo.eliminarPersona(id);
        eliminarCard(id);
    }


    // esta función itera sobre las cards
    public void eliminarCard(String id) {
        boolean encontrado = false;
        ObservableList<Node> children = userCardContainer.getChildren();
        Iterator<Node> iterator = children.iterator();

        while (iterator.hasNext() && !encontrado) {
            VBox userCard = (VBox) iterator.next();
            String dniTarjeta = (String) userCard.getUserData();

            if (id.equals(dniTarjeta)) {
                encontrado = true;
                iterator.remove();
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

    // Esta funcion se encarga de abrir la ventana de edición de usuario
    public void editar(Persona persona) throws ExcepcionPersona, IOException {
        if (lastSelectedVBox != null)
            main.EditarONuevo(persona);
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Debe tener a un usuario seleccionado");
            alert.show();
        }
    }

    // Esta función se encarga de abrir la ventana de añadir usuarios
    public void añadirUsuario() throws ExcepcionPersona, IOException {
        main.EditarONuevo();
    }

    // Esta función se encarga de abrir las reservas del cliente Seleccionado
    public void verReserva(String id) throws IOException {
        if (lastSelectedVBox != null)
            main.verReserva(id);
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Debe tener a un usuario seleccionado");
            alert.show();
        }
    }

    // Esta función se encarga de manetener la logica de la carda seleccionada, cuando hacemos clic en una card, se
    // cambia de color y se cambian los "id" de los botones al DNI de esa persona seleccionada
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

    // Esta función se encarga de buscar la tarjeta que tenemos que actualizar
    public void actualizarCard(Persona persona) {
        ObservableList<Node> children = userCardContainer.getChildren();

        for (Node node : children) {
            VBox userCard = (VBox) node;
            String dniTarjeta = (String) userCard.getUserData();

            if (!persona.getDNI().equals(dniTarjeta)) continue;

            actualizarTarjeta(userCard, persona);
            break;
        }
    }

    // Actualiza la tarjeta que hemos encontrado
    private void actualizarTarjeta(VBox userCard, Persona persona) {
        for (Node node : userCard.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                if (label.getId().equals("dniLabel")) {
                    label.setText("DNI: " + persona.getDNI());
                } else if (label.getId().equals("nameLabel")) {
                    label.setText("Nombre: " + persona.getNombre() + " " + persona.getApellidos());
                } else if (label.getId().equals("addressLabel")) {
                    label.setText("Dirección: " + persona.getDireccion() + ", " + persona.getLocalidad() + ", " + persona.getProvincia());
                }
            }
        }
    }

    @FXML
    private void abrirProgreso(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        String menuItemText = menuItem.getText();
        main.Progreso(menuItemText);
    }
    @FXML
    private void abrirEstadisticas(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        String menuItemText = menuItem.getText();
        main.Estadisticas(menuItemText);
    }

}
