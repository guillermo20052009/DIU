package gestionhotel;

import gestionhotel.controller.MainController;
import gestionhotel.modelo.PersonaModelo;
import gestionhotel.modelo.repository.PersonaRepository;
import gestionhotel.modelo.repository.impl.PersonaRepositoryImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
        MainController controller;
        PersonaModelo modelo;
        PersonaRepositoryImpl personaRepository = new PersonaRepositoryImpl();


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/Main_view.fxml"));
        Parent root = loader.load();
        // Obtener el controlador del FXMLLoader
        controller = loader.getController();
        primaryStage.setTitle("Usuarios");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        modelo=new PersonaModelo();
        modelo.setPersonaRepository(personaRepository);
        controller.setPersonaModelo(modelo);
        controller.setPersonas();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
