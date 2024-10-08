package com.example.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Paginacion extends Application {

    private Pagination paginacion;

    private VBox creaPagina(int indice) {
        VBox raizPagina = new VBox(10);
        raizPagina.setPadding(new Insets(0, 0, 10, 0));
        if (indice == 0) {
            Image imagen = new Image("file:resources/images/kiko.jpg", 100, 100, true, true);
            raizPagina.getChildren().add(new ImageView(imagen));
            return raizPagina;
        } else if (indice == 1) {
            Image imagen2 = new Image("file:resources/images/kiko.jpg", 100, 100, true, true);
            raizPagina.getChildren().add(new ImageView(imagen2));
            return raizPagina;
        } else
            return null;
    }

    @Override
    public void start(Stage escenarioPrincipal) {
        try {
            VBox raiz = new VBox(10);
            raiz.setPadding(new Insets(20, 20, 20, 20));

            paginacion = new Pagination(2, 1);
            paginacion.setPageFactory((Integer indicePagina) -> creaPagina(indicePagina));

            raiz.getChildren().addAll(paginacion);

            Scene escena = new Scene(raiz, 240, 290);
            escenarioPrincipal.setTitle("Paginación");
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