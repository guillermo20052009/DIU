package Agenda;

import Agenda.controller.ControllerDialogo;
import Agenda.controller.ControllerPersonas;
import Agenda.controller.ControllerPrincipal;
import Agenda.controller.CumpleañosController;
import Agenda.modelo.AgendaModelo;
import Agenda.modelo.ExcepcionPersona;
import Agenda.modelo.repository.PersonaRepository;
import Agenda.modelo.repository.impl.PersonaRepositoryImpl;
import Agenda.util.PersonaUtil;
import Modelo.ExcepcionMoneda;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;


public class Main extends Application {
    PersonaRepositoryImpl personaRepository = new PersonaRepositoryImpl();
    AgendaModelo modelo = new AgendaModelo();
    ControllerPersonas controller;
    BorderPane rootLayout;
    public ObservableList<Persona> personas;
    private Stage primaryStage;

    @Override
    public void start (Stage primaryStage) throws ExcepcionMoneda {
        try {
            // Cargar el archivo Principal.fxml
            this.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Agenda/Principal.fxml"));
            rootLayout = (BorderPane) loader.load();  // Asumimos que Principal.fxml tiene un BorderPane como raíz

            Scene scene = new Scene(rootLayout);  // Usamos rootLayout para la escena
            this.primaryStage.setScene(scene);
            this.primaryStage.setTitle("Agenda");
            ControllerPrincipal controllerPrincipal = loader.getController();
            controllerPrincipal.setMainApp(this);

            // Mostrar la vista de personas
            modelo.SetConversorModelo(personaRepository);
            showPersonaOverview();
            this.primaryStage.show();
            personas = FXCollections.observableArrayList(controller.getPersonas());
            controller.setData(personas);
            controller.setMain(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } catch (ExcepcionPersona e) {
            throw new RuntimeException(e);
        }
    }
    public void showPersonEditDialog(Persona person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/Agenda/Dialogo.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar persona");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ControllerDialogo controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            this.controller.setclick(true);

        } catch (IOException e) {
            e.printStackTrace();
            controller.setclick(false);
        }
    }
    public void showBirthdayStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/Agenda/Cumpleaños.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the persons into the controller.
            CumpleañosController controller = loader.getController();
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reiniciarlista() throws ExcepcionPersona {
        this.controller.setPersona();
        personas.clear();
        personas = FXCollections.observableArrayList(this.controller.getPersonas());
        this.controller.setData(personas);
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
