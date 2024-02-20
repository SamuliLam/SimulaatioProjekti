package view;

import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;

import java.util.HashMap;
import java.util.Map;
public class VisualisointiPalvelupiste extends StackPane implements IVisualisointi {
    private PieChart pieChart;

    public VisualisointiPalvelupiste(int w, int h) {
        super();
        pieChart = new PieChart();
        pieChart.setPrefSize(w, h);
        pieChart.setTitle("Palvelupisteiden Esiintymät");
        this.getChildren().add(pieChart);
    }

    // piechart palvelupisteistä
    public void updateServicePointVisitData(HashMap<String, Integer> visitData) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : visitData.entrySet()) {
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
        // Implement adding a new customer if needed
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}
