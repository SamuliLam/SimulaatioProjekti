package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import simu.model.Palvelupiste;

import java.util.HashMap;
import java.util.Map;

public class VisualisointiPalveluajat extends StackPane implements IVisualisointi {

        private PieChart pieChart;

        public VisualisointiPalveluajat(int w, int h) {
            super();
            pieChart = new PieChart();
            pieChart.setPrefSize(w, h);
            pieChart.setTitle("Palvelupisteissä vietetty aika");
            this.getChildren().add(pieChart);
        }

        // piechart palvelupisteistä
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
    public void tyhjennaNaytto() {

    }
    @Override
    public void uusiAsiakas() {
    }
    @Override
    public void asiakasPoistuu() {
        // Implement removing a customer if needed
    }
    @Override
    public void updateMeatDepActivity(boolean isReserved) {
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