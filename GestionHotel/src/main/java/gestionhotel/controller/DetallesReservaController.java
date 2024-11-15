package gestionhotel.controller;

import gestionhotel.Reserva;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DetallesReservaController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label idReservaLabel;
    @FXML
    private Label fechaLlegadaLabel;
    @FXML
    private Label fechaSalidaLabel;
    @FXML
    private Label tipoLabel;
    @FXML
    private Label fumadorLabel;
    @FXML
    private Label regimenLabel;
    @FXML
    private Label numeroHabitacionesLabel;
    @FXML
    private Label dniClienteLabel;
    @FXML
    private void initialize() {
        // Verifica que los elementos en el FXML se hayan cargado correctamente
        System.out.println("Controlador cargado correctamente");
    }

    public void setData(Reserva reserva) {
        idReservaLabel.setText(String.valueOf(reserva.getIdReserva()));
        fechaLlegadaLabel.setText(String.valueOf(reserva.getFechaLlegada()));
        fechaSalidaLabel.setText(String.valueOf(reserva.getFechaSalida()));
        tipoLabel.setText(String.valueOf(reserva.getTipoHabitacion()));
        String fumador = reserva.isFumador() ? "Sí" : "No";
        fumadorLabel.setText(fumador);
        regimenLabel.setText(String.valueOf(reserva.getRegimen()));
        numeroHabitacionesLabel.setText(String.valueOf(reserva.getNumero_habitaciones()));
        dniClienteLabel.setText(reserva.getDni_cliente());
    }
}
