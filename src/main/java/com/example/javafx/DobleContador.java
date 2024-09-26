package com.example.javafx;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.Stage;

public class DobleContador extends Application {

    // Instancias de la clase Contador

    Contador contador1 = new Contador();
    Contador contador2 = new Contador();
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Crear y mostrar el primer contador

        Stage stage1 = new Stage();
        contador1.start(stage1);

        // Crear y mostrar el segundo contador
        Stage stage2 = new Stage();
        contador2.start(stage2);
        //contador1.numero.textProperty().bindBidirectional(contador2.numero.textProperty());

    }

    public static void main(String[] args) {
        launch(args);
    }
}