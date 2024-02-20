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
        public void updateServicePointTimeData(HashMap<Palvelupiste, Double> timeData) {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for (Map.Entry<Palvelupiste, Double> entry : timeData.entrySet()) {
                String label = entry.getKey().toString() + " (" + entry.getValue() + ")";
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
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}
