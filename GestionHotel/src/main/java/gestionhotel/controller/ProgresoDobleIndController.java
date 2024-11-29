package gestionhotel.controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ProgresoDobleIndController {

    @FXML
    private TilePane galeriaTilePane;  // Referencia al TilePane en el FXML
    @FXML
    Button botonSiguiente;
    @FXML
    Button botonCerrar;

    @FXML
    private ImageView imagenView; // ImageView para mostrar la imagen actual

    private int imagenIndex = 0; // Índice para navegar entre las imágenes
    private final String[] imagenes = {
            "file:resources/imagen2.jpg",
            "file:resources/imagen3.jpg",
            "file:resources/imagen4.jpg"
    };

    @FXML
    public void initialize() {
        // Llamar al metodo para cargar la primera imagen
        cargarImagen();

        // Iniciar la animación para cambiar automáticamente la imagen cada 5 segundos
        iniciarCambioAutomatico();

        // Configurar el botón para cambiar la imagen manualmente
        configurarBotonSiguiente();
    }

    private void cargarImagen() {
        // Cargar la imagen en el ImageView
        Image imagen = new Image(imagenes[imagenIndex]);
        imagenView.setImage(imagen);
        imagenView.setFitWidth(850);  // Ajustar el tamaño de la imagen
        imagenView.setFitHeight(850); // Ajustar el tamaño de la imagen
    }

    private void iniciarCambioAutomatico() {
        // Crear una animación para cambiar la imagen cada 5 segundos
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> cambiarImagen())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);  // Ciclo infinito
        timeline.play();  // Iniciar la animación
    }
    @FXML
    private void cerrarVentana() {
        // Cerrar la ventana actual
        Stage stage = (Stage) botonCerrar.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void cambiarImagen() {
        // Incrementar el índice de la imagen
        imagenIndex = (imagenIndex + 1) % imagenes.length; // Regresa al inicio al llegar al final

        // Crear una transición de desvanecimiento
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.7), imagenView);
        fadeTransition.setFromValue(1); // Comienza visible
        fadeTransition.setToValue(0);   // Termina invisible

        fadeTransition.setOnFinished(e -> {
            cargarImagen(); // Cargar la nueva imagen
            // Hacer un fade in después de cambiar la imagen
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.7), imagenView);
            fadeIn.setFromValue(0);    // Comienza invisible
            fadeIn.setToValue(1);      // Termina visible
            fadeIn.play();             // Reproducir la transición de entrada
        });

        fadeTransition.play(); // Reproducir la transición de salida
    }


    private void configurarBotonSiguiente() {
        // Configurar el botón para cambiar la imagen manualmente
        // Este código depende de cómo tengas el botón en tu FXML, pero lo puedes hacer así:
        botonSiguiente.setOnAction(e -> cambiarImagen());
    }
}
