package gestionhotel;

import gestionhotel.controller.*;
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
        ReservaController controllerReserva;


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
        stage.setScene(new Scene(root, 450, 400));
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
        stage.setScene(new Scene(root, 450, 400));
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
        controllerReserva = loader.getController();
        reservaModelo=new ReservaModelo();
        reservaModelo.setPersonaRepository(reservaRepository);
        controllerReserva.setReservaModelo(reservaModelo);
        controllerReserva.setDni(dni);
        controllerReserva.setReservas();
        controllerReserva.setData();
        controllerReserva.setMain(this);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();

    }
    public void verDetalleReserva(Reserva reserva) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/DetallesReserva.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 475, 375);
        DetallesReservaController controller2 = loader.getController();
        Stage stage = new Stage();
        controller2.ReservaModelo(reservaModelo);
        controller2.setReservaController(controllerReserva);
        controller2.setMain(this);
        stage.setScene(scene);
        stage.show();
        controller2.setData(reserva);
    }
    public void AñadirReservaEditar(String dni) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/NuevoEditarReserva.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 500);
        NuevoEditarReserva controller2 = loader.getController();
        Stage stage = new Stage();
        controller2.setDniTextField(dni);
        controller2.setReservaModelo(reservaModelo);
        controller2.setReservaController(controllerReserva);
        stage.setScene(scene);
        stage.show();
    }
    public void AñadirReservaEditar(Reserva reserva) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/NuevoEditarReserva.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 500);
        NuevoEditarReserva controller2 = loader.getController();
        Stage stage = new Stage();
        controller2.setData(reserva);
        controller2.setReservaModelo(reservaModelo);
        controller2.setReservaController(controllerReserva);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

