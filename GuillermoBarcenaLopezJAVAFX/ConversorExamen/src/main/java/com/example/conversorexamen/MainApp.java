package com.example.conversorexamen;

import Modelo.ExcepcionMoneda;
import Modelo.repository.MonedaRepository;
import Modelo.repository.impl.MonedaRepositoryImpl;
import com.example.conversorexamen.ModeloConversor.ModeloConversor;
import com.example.conversorexamen.controller.ControllerPrincipal;
import com.example.conversorexamen.controller.ControllerVistaPrincipal;
import com.example.conversorexamen.controller.ImagenController;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainApp extends Application {

    private static ObservableList<Moneda> monedas = FXCollections.observableArrayList();
    private static Stage primaryStage;
    private BorderPane principal;
    private ModeloConversor modeloConversor;
    private MonedaRepositoryImpl monedaRepository = new MonedaRepositoryImpl();


    public MainApp() throws ExcepcionMoneda {
        modeloConversor = new ModeloConversor();
        modeloConversor.setRepository(monedaRepository);
        monedas.addAll(modeloConversor.obtenerListaMonedas());
        System.out.println(monedas);

    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Articulos");
        Principal();
        vistaConversor();
    }
    public ObservableList<Moneda> getArticulos() {
        return monedas;
    }
    public void Principal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/com/example/conversorexamen/Principal.fxml"));
            principal = (BorderPane) loader.load();
            Scene scene = new Scene(principal);
            ControllerPrincipal controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void vistaConversor() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/com/example/conversorexamen/VistaPrincipal.fxml"));
            AnchorPane vistaArticulos = (AnchorPane) loader.load();
            principal.setCenter(vistaArticulos);
            ControllerVistaPrincipal controller = loader.getController();
            controller.setMainApp(this);
            controller.setMonedas(new ArrayList<>(monedas));
            controller.setModeloConversor(modeloConversor);
            modeloConversor.obtenerListaMonedas();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExcepcionMoneda e) {
            throw new RuntimeException(e);
        }
    }
    public void VentanaImagen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/com/example/conversorexamen/VentanaImagen.fxml"));
        Parent root = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Editar Artículo");
        dialogStage.initModality(Modality.NONE);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(root, 600, 500);
        dialogStage.setScene(scene);
        ImagenController controller = loader.getController();
        Image imagen = new Image("file:resources/images/moneda.jpg", 500, 500, true, true);
        controller.cargarImagen(imagen);
        controller.ModeloConversor(modeloConversor);

        dialogStage.showAndWait();
    }
}

