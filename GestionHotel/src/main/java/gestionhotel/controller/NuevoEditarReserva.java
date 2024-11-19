package gestionhotel.controller;

import gestionhotel.Reserva;
import gestionhotel.modelo.Regimen;
import gestionhotel.modelo.ReservaModelo;
import gestionhotel.modelo.repository.ExcepcionPersona;
import gestionhotel.modelo.tipo_habitacion;
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

    public void setDniTextField(String dniTextField) {
        this.dniTextField.setText(dniTextField);
    }
    @FXML
    private void añadir() throws ExcepcionPersona {
        reservaModelo.addReserva(crearReserva());
    }

    private Reserva crearReserva() {
        // Obtener valores de los campos del formulario
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
            regimen = "Media pensión";
        } else if (regimenGroup.getSelectedToggle() == pensionCompletaRadioButton) {
            regimen = "Pensión completa";
        }

        // Crear y devolver una nueva reserva
        return new Reserva( fechaLlegada, fechaSalida,  tipo_habitacion.valueOf(tipoHabitacion.toUpperCase().replaceAll("\\s+", "_")), fumador, Regimen.valueOf(regimen.toUpperCase().replaceAll("\\s+", "_")), numeroHabitaciones,dniCliente);
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
        private void aceptarFormulario() {
            // Lógica para aceptar (por ejemplo, validar datos y guardar)
        }

        @FXML
        private void cancelarFormulario() {
            // Lógica para cerrar o cancelar el formulario
        }
    }
