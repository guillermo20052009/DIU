package com.example.javafx;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Contador extends Application {
    // Usamos IntegerProperty en lugar de un int simple
    private IntegerProperty numeroPuls = new SimpleIntegerProperty(0);
    Label numero;
    Button btMas, btMenos, btCero;

    // Método para modificar el valor del contador
    private void sumar(int opcion) {
        // Actualizamos el valor de IntegerProperty en función de la opción
        if (opcion == 1) {
            numeroPuls.set(numeroPuls.get() + 1);
        } else if (opcion == -1) {
            numeroPuls.set(numeroPuls.get() - 1);
        } else {
            numeroPuls.set(0);
        }
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
            btMas = new Button();
            btMenos = new Button();
            btCero = new Button();

            btMas.setText("+");
            btMenos.setText("-");
            btCero.setText("0");
            btCero.getStyleClass().add("btCero");

            numero = new Label();
            numero.getStyleClass().add("texto");
            numero.setFont(Font.font("Ani", 40));

            // Vinculamos la propiedad del número a la etiqueta usando un listener
            numeroPuls.addListener((obs, oldVal, newVal) -> numero.setText(String.valueOf(newVal)));

            // Inicialmente establecemos el texto de la etiqueta al valor de numeroPuls
            numero.setText(String.valueOf(numeroPuls.get()));

            botones.setAlignment(Pos.CENTER);
            raiz.setAlignment(Pos.CENTER);

            btMas.setOnAction(e -> sumar(1));
            btMenos.setOnAction(e -> sumar(-1));
            btCero.setOnAction(e -> sumar(0));

            raiz.getChildren().addAll(btMas, btMenos, btCero);
            botones.getChildren().addAll(raiz, numero);

            Scene escena = new Scene(botones, 500, 200);
            escena.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            escenarioPrincipal.setTitle("Contador");
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IntegerProperty numeroPulsProperty() {
        return numeroPuls;
    }
}
