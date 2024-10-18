package conversorPropio;

import Modelo.ExcepcionMoneda;
import Modelo.MonedaVO;
import Modelo.repository.impl.MonedaRepositoryImpl;
import conversorPropio.controller.Controller;
import conversorPropio.modelo.ConversorModelo;
import eu.hansolo.toolbox.properties.FloatProperty;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

import static javafx.application.Application.launch;


public class MainApp extends Application {

    MonedaRepositoryImpl monedarepositoryImpl;
    ConversorModelo modelo;
    Controller controller;

    @Override
    public void start (Stage primaryStage) throws  ExcepcionMoneda{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conversorPropio/ConversorView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Conversor");
            monedarepositoryImpl = new MonedaRepositoryImpl();
            modelo = new ConversorModelo();
            Controller controller = loader.getController();
            modelo.SetConversorModelo(monedarepositoryImpl);
            controller.setController(modelo);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main (String[]args){
        launch(args);
    }
}

