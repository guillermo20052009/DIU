package com.example.conversorexamen.controller;

import com.example.conversorexamen.MainApp;
import javafx.fxml.FXML;

import java.io.IOException;

public class ControllerPrincipal {
    MainApp main;
    @FXML
    private void MostrarVentana() throws IOException {
    main.VentanaImagen();
    }
    public void setMainApp(MainApp main){
        this.main = main;
    }
}
