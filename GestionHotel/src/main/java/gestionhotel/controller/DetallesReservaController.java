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

// Esta clase es el controlador de los detalles de la reserva, contiene los botones de editar y eliminar.
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

    // Inyectamos el Main
    public void setMain(Main main) {
        this.main = main;
    }

    // Inyectamos el controller de reservas para después pasarle el elemento que se ha creado
    public void setReservaController(ReservaController reservaController) {
        this.reservaController = reservaController;
    }

    // Inyectamos el modelo de Reserva
    public void ReservaModelo(ReservaModelo reservaModelo) {
        this.reservaModelo = reservaModelo;
    }

    // Eliminamos la reserva de la base de datos y de la vista
    public void eliminarReserva() throws ExcepcionPersona {
        // Eliminar reserva en el modelo
        reservaModelo.eliminarReserva(reserva.getIdReserva(), reserva.getDni_cliente());

        // Comprobar si la reserva es "actual"
        java.time.LocalDate fechaActual = java.time.LocalDate.now();
        if (reserva.getFechaLlegada().toLocalDate().isBefore(fechaActual) || reserva.getFechaLlegada().toLocalDate().isEqual(fechaActual)) {
            if (reserva.getFechaSalida().toLocalDate().isAfter(fechaActual) || reserva.getFechaSalida().toLocalDate().isEqual(fechaActual)) {
                // Llamar a incrementar si la reserva está "activa"
                reservaModelo.decrementar(reserva.getTipoHabitacion());
            }
        }
        reservaController.eliminarTarjetasReserva(reserva.getIdReserva());
    }

    // llamamos a la función del main que abre la pantalla de editar con los datos de la reserva actual
    public void editarReserva() throws ExcepcionReserva, IOException {
        main.AñadirReservaEditar(reserva);
    }

    // Colocamos los datos
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

    // cerramos la ventana
    @FXML
    public void cerrar(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


}
