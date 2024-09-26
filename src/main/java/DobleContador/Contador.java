package DobleContador;

import javafx.application.Application;
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
    private int numeroPuls = 0;
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

            // Crear un contenedor horizontal (HBox) para los botones
            HBox raiz = new HBox();
            raiz.setPadding(new Insets(50, 50, 50, 50));
            raiz.setSpacing(30);

            // Añadir clases CSS (verifica que estas clases existan en tu style.css)
            botones.getStyleClass().add("fondo");
            raiz.getStyleClass().add("fondo");

            // Inicialización de los botones
            btMas = new Button("+");
            btMenos = new Button("-");
            btCero = new Button("0");
            btCero.getStyleClass().add("btCero");  // Clase CSS para el botón "0"

            // Inicialización de la etiqueta (label) para mostrar el número
            numero = new Label("0");
            numero.getStyleClass().add("texto");  // Clase CSS para el texto
            numero.setFont(Font.font("Ani", 40));  // Tamaño y fuente del texto

            // Alineación de los contenedores
            botones.setAlignment(Pos.CENTER);
            raiz.setAlignment(Pos.CENTER);

            // Configuración de los eventos de los botones
            btMas.setOnAction(e -> sumar(1));
            btMenos.setOnAction(e -> sumar(-1));
            btCero.setOnAction(e -> sumar(0));

            // Añadir los botones al contenedor horizontal (raiz)
            raiz.getChildren().addAll(btMas, btMenos, btCero);

            // Añadir la etiqueta (numero) al contenedor vertical (botones)
            botones.getChildren().addAll(raiz, numero);

            // Crear la escena con el contenedor principal
            Scene escena = new Scene(botones, 500, 200);

            // Añadir la hoja de estilos CSS
            escena.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            // Configuración del escenario principal
            escenarioPrincipal.setTitle("Contador");
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.show();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}