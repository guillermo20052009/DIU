package conversorPropio.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class Controller {
    @FXML
    private TextField CuadroEuros;
    @FXML
    private TextField CuadroDolares;

    @FXML
    private void Cambiar(KeyEvent event){
        if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
            float euros = Integer.valueOf(CuadroEuros.getText());
            CuadroDolares.setText(String.valueOf(euros * 1.2));
        }
    }
}