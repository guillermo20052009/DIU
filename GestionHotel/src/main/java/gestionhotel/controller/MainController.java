package gestionhotel.controller;

import gestionhotel.Main;
import gestionhotel.Persona;
import gestionhotel.modelo.PersonaModelo;
import gestionhotel.modelo.repository.ExcepcionPersona;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

// Controlador principal para gestionar la vista de clientes
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

    // Inicializa el controlador y asigna eventos
    public void initialize() {
        configurarScrollPane();
        configurarBotones();
    }

    // Configura el scroll suave del contenedor
    private void configurarScrollPane() {
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            double newVvalue = scrollPane.getVvalue() - event.getDeltaY() * 0.0005;
            scrollPane.setVvalue(Math.min(Math.max(newVvalue, 0), 1));
            event.consume();
        });
    }

    // Configura los botones principales
    private void configurarBotones() {
        deleteButton.setOnAction(event ->  {
            try {
                eliminar(deleteButton.getId());
            } catch (ExcepcionPersona e) {
                throw new RuntimeException(e);
            }
        });
        editButton.setOnAction(event ->  {
            try {
                editar(getPersona(editButton.getId()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        reservasButton.setOnAction(event -> {
            try {
                verReserva(reservasButton.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Inyección de dependencias
    public void setMain(Main main) {
        this.main = main;
    }

    public void setPersonaModelo(PersonaModelo personaModelo) {
        this.personaModelo = personaModelo;
    }

    public void setPersonas() throws ExcepcionPersona {
        personas = personaModelo.obtenerListaPersonas();
        setData();
    }
    public ArrayList<Persona> getPersonas(){
        return personas;
    }

    // Busca una persona en la lista
    public Persona getPersona(String id) {
        return personas.stream()
                .filter(persona -> persona.getDNI().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Crea una tarjeta de cliente
    public VBox nuevaTarjeta(Persona persona) {
        if (!personas.contains(persona)) personas.add(persona);

        VBox userCard = crearTarjetaBase(persona);
        userCard.setOnMouseClicked(event -> Seleccionar(event));
        return userCard;
    }

    // Configura el estilo base de una tarjeta
    private VBox crearTarjetaBase(Persona persona) {
        VBox userCard = new VBox();
        userCard.setStyle(getCardStyle(false));
        userCard.setId(persona.getDNI());
        userCard.setUserData(persona.getDNI());

        Label dniLabel = new Label("DNI: " + persona.getDNI());
        dniLabel.setId("dniLabel");
        Label nameLabel = new Label("Nombre: " + persona.getNombre() + " " + persona.getApellidos());
        nameLabel.setId("nameLabel");
        Label addressLabel = new Label("Dirección: " + persona.getDireccion() + ", " +
                persona.getLocalidad() + ", " + persona.getProvincia());
        addressLabel.setId("addressLabel");

        userCard.getChildren().addAll(dniLabel, nameLabel, addressLabel);
        return userCard;
    }

    // Devuelve el estilo de una tarjeta
    private String getCardStyle(boolean isSelected) {
        return isSelected
                ? "-fx-border-color: #6E7C7F; -fx-background-color: #2689a6; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10; -fx-text-fill: white;"
                : "-fx-border-color: #6E7C7F; -fx-background-color: #A2B9C0; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10; -fx-text-fill: black;";
    }


    // Actualiza el contenedor con las tarjetas de las personas
    public void setData() {
        personas.forEach(persona -> userCardContainer.getChildren().add(nuevaTarjeta(persona)));
    }

    public void setData(VBox userCard) {
        userCardContainer.getChildren().add(userCard);
    }

    @FXML
    private void buscarPorDNI() {
        String dniBusqueda = searchField.getText().trim();
        boolean encontrado = false;

        for (Node node : userCardContainer.getChildren()) {
            VBox userCard = (VBox) node;
            boolean match = dniBusqueda.equals(userCard.getUserData());
            userCard.setStyle(getCardStyle(match));
            if (match) {
                encontrado = true;
                actualizarBotones(userCard);
                scrollPane.setVvalue(userCardContainer.getChildren().indexOf(userCard) / (double) userCardContainer.getChildren().size());
            }
        }

        alerta(encontrado ? "DNI encontrado" : "Este DNI no existe", encontrado ? Alert.AlertType.CONFIRMATION : Alert.AlertType.ERROR);
    }

    public Persona eliminarPersonaDelArrayList(String id) throws ExcepcionPersona {
        Persona personaAEliminar = null;
        for (Persona p : personas) {
            if (p.getDNI().equals(id)) {
                return p;
            }
        }
        return null;
    }


    public void eliminar(String id) throws ExcepcionPersona {
        personaModelo.eliminarPersona(id);
        personas.remove(eliminarPersonaDelArrayList(id));
        eliminarCard(id);
    }

    public void eliminarCard(String id) {
        Iterator<Node> iterator = userCardContainer.getChildren().iterator();
        boolean encontrado = false;

        while (iterator.hasNext()) {
            VBox userCard = (VBox) iterator.next();
            if (id.equals(userCard.getUserData())) {
                iterator.remove();
                encontrado = true;
                break;
            }
        }

        alerta(encontrado ? "Eliminado con éxito" : "No se ha podido eliminar", encontrado ? Alert.AlertType.CONFIRMATION : Alert.AlertType.ERROR);
    }

    public void editar(Persona persona) throws IOException {
        if (lastSelectedVBox != null) {
            main.EditarONuevo(persona);
        } else {
            alerta("Debe tener a un usuario seleccionado", Alert.AlertType.ERROR);
        }
    }

    public void añadirUsuario() throws IOException {
        main.EditarONuevo();
    }

    public void verReserva(String id) throws IOException {
        if (lastSelectedVBox != null) {
            main.verReserva(id);
        } else {
            alerta("Debe tener a un usuario seleccionado", Alert.AlertType.ERROR);
        }
    }

    public void Seleccionar(javafx.event.Event event) {
        VBox vbox = (VBox) event.getSource();
        if (lastSelectedVBox != null && lastSelectedVBox != vbox) {
            lastSelectedVBox.setStyle(getCardStyle(false));
        }
        vbox.setStyle(getCardStyle(true));
        lastSelectedVBox = vbox;
        actualizarBotones(vbox);
    }

    private void actualizarBotones(VBox userCard) {
        String id = userCard.getId();
        deleteButton.setId(id);
        editButton.setId(id);
        reservasButton.setId(id);
    }

    public void actualizarCard(Persona persona) {
        for (Node node : userCardContainer.getChildren()) {
            VBox userCard = (VBox) node;
            if (persona.getDNI().equals(userCard.getUserData())) {
                actualizarTarjeta(userCard, persona);
                break;
            }
        }
    }

    private void actualizarTarjeta(VBox userCard, Persona persona) {
        for (Node node : userCard.getChildren()) {
            if (node instanceof Label label) {
                switch (label.getId()) {
                    case "dniLabel" -> label.setText("DNI: " + persona.getDNI());
                    case "nameLabel" -> label.setText("Nombre: " + persona.getNombre() + " " + persona.getApellidos());
                    case "addressLabel" -> label.setText("Dirección: " + persona.getDireccion() + ", " + persona.getLocalidad() + ", " + persona.getProvincia());
                }
            }
        }
    }

    @FXML
    private void abrirProgreso(ActionEvent event) throws IOException {
        main.Progreso(((MenuItem) event.getSource()).getText());
    }

    @FXML
    private void abrirEstadisticas(ActionEvent event) throws IOException {
        main.Estadisticas(((MenuItem) event.getSource()).getText());
    }

    public void alerta(String message, Alert.AlertType alertType) {
        new Alert(alertType, message).show();
    }

    @FXML
    private void abrirWeb() throws IOException {
        main.paginaWeb();
    }

}
