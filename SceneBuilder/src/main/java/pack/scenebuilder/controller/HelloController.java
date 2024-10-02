package pack.scenebuilder.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    public Label numero;
    public TextField cuadro;

    @FXML
    public void handleButtonAction() {
        if (cuadro.getText().compareTo("")==0)
            numero.setText("0");
        else
            numero.setText(cuadro.getText());
    }
    public void handleKeyPressed() {
        if (cuadro.getText().compareTo("")==0)
            numero.setText("0");
        else
            numero.setText(cuadro.getText());
    }
}