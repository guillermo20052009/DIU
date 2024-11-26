package com.example.conversorexamen.controller;

import com.example.conversorexamen.ModeloConversor.ModeloConversor;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ImagenController {
    @FXML
    AnchorPane contenido;
    @FXML
    ImageView imagen;
    @FXML
    Label numero;
    ModeloConversor modeloConversor;
    IntegerProperty numeroProperty = new SimpleIntegerProperty(0);

    @FXML
    private void initialize() {
        // Verifica que los elementos en el FXML se hayan cargado correctamente

    }

    public void cargarImagen(Image image) {
    imagen.setImage(image);
    }

    public void ModeloConversor(ModeloConversor modeloConversor) {
        this.modeloConversor = modeloConversor; // Asigna el modelo de datos del catálogo.
        // Vínculo bidireccional con la propiedad totalProductos del modelo
        numeroProperty.bindBidirectional(modeloConversor.cantidadProperty());
        numero.setText(String.valueOf(numeroProperty.get()));
        // Listener para la propiedad disponible
        numeroProperty.addListener((observable, oldValue, newValue) -> {
            // Actualiza el progreso de la ProgressBar basándote en el valor disponible
            // El progreso debe estar entre 0 y 1, por lo que dividimos el valor por 50
            numero.setText(String.valueOf(newValue));

        });
    }
}
