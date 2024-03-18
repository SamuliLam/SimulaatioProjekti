package view.charts;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import java.util.HashMap;
import java.util.Map;

public class PalvelupisteetChart extends StackPane {
    private PieChart pieChart;
    public PalvelupisteetChart(int w, int h) {
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
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }

}
