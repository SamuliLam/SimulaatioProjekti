package view;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import simu.model.TapahtumanTyyppi;

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
        for (TapahtumanTyyppi category : soldProducts.keySet()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(category.getPalvelupiste());
            HashMap<String, Integer> products = soldProducts.get(category);
            for (String product : products.keySet()) {
                series.getData().add(new XYChart.Data<>(product, products.get(product)));
            }
            barChart.getData().add(series);
        }
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}