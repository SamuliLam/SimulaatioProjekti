package view;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import simu.model.TapahtumanTyyppi;

import java.util.Collections;
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
        barChart.setPrefSize(w, h);
        barChart.getXAxis().setLabel("Tuote");
        barChart.getYAxis().setLabel("Myynti");
        barChart.setCategoryGap(50);  // Adjust this value as needed

        // Set the tick unit to 1.0 to display only integers on the y-axis
        yAxis.setTickUnit(1.0);

        // Set a custom label formatter to display only integers
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                // Format the tick label as an integer
                return String.format("%d", object.intValue());
            }
        });
    }


    public void updateSoldProductsData(HashMap<TapahtumanTyyppi, HashMap<String, Integer>> soldProducts) {
        barChart.getData().clear();

        int minYValue = 1;
//                soldProducts.values().stream()
//                .flatMap(products -> products.values().stream())
//                .mapToInt(Integer::intValue)
//                .min()
//                .orElse(0);

        int maxYValue = soldProducts.values().stream()
                .flatMap(products -> products.values().stream())
                .mapToInt(Integer::intValue)
                .max()
                .orElse(10);

        // Set the lower and upper bounds for the Y-axis
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(minYValue - 1);
        yAxis.setUpperBound(maxYValue + 1);
        yAxis.setTickUnit(1); // Set the tick unit to 1 to display only integer values

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