package com.example.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Acordeon extends Application {
    @Override
    public void start(Stage escenarioPrincipal) {
        try {
            Accordion raiz = new Accordion();
            raiz.setPadding(new Insets(20, 20, 20, 20));

            Image imagen = new Image("file:resources/images/kiko.jpg", 100, 100, true, true);
            Image imagen2 = new Image("file:resources/images/kiko.jpg", 100, 100, true, true);

            TitledPane tlpClaro = new TitledPane("Logo claro", new ImageView(imagen));
            TitledPane tlpOscuro = new TitledPane("Logo oscuro", new ImageView(imagen2));

            raiz.getPanes().addAll(tlpClaro, tlpOscuro);
            raiz.setExpandedPane(tlpOscuro);

            Scene escena = new Scene(raiz, 250, 300);
            escenarioPrincipal.setTitle("Acordeón");
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}