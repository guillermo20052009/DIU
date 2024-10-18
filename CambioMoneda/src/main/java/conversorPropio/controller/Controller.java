package conversorPropio.controller;

import Modelo.ExcepcionMoneda;
import conversorPropio.MainApp;
import conversorPropio.Moneda;
import conversorPropio.modelo.ConversorModelo;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Controller {
    @FXML
    private TextField CuadroEuros;
    @FXML
    private TextField CuadroDolares;

    private ConversorModelo modelo;
    private ArrayList<Moneda> monedas;

    @FXML
    private void Cambiar(KeyEvent event) throws ExcepcionMoneda {
        if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
            float Dolares;
            float euros = Float.parseFloat(CuadroEuros.getText());
            Dolares = modelo.conversor(euros,monedas);
            CuadroDolares.setText(String.valueOf(Dolares));
        }
    }
    public void setController(ConversorModelo modelo) {
        this.modelo = modelo;
    }
    public void setMoneda() throws ExcepcionMoneda {
        monedas = modelo.setMoneda();
    }
}

