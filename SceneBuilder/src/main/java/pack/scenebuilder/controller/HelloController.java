package pack.scenebuilder.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

public class HelloController {
    @FXML
    public Label numero;
    public TextField cuadro;
    public IntegerProperty numeroPuls = new SimpleIntegerProperty(0);
    public ProgressBar barra;

    @FXML
    public void handleButtonAction() {
        if (cuadro.getText().compareTo("")==0)
            numero.setText("0");
        else
            numero.setText(cuadro.getText());

    }

    @FXML
    public void initialize(){
        barra.progressProperty().bind(numeroPuls.divide(50.0));
        numeroPuls.addListener((obs, oldVal, newVal) -> numero.setText(String.valueOf(newVal)));
    }
    public void handleKeyPressed(KeyEvent event) {
        // Verifica si se presionó la tecla Enter
        if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
            // Lógica para cuando se presiona Enter
            if (isNumeric(cuadro.getText())) {
                numero.setText(cuadro.getText());
                numeroPuls.setValue(Integer.valueOf(numero.getText()));

            } else {
                numero.setText("0");
                numeroPuls.setValue(0);

            }
        }
    }

    public void botonmas(){
        numeroPuls.setValue(Integer.valueOf(numero.getText()));
        numeroPuls.setValue(numeroPuls.getValue()+1);
        numero.setText(String.valueOf(numeroPuls.getValue()));

    }
    public void botonmenos(){
        numeroPuls.setValue(Integer.valueOf(numero.getText()));
        numeroPuls.setValue(numeroPuls.getValue()-1);
        numero.setText(String.valueOf(numeroPuls.getValue()));
    }
    public void botoncero(){
        numeroPuls.setValue(0);
        numero.setText(String.valueOf(numeroPuls.getValue()));
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str); // Puedes usar Integer.parseInt si solo deseas enteros
            return true;
        } catch (NumberFormatException e) {
            return false; // No es un número
        }
    }
}