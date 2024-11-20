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

import java.sql.Date;

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

        // Grupo de radio buttons

        private ToggleGroup regimenGroup;

        // Botones
        @FXML
        private Button limpiarButton;
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
        private Integer id= null;


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
    public void setReservaController(ReservaController reservaController) { this.reservaController = reservaController; }

    public void setDniTextField(String dniTextField) {
        this.dniTextField.setText(dniTextField);
        opcion=1;
    }
    @FXML
    private void añadir() throws ExcepcionPersona {
        if (opcion==1) {
            Reserva reserva = crearReserva();
            reservaModelo.addReserva(reserva);
            reservaController.nuevaTarjetasReserva(reserva);
        } else {
            Reserva reserva = crearReserva();
            reservaModelo.actualizar(reserva);
            reservaController.eliminarReservaDelArrayList(reserva.getIdReserva());
            reservaController.nuevaTarjetasReserva(reserva);
            reservaController.mostrarTarjetasReserva();
        }
        }

    private Reserva crearReserva() {
        // Obtener valores de los campos del formulario
        if (opcion==1) {
            id = reservaModelo.getLastId();
        }
        String dniCliente = dniTextField.getText();
        java.sql.Date fechaLlegada = fechaLlegadaDatePicker.getValue() != null ? Date.valueOf(fechaLlegadaDatePicker.getValue().toString()) : null;
        java.sql.Date fechaSalida = fechaSalidaDatePicker.getValue() != null ? Date.valueOf(fechaSalidaDatePicker.getValue().toString()) : null;
        int numeroHabitaciones = numeroHabitacionesSpinner.getValue();
        String tipoHabitacion = tipoHabitacionComboBox.getValue();
        boolean fumador = fumadorCheckBox.isSelected();

        // Obtener el régimen seleccionado del grupo de radio buttons
        String regimen = "";
        if (regimenGroup.getSelectedToggle() == alojamientoRadioButton) {
            regimen = "desayuno";
        } else if (regimenGroup.getSelectedToggle() == mediaPensionRadioButton) {
            regimen = "Media pension";
        } else if (regimenGroup.getSelectedToggle() == pensionCompletaRadioButton) {
            regimen = "Pension completa";
        }

        // Crear y devolver una nueva reserva
        return new Reserva( id,fechaLlegada, fechaSalida,  tipo_habitacion.valueOf(tipoHabitacion.toUpperCase().replaceAll("\\s+", "_")), fumador, Regimen.valueOf(regimen.toUpperCase().replaceAll("\\s+", "_")), numeroHabitaciones,dniCliente);
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
            opcion=0;
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

}
