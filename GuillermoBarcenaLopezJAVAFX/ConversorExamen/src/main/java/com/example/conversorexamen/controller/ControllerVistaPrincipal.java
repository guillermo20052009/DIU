package com.example.conversorexamen.controller;

import Modelo.ExcepcionMoneda;
import com.example.conversorexamen.MainApp;
import com.example.conversorexamen.ModeloConversor.ModeloConversor;
import com.example.conversorexamen.Moneda;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class ControllerVistaPrincipal {
    ModeloConversor modeloConversor;
    MainApp mainApp;
    ArrayList<Moneda> monedas;
    int opcion;
    @FXML
    private Label cambiar;
    @FXML
    private TableView<Moneda> tablaconversor;
    @FXML
    private TableColumn<Moneda, String> nombreColumn;
    @FXML
    private TextField campoMoneda;
    @FXML
    private TextField campoEuros;


    @FXML
    private void initialize() {
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        seleccionMoneda(null);
        tablaconversor.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionMoneda(newValue));
    }

    public ControllerVistaPrincipal() {
    }

    public void setModeloConversor(ModeloConversor modeloConversor) {
        this.modeloConversor = modeloConversor;
    }

    public void setMonedas(ArrayList<Moneda> monedas) {
        this.monedas = monedas;
    }

    public void seleccionMoneda(Moneda moneda) {
        if (moneda != null) {
            cambiar.setText(moneda.getNombre());
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        tablaconversor.setItems(mainApp.getArticulos());
    }

    @FXML
    private void Convertir() throws ExcepcionMoneda {
        try {
            int seleccion = tablaconversor.getSelectionModel().getSelectedIndex();
            if (seleccion != -1) {
                if (campoEuros.getText().length() != 0) {
                    String convertido = modeloConversor.obtenerValorMonedaEuros(monedas.get(seleccion), campoEuros.getText());
                    campoMoneda.setText(convertido);
                } else if (campoMoneda.getText().length() != 0) {
                    String convertido = modeloConversor.obtenerValorMonedaOtra(monedas.get(seleccion), campoMoneda.getText());
                    campoEuros.setText(convertido);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de entrada");
                    alert.setHeaderText("Valores inválidos");
                    alert.setContentText("Por favor introduce Algun valor en alguno de los campos");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de seleccion");
                alert.setHeaderText("Seleccion inválida");
                alert.setContentText("Selecciona una Moneda en la tabla");
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de entrada");
            alert.setHeaderText("El valor debe ser un numero");
            alert.setContentText("Introduce un numero en la tabla");
            alert.showAndWait();
        }
    }

    @FXML
    private void Vaciar() {
        campoEuros.setText("");
        campoMoneda.setText("");
    }

    @FXML
    private void Eliminar() {
        int seleccionado = tablaconversor.getSelectionModel().getSelectedIndex();
        Moneda seleccionadoArticulo = tablaconversor.getSelectionModel().getSelectedItem();

        if (seleccionado != -1) {
            tablaconversor.getItems().remove(seleccionado);
            modeloConversor.decrementarCantidad();
            try {
                modeloConversor.EliminarMoneda(seleccionadoArticulo.getCodigo());
            }  catch (ExcepcionMoneda e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Seleccionado");
            alert.setHeaderText("No hay artículo seleccionado");
            alert.setContentText("Por favor selecciona un artículo en la tabla");
            alert.showAndWait();
        }
    }
}


