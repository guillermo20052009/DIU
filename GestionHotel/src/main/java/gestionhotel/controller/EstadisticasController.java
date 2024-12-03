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
            this.meses=reservaModelo.contarReservasPorMes(opcion);

            setDatosGrafico();
        }


    public void setDatosGrafico() {
        if (xAxis == null || yAxis == null) {
            System.err.println("Error: xAxis o yAxis no están inicializados.");
            return;
        }

        // Limpiar datos previos y establecer etiquetas de los ejes
        limpiarDatosPrevios();
        establecerEtiquetasEjes();

        // Crear y agregar los datos al gráfico
        agregarDatosAlGrafico();

        // Establecer estilo del gráfico
        aplicarEstiloGrafico();

        // Ajustar el rango del eje Y y tamaño del gráfico
        actualizarRangoEjeY();
        ajustarTamanioGrafico();
    }

    private void limpiarDatosPrevios() {
        barChart.getData().clear();
    }

    private void establecerEtiquetasEjes() {
        xAxis.setLabel("Mes");
        yAxis.setLabel("Habitaciones Alquiladas");
    }

    private void agregarDatosAlGrafico() {
        String[] nombresMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Habitaciones Alquiladas");

        for (int i = 0; i < nombresMeses.length; i++) {
            series.getData().add(new XYChart.Data<>(nombresMeses[i], meses[i]));
        }

        barChart.getData().add(series);
    }

    private void aplicarEstiloGrafico() {
        barChart.lookupAll(".chart-bar").forEach(node -> node.setStyle("-fx-bar-fill: #4c82af;"));
    }

    private void ajustarTamanioGrafico() {
        barChart.setPrefSize(800, 400);
    }

    public void actualizarRangoEjeY() {
        // Encontrar el máximo en los datos
        int maxReservas = obtenerMaximoReservas();

        // Ajustar el límite superior dinámicamente
        yAxis.setLowerBound(0); // El límite inferior siempre es 0
        yAxis.setUpperBound(maxReservas + 1); // Límite superior ajustado (+1 para margen)
        yAxis.setTickUnit(1); // Asegurar que las marcas incrementen de 1 en 1
    }

    private int obtenerMaximoReservas() {
        int max = 0;
        for (int reservas : meses) {
            if (reservas > max) {
                max = reservas;
            }
        }
        return max;
    }

}
