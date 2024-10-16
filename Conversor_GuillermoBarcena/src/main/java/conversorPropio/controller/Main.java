package conversorPropio.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;


public class Main extends Application {
        public void start (Stage primaryStage){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/conversorView/conversor_view.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Conversor");
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void main (String[]args){
            launch(args);
        }
    }
