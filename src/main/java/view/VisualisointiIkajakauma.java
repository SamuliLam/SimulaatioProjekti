package view;

import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class VisualisointiIkajakauma extends StackPane implements IVisualisointi {
    private final LineChart<String, Number> lineChart;
    private final CategoryAxis xAxis;
    private final NumberAxis yAxis;

    public VisualisointiIkajakauma(int w, int h) {
        super();
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        yAxis.setLabel("Esiintymiä");

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.getXAxis().setLabel("Ikä");
        lineChart.setPrefSize(w, h);
        this.getChildren().add(lineChart);
    }

    // ikäjakauma chartti
    public void updateAgeDistributionData(HashMap<Integer, Integer> ageDistribution) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Age Distribution");

        // Ikäjärjestykseen
        TreeMap<Integer, Integer> sortedAgeDistribution = new TreeMap<>(ageDistribution);

        for (Map.Entry<Integer, Integer> entry : sortedAgeDistribution.entrySet()) {
            series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
        }

        lineChart.getData().clear();
        lineChart.getData().add(series);

        // Calculate the minimum and maximum Y-axis values
        int minYValue = ageDistribution.values().stream().mapToInt(Integer::intValue).min().orElse(0);
        int maxYValue = ageDistribution.values().stream().mapToInt(Integer::intValue).max().orElse(10);

        // Set the lower and upper bounds for the Y-axis
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(minYValue-1);
        yAxis.setUpperBound(maxYValue+1);
        yAxis.setTickUnit(1); // Set the tick unit to 1 to display only integer values
    }
    @Override
    public void tyhjennaNaytto() {

    }
    @Override
    public void uusiAsiakas() {
        // Implement adding a new customer if needed
    }
    @Override
    public void asiakasPoistuu() {
        // Implement removing a customer if needed
    }
    @Override
    public void updateMeatDepActivity(boolean isReserved) {
        // Implement updating the meat department queue if needed
    }
    @Override
    public void updateBeerDepActivity(boolean isReserved) {
        // Implement updating the beer department queue if needed
    }
    @Override
    public void updateFishDepActivity(boolean isReserved) {
        // Implement updating the fish department queue if needed
    }
    @Override
    public void updateCandyDepActivity(boolean isReserved) {
        // Implement updating the fish department queue if needed
    }
    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }


}
