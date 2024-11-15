package gestionhotel;

import gestionhotel.controller.DetallesReservaController;
import gestionhotel.controller.MainController;
import gestionhotel.controller.NuevoEditarController;
import gestionhotel.controller.ReservaController;
import gestionhotel.modelo.PersonaModelo;
import gestionhotel.modelo.ReservaModelo;
import gestionhotel.modelo.repository.PersonaRepository;
import gestionhotel.modelo.repository.impl.PersonaRepositoryImpl;
import gestionhotel.modelo.repository.impl.ReservaRepositoryImpl;
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
        ReservaModelo reservaModelo;
        ReservaRepositoryImpl reservaRepository = new ReservaRepositoryImpl();


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

    public void EditarONuevo(Persona persona) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Editar Persona");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/NuevoEditar.fxml"));
        Parent root = loader.load();
        NuevoEditarController controller2 = loader.getController();
        stage.setScene(new Scene(root, 600, 400));
        controller2.setPersona(persona);
        controller2.setPersonaModelo(modelo);
        controller2.setMainController(controller);
        stage.show();
    }
    public void EditarONuevo() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Añadir Persona");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/NuevoEditar.fxml"));
        Parent root = loader.load();
        NuevoEditarController controller2 = loader.getController();
        stage.setScene(new Scene(root, 600, 400));
        controller2.setPersona(new Persona("Introduce Dni","Introduce nombre","Introduce apellidos","Introduce Direccion","Introduce localidad","Introduce Provincia"));
        controller2.setPersonaModelo(modelo);
        controller2.setMainController(controller);
        stage.show();
    }
    public void verReserva(String dni) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Reservas");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/VistaReservas.fxml"));
        Parent root = loader.load();
        ReservaController controller2 = loader.getController();
        reservaModelo=new ReservaModelo();
        reservaModelo.setPersonaRepository(reservaRepository);
        controller2.setReservaModelo(reservaModelo);
        controller2.setDni(dni);
        controller2.setReservas();
        controller2.setData();
        controller2.setMain(this);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();

    }
    public void verDetalleReserva(Reserva reserva) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/DetallesReserva.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 400);
        DetallesReservaController controller2 = loader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        controller2.setData(reserva);

    }


    public static void main(String[] args) {
        launch(args);
    }
}

