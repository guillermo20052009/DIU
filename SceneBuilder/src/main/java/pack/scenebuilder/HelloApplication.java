package pack.scenebuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pack.scenebuilder.controller.HelloController;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene1);
        stage.show();
        // Cargar la segunda escena
        FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Stage stage2 = new Stage();
        Scene scene2 = new Scene(fxmlLoader2.load(), 320, 240);
        stage2.setTitle("Hello!");
        stage2.setScene(scene2);
        stage2.show();
        HelloController controlador1 = fxmlLoader1.getController();
        HelloController controlador2 = fxmlLoader2.getController();
        controlador1.numeroPuls.bindBidirectional(controlador2.numeroPuls);
        //Scene scene2 = new Scene(fxmlLoader.load(), 320, 240);

    }

    public static void main(String[] args) {
        launch();
    }
}