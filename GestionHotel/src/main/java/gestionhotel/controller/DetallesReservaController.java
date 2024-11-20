package gestionhotel.controller;

import gestionhotel.Main;
import gestionhotel.Reserva;
import gestionhotel.modelo.ReservaModelo;
import gestionhotel.modelo.repository.ExcepcionPersona;
import gestionhotel.modelo.repository.ExcepcionReserva;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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
    Button cancelButton;

    private Reserva reserva;
    private ReservaModelo reservaModelo;
    private ReservaController reservaController;
    private Main main;
    @FXML
    private void initialize() {
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setReservaController(ReservaController reservaController) {
        this.reservaController = reservaController;
    }

    public void ReservaModelo(ReservaModelo reservaModelo) {
        this.reservaModelo = reservaModelo;
    }
    public void eliminarReserva() throws ExcepcionPersona {
        reservaModelo.eliminarReserva(reserva.getIdReserva(),reserva.getDni_cliente());
        reservaController.eliminarTarjetasReserva(reserva.getIdReserva());

    }

    public void editarReserva() throws ExcepcionReserva, IOException {
        main.AñadirReservaEditar(reserva);
    }

    public void setData(Reserva reserva) {
        this.reserva = reserva;
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

    @FXML
    public void cerrar(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


}
