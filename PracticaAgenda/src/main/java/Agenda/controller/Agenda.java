package Agenda.controller;

import java.io.IOException;

import Agenda.Modelo.Persona;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import Agenda.controller.PersonOverviewController;

public class Agenda extends Application {

    private ObservableList<Persona> Personas = FXCollections.observableArrayList();
    private Stage primaryStage;
    private BorderPane rootLayout;

    public Agenda() {
        Personas.add(new Persona("Hans", "Muster"));
        Personas.add(new Persona("Ruth", "Mueller"));
        Personas.add(new Persona("Heinz", "Kurz"));
        Personas.add(new Persona("Cornelia", "Meier"));
        Personas.add(new Persona("Werner", "Meyer"));
        Personas.add(new Persona("Lydia", "Kunz"));
        Personas.add(new Persona("Anna", "Best"));
        Personas.add(new Persona("Stefan", "Meier"));
        Personas.add(new Persona("Martin", "Mueller"));
    }
    public void showPersonaOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Agenda.class.getResource("/Agenda/Personas.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        showPersonaOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Agenda.class.getResource("/Agenda/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the Persona overview inside the root layout.
     */


    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public ObservableList<Persona> getPersonData() {
        return Personas;
    }

    public static void main(String[] args) {
        launch(args);
    }
}