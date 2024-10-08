package com.example.javafx;

import javafx.application.Application;
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

        // Vincular las propiedades de numeroPuls de ambos contadores bidireccionalmente
        contador1.numeroPulsProperty().bindBidirectional(contador2.numeroPulsProperty());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
