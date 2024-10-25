package Agenda;

import Agenda.controller.ControllerPersonas;
import Agenda.modelo.AgendaModelo;
import Agenda.modelo.ExcepcionPersona;
import Agenda.modelo.repository.PersonaRepository;
import Agenda.modelo.repository.impl.PersonaRepositoryImpl;
import Modelo.ExcepcionMoneda;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;


public class Main extends Application {
    PersonaRepositoryImpl personaRepository = new PersonaRepositoryImpl();
    AgendaModelo modelo = new AgendaModelo();
    ControllerPersonas controller;
    BorderPane rootLayout;
    ObservableList<Persona> personas;

    @Override
    public void start (Stage primaryStage) throws ExcepcionMoneda {
        try {
            // Cargar el archivo Principal.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Agenda/Principal.fxml"));
            rootLayout = (BorderPane) loader.load();  // Asumimos que Principal.fxml tiene un BorderPane como raíz

            Scene scene = new Scene(rootLayout);  // Usamos rootLayout para la escena
            primaryStage.setScene(scene);
            primaryStage.setTitle("Agenda");

            // Mostrar la vista de personas
            modelo.SetConversorModelo(personaRepository);
            showPersonaOverview();
            primaryStage.show();
            personas = FXCollections.observableArrayList(controller.getPersonas());
            System.out.println(personas);
            controller.setData(personas);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } catch (ExcepcionPersona e) {
            throw new RuntimeException(e);
        }
    }

    public void showPersonaOverview() {
        try {
            // Cargar Personas.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Agenda/Personas.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Establecer el AnchorPane en el centro del BorderPane rootLayout
            rootLayout.setCenter(personOverview);

            // Si necesitas interactuar con el controlador:
            controller = loader.getController();
            controller.setController(modelo);
            controller.setPersona();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExcepcionPersona e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
