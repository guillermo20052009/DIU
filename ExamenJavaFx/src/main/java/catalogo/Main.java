package catalogo;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;

public class Main extends Application {

    public MainApp() {
        catalogoModel = new CatalogoModel();
        ArticuloRepositoryImpl agendaRepository = new ArticuloRepositoryImpl();
        try {
            catalogoModel.setArticuloRepository(agendaRepository);
            articuloData.addAll(catalogoModel.setArticulos());
            // Establecer el progreso inicial
            updateProgress();

            articuloData.addListener((ListChangeListener<Articulo>) change -> {
                while (change.next()) {
                    updateProgress();
                }
            });


        } catch (ExcepcionArticulo e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("No se ha conectar a la base de datos para la agenda");
            alert.setContentText("Por favor, inicie el servidor con la base de datos");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }
}
