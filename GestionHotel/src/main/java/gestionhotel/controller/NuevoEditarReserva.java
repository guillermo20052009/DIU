package gestionhotel.controller;

import gestionhotel.Reserva;
import gestionhotel.modelo.Regimen;
import gestionhotel.modelo.ReservaModelo;
import gestionhotel.modelo.repository.ExcepcionPersona;
import gestionhotel.modelo.tipo_habitacion;
import gestionhotel.util.ReservaUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;

public class NuevoEditarReserva {
    // Campos de "Datos del cliente"
    @FXML
    private TextField dniTextField;

    // Campos de "Datos de la reserva"
    @FXML
    private DatePicker fechaLlegadaDatePicker;
    @FXML
    private DatePicker fechaSalidaDatePicker;
    @FXML
    private Spinner<Integer> numeroHabitacionesSpinner;
    @FXML
    private ComboBox<String> tipoHabitacionComboBox;
    @FXML
    private CheckBox fumadorCheckBox;
    @FXML
    private Label fumadorLabel;

    // Grupo de radio buttons
    private ToggleGroup regimenGroup;

    // Botones
    @FXML
    private Button aceptarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private RadioButton alojamientoRadioButton;
    @FXML
    private RadioButton mediaPensionRadioButton;
    @FXML
    private RadioButton pensionCompletaRadioButton;

    private ReservaModelo reservaModelo;
    private ReservaController reservaController;
    private int opcion;
    private Integer id = null;

    @FXML
    public void initialize() {
        // Configuración inicial (si es necesaria)
        tipoHabitacionComboBox.setItems(FXCollections.observableArrayList(
                "Doble individual",
                "Doble",
                "Junior Suite",
                "Suite"
        ));

        // Spinner inicial
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1, 1);
        numeroHabitacionesSpinner.setValueFactory(valueFactory);
        regimenGroup = new ToggleGroup();
        alojamientoRadioButton.setToggleGroup(regimenGroup);
        mediaPensionRadioButton.setToggleGroup(regimenGroup);
        pensionCompletaRadioButton.setToggleGroup(regimenGroup);

