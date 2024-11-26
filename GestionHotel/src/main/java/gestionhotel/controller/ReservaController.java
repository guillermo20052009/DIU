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
    public void nuevaTarjetasReserva(Reserva reserva)  {
        if (!reservas.contains(reserva))
            reservas.add(reserva);
        VBox card = new VBox(10);
        card.setOnMouseClicked( (Event e) -> {
            try {
                verDetalles(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        card.setId(String.valueOf(reserva.getIdReserva()));
        card.setStyle("-fx-background-color: #A3C4DC; -fx-border-radius: 10; -fx-padding: 15; -fx-effect: dropshadow(gaussian, #2A4D69, 10, 0, 0, 2);");
        card.setAlignment(Pos.CENTER);

        Label idLabel = new Label("ID Reserva: " + reserva.getIdReserva());
        idLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2A4D69;");
        idLabel.setTextAlignment(TextAlignment.CENTER);

        Label fechaLabel = new Label("Fecha Llegada: " + reserva.getFechaLlegada().toString());
        fechaLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #3D5A80;");
        fechaLabel.setTextAlignment(TextAlignment.CENTER);

        card.getChildren().addAll(idLabel, fechaLabel);

        userCardContainer.add(card, col, row);

        col++;
        if (col > 3) {
            col = 0;
            row++;
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

    // Inicia las columnas que usaremos y el tamaño que tendrán
    public void initialize() {
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        col1.setHalignment(HPos.CENTER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        col2.setHalignment(HPos.CENTER);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        col3.setHalignment(HPos.CENTER);

        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(25);
        col4.setHalignment(HPos.CENTER);

        userCardContainer.getColumnConstraints().addAll(col1, col2, col3, col4);
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
