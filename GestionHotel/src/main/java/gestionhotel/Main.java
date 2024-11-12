package gestionhotel;

import gestionhotel.controller.MainController;
import gestionhotel.controller.NuevoEditarController;
import gestionhotel.modelo.PersonaModelo;
import gestionhotel.modelo.repository.PersonaRepository;
import gestionhotel.modelo.repository.impl.PersonaRepositoryImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
        controller.setMain(this);
    }

    public Persona EditarONuevo(Persona persona) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Editar Persona");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/NuevoEditar.fxml"));
        Parent root = loader.load();
        NuevoEditarController controller2 = loader.getController();
        stage.setScene(new Scene(root, 600, 400));
        controller2.setPersona(persona);
        controller2.setPersonaModelo(modelo);
        stage.show();
        return persona;
    }
    public void EditarONuevo(){

    }

    public static void main(String[] args) {
        launch(args);
    }
}

