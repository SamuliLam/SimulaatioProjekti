package view;

import dao.AsiakasOstoslistaDAO;
import datasource.MariaDbConnection;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import simu.model.OmaMoottori;
import simu.model.TapahtumanTyyppi;

import java.sql.SQLException;
import java.util.HashMap;

public class VisualisointiTuotteet extends StackPane {
    private BarChart<String, Number> barChart;
    private final CategoryAxis xAxis;
    private final NumberAxis yAxis;
    private final int w;
    private final int h;


    public VisualisointiTuotteet(int w, int h) {
        super();
        this.w = w;
        this.h = h;
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        createBarChart();
        this.getChildren().add(barChart);
    }

    public void createBarChart() {
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Add this line
        barChart.getXAxis().setLabel("Tuote");
        barChart.getYAxis().setLabel("Myynti");
        barChart.setCategoryGap(20);
        yAxis.setTickUnit(1);
        // Adjust this value as needed
    }

    public void updateSoldProductsData(HashMap<TapahtumanTyyppi, HashMap<String, Integer>> soldProducts) {
        barChart.getData().clear();

        AsiakasOstoslistaDAO dao = new AsiakasOstoslistaDAO(MariaDbConnection.getConnection()); // Assuming you have an instance of your DAO class
        try {
            // Get sold products from the database
            HashMap<String, Integer> soldProductsData = dao.getSoldProducts(OmaMoottori.getSimulationRunNumber());

            // Create a series for the bar chart
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Sold Products");

            // Add data to the series from the sold products HashMap
            for (String product : soldProductsData.keySet()) {
                series.getData().add(new XYChart.Data<>(product, soldProductsData.get(product)));
            }

            // Add the series to the bar chart
            barChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's requirements
        }
    }


    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}