        // Opcional: establecer el valor predeterminado
        regimenGroup.selectToggle(alojamientoRadioButton);
        dniTextField.setEditable(Boolean.FALSE);
    }

    public void setReservaModelo(ReservaModelo reservaModelo) {
        this.reservaModelo = reservaModelo;
    }

    public void setReservaController(ReservaController reservaController) {
        this.reservaController = reservaController;
    }

    public void setDniTextField(String dniTextField) {
        this.dniTextField.setText(dniTextField);
        opcion = 1;
    }

    private int espacio(Reserva reserva){
        System.out.println(reservaModelo.contarReservasFecha(reserva));
        return reservaModelo.contarReservasFecha(reserva);

    }

    @FXML
    private void añadir() throws ExcepcionPersona {
        if (opcion == 1) {
            if (comprobarCampos()) {
                Reserva reserva = crearReserva();
                if (espacio(reserva)<reservaModelo.getMaximo(reserva.getTipoHabitacion())) {
                    reservaModelo.addReserva(reserva);
                    reserva.setIdReserva(reservaModelo.getLastId());
                    reservaController.nuevaTarjetasReserva(reserva);
                    Stage stage = (Stage) aceptarButton.getScene().getWindow();
                    stage.close();
                } else {
                    mostrarAlerta("LLENOS","Lo siento señor las reservas para hoy están completas");
                }
            }
        } else {
            if (comprobarCampos()) {
                Reserva reserva = crearReserva();
                reservaModelo.actualizar(reserva);
                reservaController.eliminarReservaDelArrayList(reserva.getIdReserva());
                reservaController.nuevaTarjetasReserva(reserva);
                reservaController.mostrarTarjetasReserva();
                Stage stage = (Stage) aceptarButton.getScene().getWindow();
                stage.close();
            }
        }
    }

    private boolean comprobarCampos() {
        // Comprobar que los campos no estén vacíos
        if (dniTextField.getText().isEmpty() ||
                fechaLlegadaDatePicker.getValue() == null ||
                fechaSalidaDatePicker.getValue() == null ||
                tipoHabitacionComboBox.getValue() == null ||
                regimenGroup.getSelectedToggle() == null) {

            // Mostrar una alerta si algún campo está vacío
            mostrarAlerta("Campos incompletos", "Por favor, complete todos los campos.");
            return false;
        }

        // Comprobar que la fecha de llegada es menor que la fecha de salida
        if (fechaLlegadaDatePicker.getValue().isAfter(fechaSalidaDatePicker.getValue())) {
            // Mostrar una alerta si la fecha de llegada es posterior a la fecha de salida
            mostrarAlerta("Fechas incorrectas", "La fecha de llegada no puede ser posterior a la fecha de salida.");
            return false;
        }

        // Comprobar que la fecha de llegada es mayor o igual a la fecha actual
        if (fechaLlegadaDatePicker.getValue().isBefore(java.time.LocalDate.now())) {
            // Mostrar una alerta si la fecha de llegada es anterior a la fecha actual
            mostrarAlerta("Fecha de llegada no válida", "La fecha de llegada no puede ser anterior a hoy.");
            return false;
        }

        return true;
    }

    @FXML
    private void cancelar() {
        // Obtener el Stage actual
        Stage stage = (Stage) cancelarButton.getScene().getWindow();

        // Cerrar el Stage
        stage.close();
    }

    private Reserva crearReserva() {
        // Obtener valores de los campos del formulario
        if (opcion == 1) {
            id = (reservaModelo.getLastId() + 1);
            System.out.println(id);
        }
        String dniCliente = dniTextField.getText();
        java.sql.Date fechaLlegada = fechaLlegadaDatePicker.getValue() != null ? Date.valueOf(fechaLlegadaDatePicker.getValue().toString()) : null;
        java.sql.Date fechaSalida = fechaSalidaDatePicker.getValue() != null ? Date.valueOf(fechaSalidaDatePicker.getValue().toString()) : null;
        int numeroHabitaciones = numeroHabitacionesSpinner.getValue();
        String tipoHabitacion = tipoHabitacionComboBox.getValue();
        boolean fumador = fumadorCheckBox.isSelected();

        // Obtener el régimen seleccionado del grupo de radio buttons
        String regimen = (regimenGroup.getSelectedToggle() == alojamientoRadioButton) ? "desayuno" :
                (regimenGroup.getSelectedToggle() == mediaPensionRadioButton) ? "Media pension" :
                        (regimenGroup.getSelectedToggle() == pensionCompletaRadioButton) ? "Pension completa" : "";


        // Crear y devolver una nueva reserva
        return new Reserva(id, fechaLlegada, fechaSalida, tipo_habitacion.valueOf(tipoHabitacion.toUpperCase().replaceAll("\\s+", "_")), fumador, Regimen.valueOf(regimen.toUpperCase().replaceAll("\\s+", "_")), numeroHabitaciones, dniCliente);
    }

    // Métodos para manejar eventos (opcional)
    @FXML
    private void limpiarFormulario() {
        fechaLlegadaDatePicker.setValue(null);
        fechaSalidaDatePicker.setValue(null);
        numeroHabitacionesSpinner.getValueFactory().setValue(1);
        tipoHabitacionComboBox.getSelectionModel().clearSelection();
        fumadorCheckBox.setSelected(false);
        regimenGroup.selectToggle(null);
    }

    @FXML
    private void cancelarFormulario() {
        // Lógica para cerrar o cancelar el formulario
    }

    public void setData(Reserva reserva) {
        opcion = 0;
        // Rellenar el campo DNI
        id = reserva.getIdReserva();
        dniTextField.setText(reserva.getDni_cliente());

        // Rellenar los campos de fecha
        fechaLlegadaDatePicker.setValue(reserva.getFechaLlegada().toLocalDate());
        fechaSalidaDatePicker.setValue(reserva.getFechaSalida().toLocalDate());

        // Rellenar el spinner de número de habitaciones
        numeroHabitacionesSpinner.getValueFactory().setValue(reserva.getNumero_habitaciones());

        // Seleccionar el tipo de habitación en el ComboBox
        tipoHabitacionComboBox.setValue(reserva.getTipoHabitacion().toString().replaceAll("_", " "));

        // Configurar el checkbox de fumador
        fumadorCheckBox.setSelected(reserva.isFumador());

        // Seleccionar el régimen adecuado
        switch (reserva.getRegimen()) {
            case DESAYUNO:
                regimenGroup.selectToggle(alojamientoRadioButton);
                break;
            case MEDIA_PENSION:
                regimenGroup.selectToggle(mediaPensionRadioButton);
                break;
            case PENSION_COMPLETA:
                regimenGroup.selectToggle(pensionCompletaRadioButton);
                break;
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    @FXML
    private void mostrarLabelFumador() {
        if (fumadorCheckBox.isSelected()) {
            fumadorLabel.setVisible(true); // Mostrar el Label
        } else {
            fumadorLabel.setVisible(false); // Ocultar el Label
        }
    }
}



