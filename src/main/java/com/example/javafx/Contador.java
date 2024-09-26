package com.example.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Contador extends Application {
    int numeroPuls = 0;
    Label numero;
    Button btMas, btMenos, btCero;
    private void sumar(int opcion) {
            numeroPuls = (opcion == 1) ? numeroPuls + 1
                    : (opcion == -1) ? numeroPuls - 1
                    : 0;
            numero.setText(String.valueOf(numeroPuls));
        }
    @Override
    public void start(Stage escenarioPrincipal) {
        try {

            VBox botones = new VBox();

            HBox raiz = new HBox();
            raiz.setPadding(new Insets(50, 50, 50, 50));
            raiz.setSpacing(30);

            botones.getStyleClass().add("fondo");
            raiz.getStyleClass().add("fondo");
            btMas=new Button();
            btMenos=new Button();
            btCero=new Button();

            btMas.setText("+");
            btMenos.setText("-");
            btCero.setText("0");
            btCero.getStyleClass().add("btCero");


            numero = new Label();
            numero.getStyleClass().add("texto");
            numero.setText("0");
            numero.setFont(Font.font("Ani", 40));
            botones.setAlignment(Pos.CENTER);
            raiz.setAlignment(Pos.CENTER);
            btMas.setOnAction(e -> sumar(1));
            btMenos.setOnAction(e -> sumar(-1));
            btCero.setOnAction(e -> sumar(0));



            raiz.getChildren().addAll(btMas, btMenos, btCero);
            botones.getChildren().addAll(raiz,numero
            );
            Scene escena = new Scene(botones, 500, 200);
            escena.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            escenarioPrincipal.setTitle("Contador");
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
