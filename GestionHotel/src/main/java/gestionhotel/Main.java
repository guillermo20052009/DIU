package gestionhotel;

import gestionhotel.controller.*;
import gestionhotel.modelo.PersonaModelo;
import gestionhotel.modelo.ReservaModelo;
import gestionhotel.modelo.repository.impl.PersonaRepositoryImpl;
import gestionhotel.modelo.repository.impl.ReservaRepositoryImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


//La clase Main será la clase principal del Programa donde Abriremos las ventanas e inyectaremos los datos necesarios en los controladores y modelos

public class Main extends Application {
        MainController controller;
        PersonaModelo modelo;
        PersonaRepositoryImpl personaRepository = new PersonaRepositoryImpl();
        ReservaModelo reservaModelo;
        ReservaRepositoryImpl reservaRepository = new ReservaRepositoryImpl();
        ReservaController controllerReserva;

//Iniciar Pantalla Principal
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

        reservaModelo=new ReservaModelo();
        reservaModelo.setReservaRepository(reservaRepository);


        modelo.setPersonaRepository(personaRepository);
        controller.setPersonaModelo(modelo);
        try{
            controller.setPersonas();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR,"No se pudo conectar a la base de datos");
            alert.setTitle("Se produjo un error");
            alert.show();
        }

        controller.setMain(this);
    }

// Iniciar Pantalla de edicion para un cliente existente
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


// Iniciar Pantalla Para añadir cliente nuevo, es la misma que la anterior, pero cambian los datos de los editText
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

//Iniciar Pantalla con las reservas de un cliente
    public void verReserva(String dni) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Reservas");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/VistaReservas.fxml"));
        Parent root = loader.load();
        controllerReserva = loader.getController();
        controllerReserva.setReservaModelo(reservaModelo);
        controllerReserva.setDni(dni);
        controllerReserva.setReservas();
        controllerReserva.setData();
        controllerReserva.setMain(this);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();

    }


// En la anterior pantalla se ven todas las reservas y esta contendrá los detalles de la que pulsemos
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

// En esta pantalla se podrán añadir nuevas reservas
    public void AñadirReservaEditar(String dni) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/NuevoEditarReserva.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 700, 500);
        NuevoEditarReserva controller2 = loader.getController();
        Stage stage = new Stage();
        controller2.setDniTextField(dni);
        controller2.setReservaModelo(reservaModelo);
        controller2.setReservaController(controllerReserva);
        stage.setScene(scene);
        stage.show();
    }

// La misma que la anterior, pero con los datos de la reserva que queremos editar
    public void AñadirReservaEditar(Reserva reserva) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/NuevoEditarReserva.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 700, 500);
        NuevoEditarReserva controller2 = loader.getController();
        Stage stage = new Stage();
        controller2.setData(reserva);
        controller2.setReservaModelo(reservaModelo);
        controller2.setReservaController(controllerReserva);
        stage.setScene(scene);
        stage.show();
    }

// Se encarga de abrir la ventana de progreso de ocupación de las habitaciones
    public void Progreso(String opcion) throws IOException {
        String[] imagenes=seleccionarImagenes(opcion);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/ProgresoDobleInd.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 800);
        ProgresoDobleIndController controller2 = loader.getController();
        controller2.setImagenes(imagenes);
        reservaModelo.contarOcupadas();
        controller2.setReservaModelo(reservaModelo,opcion);
        controller2.setTitle(opcion);
        Stage stage = new Stage();
        stage.setTitle("Progreso");
        stage.setScene(scene);
        stage.show();
    }

    // Decide las fotos que vamos a mostrar según la opcion del menú que hayamos pulsado
    private String[] seleccionarImagenes(String opcion) throws IOException {
        return opcion.equals("Doble") ? new String[]{
                "file:resources/imagen2.jpg",
                "file:resources/imagen3.jpg",
                "file:resources/imagen4.jpg"
        } : opcion.equals("Doble Individual") ? new String[]{
                "file:resources/imagen10.jpg",
                "file:resources/imagen11.jpg",
                "file:resources/imagen12.jpg"
        } : opcion.equals("Junior Suite") ? new String[]{
                "file:resources/imagen5.jpg",
                "file:resources/imagen6.jpg",
                "file:resources/imagen7.jpg"
        } : new String[]{
                "file:resources/imagen8.jpg",
                "file:resources/imagen9.jpg",
                "file:resources/imagen13.jpg"
        };
    }

    public void Estadisticas(String opcion) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/Estadisticas.fxml"));
        Parent root = loader.load();
        EstadisticasController controller2 = loader.getController();
        controller2.setReservaModelo(reservaModelo);
        controller2.setMeses(opcion);
        Scene scene = new Scene(root, 800, 500);
        Stage stage = new Stage();
        stage.setTitle(opcion);
        stage.setScene(scene);
        stage.show();
    }

    public void paginaWeb() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionhotel/WebView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 550);
        Stage stage = new Stage();
        stage.setTitle("Progreso");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

