package gestionhotel.controller;

import gestionhotel.Main;
import gestionhotel.Persona;
import gestionhotel.Reserva;
import gestionhotel.modelo.ReservaModelo;
import gestionhotel.modelo.repository.ExcepcionPersona;
import gestionhotel.modelo.repository.ExcepcionReserva;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.event.Event;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;


// Este controlador se encargará de manejar la vista donde se muestran las reservas de cada cliente
public class ReservaController {
    @FXML
    private GridPane userCardContainer;
    @FXML
    private Label titulo;

    ReservaModelo reservaModelo;
    Main main;
    private ArrayList<Reserva> reservas;
    String dni;
    int col=0;
    int row=0;

    // Inicia las columnas que usaremos y el tamaño que tendrán
    public void initialize() {
        // Crear las columnas y asignarles el mismo valor de forma eficiente
        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(25);
            column.setHalignment(HPos.CENTER);
            userCardContainer.getColumnConstraints().add(column);
        }
    }

    // Inyectamos el modelo de Reserva
    public void setReservaModelo(ReservaModelo reservaModelo) {
        this.reservaModelo = reservaModelo;
    }

    // Inyectamos el dni del cliente
    public void setDni(String dni) {
        this.dni = dni;
    }

    // Inyectamos el Main
    public void setMain(Main main) {
        this.main = main;
    }


    // Funcion para añadir una card de una reserva existente y añadirle un action cuando pulsemos
    public void nuevaTarjetasReserva(Reserva reserva) {
        if (!reservas.contains(reserva)) {
            reservas.add(reserva);
        }

        VBox card = crearCardReserva(reserva);
        userCardContainer.add(card, col, row);

        // Actualizar columnas y filas
        col = (col + 1) % 4;
        if (col == 0) {
            row++;
        }
    }

    private VBox crearCardReserva(Reserva reserva) {
        VBox card = new VBox(10);
        card.setId(String.valueOf(reserva.getIdReserva()));
        card.setStyle("-fx-background-color: #A3C4DC; -fx-border-radius: 10; -fx-padding: 15; -fx-effect: dropshadow(gaussian, #2A4D69, 10, 0, 0, 2);");
        card.setAlignment(Pos.CENTER);
        card.setOnMouseClicked(e -> verDetallesConExcepcion(e));

        // Crear y agregar etiquetas
        Label idLabel = crearLabel("ID Reserva: " + reserva.getIdReserva(), 18, "#2A4D69", true);
        Label fechaLabel = crearLabel("Fecha Llegada: " + reserva.getFechaLlegada().toString(), 16, "#3D5A80", false);

        card.getChildren().addAll(idLabel, fechaLabel);

        return card;
    }

    private Label crearLabel(String text, int fontSize, String color, boolean bold) {
        Label label = new Label(text);
        label.setStyle(String.format("-fx-font-size: %dpx; -fx-font-weight: %s; -fx-text-fill: %s;", fontSize, bold ? "bold" : "normal", color));
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }

    private void verDetallesConExcepcion(Event e) {
        try {
            verDetalles(e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Esta funcion Itera sobre la funcion anterior y crea tantas cards como reservas haya
    public void mostrarTarjetasReserva() {
        userCardContainer.getChildren().clear();
        col=0;
        row=0;
        for (Reserva reserva : reservas) {
            nuevaTarjetasReserva(reserva);
        }
    }

    // Itera sobre las tarjetas y busca la que coincida el id
    public void eliminarTarjetasReserva(int idReserva) {
        for (var node : userCardContainer.getChildren()) {
            if (node instanceof VBox && node.getId() != null && node.getId().equals(String.valueOf(idReserva))) {
                eliminarReservaDelArrayList(idReserva);
                userCardContainer.getChildren().remove(node);
                setData();
                break;
            }
        }
    }

    // Elimina la reserva del arraylist
    public void eliminarReservaDelArrayList(int idReserva) {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getIdReserva() == idReserva) {
                reservas.remove(i);
                break;
            }
        }
    }

    // Llama a la función que muestras las tarjetas
    public void setData()  {
        mostrarTarjetasReserva();
    }


    // llama a la función de main que abre la ventana de editar
    @FXML
    private void añadir() throws IOException {
        main.AñadirReservaEditar(dni);
    }


    // Nos devuelve el id de la ultima reserva, lo usamos cuando añadimos una nueva reserva y el id, se pone automaticamente

    public void lastidReserva() {
        reservaModelo.getLastId();
    }



    // se recogen las reservas de la base de datos
    public void setReservas() throws ExcepcionReserva {
        reservas = reservaModelo.obtenerListaReserva(dni);
        titulo.setText("Reservas de " + dni);
    }


    // funcion que determina que tarjeta hemos pulsado y abre los detalles de la misma
    public void verDetalles(Event event) throws IOException {
        VBox vbox = (VBox) event.getSource();
        String id = vbox.getId();
        Reserva reserva = buscarReserva(Integer.valueOf(id));

        main.verDetalleReserva(reserva);
    }

    // funcion que busca la reserva
    public Reserva buscarReserva(int id) {
        for (Reserva reserva : reservas) {
            if (reserva.getIdReserva() == id) {
                return reserva;
            }
        }
        return null;
    }
}
