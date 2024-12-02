package gestionhotel.controller;

import gestionhotel.modelo.ReservaModelo;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class EstadisticasController {

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private ReservaModelo reservaModelo;
    private int[] meses;


    public void setReservaModelo(ReservaModelo reservaModelo) {
        this.reservaModelo = reservaModelo;
    }


    public void setMeses(String opcion) {
        switch (opcion) {
            case "Doble":
                this.meses = reservaModelo.contarReservasPorMesDobles();
                break;

            case "Doble Individual":
                this.meses = reservaModelo.contarReservasPorMesDoblesInd();
                break;

            case "Junior Suite":
                this.meses = reservaModelo.contarReservasPorMesJSuite();
                break;

            case "Suite":
                this.meses = reservaModelo.contarReservasPorMesSuites();
                break;

            default:
                throw new IllegalArgumentException("Opción no válida: " + opcion);
        }
        setDatosGrafico();
    }


    public void setDatosGrafico() {
        if (xAxis != null && yAxis != null) {
            // Limpiar datos previos
            barChart.getData().clear();

            // Títulos de los ejes
            xAxis.setLabel("Mes");
            yAxis.setLabel("Habitaciones Alquiladas");

            // Array con los nombres de los meses
            String[] nombresMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

            // Crear una nueva serie de datos
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Habitaciones Alquiladas");

            // Rellenar los datos en la serie
            for (int i = 0; i < nombresMeses.length; i++) {
                series.getData().add(new XYChart.Data<>(nombresMeses[i], meses[i]));
            }

            // Agregar la serie al gráfico
            barChart.getData().add(series);

            // Estilo CSS (recomendado usar un archivo externo)
            barChart.lookupAll(".chart-bar").forEach(node -> {
                node.setStyle("-fx-bar-fill: #4c82af;"); // Color azul
            });

            // Ajustar el tamaño del gráfico
            barChart.setPrefSize(800, 400);
        } else {
            System.err.println("Error: xAxis o yAxis no están inicializados.");
        }
    }
}
