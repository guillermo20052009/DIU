package pack.scenebuilder.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    int numpuls;

    @FXML
    public void handleButtonAction() {
        if (cuadro.getText().compareTo("")==0)
            numero.setText("0");
        else
            numero.setText(cuadro.getText());
    }
    public void handleKeyPressed(KeyEvent event) {
        // Verifica si se presionó la tecla Enter
        if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
            // Lógica para cuando se presiona Enter
            if (isNumeric(cuadro.getText())) {
                numero.setText(cuadro.getText());
            } else {
                numero.setText("0");
            }
        }
    }

    public void botonmas(){
        numpuls=Integer.valueOf(numero.getText());
        numpuls++;
        numero.setText(String.valueOf(numpuls));
    }
    public void botonmenos(){
        numpuls=Integer.valueOf(numero.getText());
        numpuls--;
        numero.setText(String.valueOf(numpuls));
    }
    public void botoncero(){
        numero.setText("0");
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