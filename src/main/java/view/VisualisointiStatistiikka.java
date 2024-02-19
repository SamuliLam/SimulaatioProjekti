package view;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class VisualisointiStatistiikka extends StackPane implements IVisualisointi {
    private final LineChart<String, Number> lineChart;
    private final CategoryAxis xAxis;
    private final NumberAxis yAxis;

    public VisualisointiStatistiikka(int w, int h) {
        super();
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        yAxis.setTickUnit(1);
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                return String.format("%d", object.intValue());
            }
        });
        lineChart = new LineChart<>(xAxis, yAxis);
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
    }


    @Override
    public void tyhjennaNaytto() {

    }

    @Override
    public void uusiAsiakas() {
        // Implement adding a new customer if needed
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}
