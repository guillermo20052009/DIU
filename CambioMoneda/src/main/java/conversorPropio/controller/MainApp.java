package conversorPropio.controller;

import Modelo.repository.impl.MonedaRepositoryImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

public class MainApp extends Application {

    Controller controller = new Controller();
    MonedaRepositoryImpl monedarepositoryImpl = new MonedaRepositoryImpl();

 
    @Override
    public void start (Stage primaryStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/conversorPropio/ConversorView.fxml"));
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
