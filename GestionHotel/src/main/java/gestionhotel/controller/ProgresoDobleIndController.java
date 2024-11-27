package gestionhotel.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.util.List;

public class ProgresoDobleIndController {

    @FXML
    private TilePane galeriaTilePane;  // Referencia al TilePane en el FXML

    @FXML
    public void initialize() {
        // Llamar al método para cargar las imágenes
        cargarImagenes();
    }

    private void cargarImagenes() {
        // Ruta de las imágenes (por ejemplo, dentro del directorio "resources/images/")
        String rutaDirectorio = "";  // Cambia esto a tu directorio

        // Listar archivos de imágenes en el directorio
        File directorio = new File(rutaDirectorio);
        File[] archivos = directorio.listFiles((dir, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg"));

        if (archivos != null) {
            for (File archivo : archivos) {
                // Crear una imagen a partir del archivo
                Image imagen = new Image(archivo.toURI().toString());

                // Crear un ImageView para la imagen y añadirlo al TilePane
                ImageView imagenView = new ImageView(imagen);
                imagenView.setFitWidth(100);  // Ajustar el tamaño de la imagen
                imagenView.setFitHeight(100); // Ajustar el tamaño de la imagen
                imagenView.setPreserveRatio(true);  // Mantener la proporción de la imagen

                // Añadir la imagen al TilePane
                galeriaTilePane.getChildren().add(imagenView);
            }
        }
    }
}

