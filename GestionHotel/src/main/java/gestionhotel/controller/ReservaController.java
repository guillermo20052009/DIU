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

public class ReservaController {
    @FXML
    private GridPane userCardContainer;
    @FXML
    private Label titulo;

    ReservaModelo reservaModelo;
    Main main;
    private ArrayList<Reserva> reservas;
    String dni;


    public void setReservaModelo(ReservaModelo reservaModelo) {
        this.reservaModelo = reservaModelo;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void mostrarTarjetasReserva() {
        int col = 0; // Columna inicial
        int row = 0; // Fila inicial

        for (Reserva reserva : reservas) {
            // Crear VBox para la tarjeta
            VBox card = new VBox(10);
            card.setOnMouseClicked( (Event e) -> {
                try {
                    verDetalles(e);  // Pasa el evento como parámetro
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            card.setId(String.valueOf(reserva.getIdReserva()));
            card.setStyle("-fx-background-color: #A3C4DC; -fx-border-radius: 10; -fx-padding: 15; -fx-effect: dropshadow(gaussian, #2A4D69, 10, 0, 0, 2);");
            card.setAlignment(Pos.CENTER);

            // Crear y configurar el Label para el ID de reserva
            Label idLabel = new Label("ID Reserva: " + reserva.getIdReserva());
            idLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2A4D69;");
            idLabel.setTextAlignment(TextAlignment.CENTER);

            // Crear y configurar el Label para la fecha de llegada
            Label fechaLabel = new Label("Fecha Llegada: " + reserva.getFechaLlegada().toString());
            fechaLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #3D5A80;");
            fechaLabel.setTextAlignment(TextAlignment.CENTER);

            // Añadir los labels al VBox (tarjeta)
            card.getChildren().addAll(idLabel, fechaLabel);

            // Añadir la tarjeta al GridPane
            userCardContainer.add(card, col, row);

            // Ajustar la posición de la siguiente tarjeta
            col++;
            if (col > 3) { // Cambiar de columna después de la segunda tarjeta en la fila
                col = 0;
                row++;
            }
        }
    }




    public void setData(){
        mostrarTarjetasReserva();
    }
    @FXML
    private void añadir() throws IOException {
        main.AñadirReservaEditar(dni);
    }

    public void initialize() {
        // Crear las columnas y definir el ancho
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

        // Asignar las ColumnConstraints al GridPane
        userCardContainer.getColumnConstraints().addAll(col1, col2,col3,col4);
    }

    public void setReservas() throws ExcepcionReserva {
        reservas=reservaModelo.obtenerListaReserva(dni);
        titulo.setText("Reservas de "+dni);
    }
    public void verDetalles(Event event) throws IOException {
        VBox vbox = (VBox) event.getSource();
        String id = vbox.getId();
        Reserva reserva=buscarReserva(Integer.valueOf(id));

        main.verDetalleReserva(reserva);
    }
    public Reserva buscarReserva(int id){
        for (Reserva reserva : reservas) {
            if (reserva.getIdReserva() == id) {
                return reserva;
            }
        }
        return null;
    }
}
