package view.charts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

public class PalveluajatChart extends StackPane {
        private PieChart pieChart;
        public PalveluajatChart(int w, int h) {
            super();
            pieChart = new PieChart();
            pieChart.setPrefSize(w, h);
            pieChart.setTitle("Palvelupisteissä vietetty aika");
            this.getChildren().add(pieChart);
        }
        public void updateServicePointTimeData(HashMap<String, Double> timeData) {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<String, Double> entry : timeData.entrySet()) {
                String label = entry.getKey() + " (" + String.format("%.2f", entry.getValue()) + ")";
                pieChartData.add(new PieChart.Data(label, entry.getValue()));
            }
            pieChart.getData().clear();
            pieChart.setData(pieChartData);
            pieChart.layout();
        }
    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}