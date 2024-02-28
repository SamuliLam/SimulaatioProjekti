package view;

import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;
import simu.model.Asiakas;
import javafx.scene.chart.AreaChart;
import java.util.*;

public class VisualisointiRahankaytto extends StackPane implements IVisualisointi {
    private AreaChart<String, Number> areaChart;;
    private final CategoryAxis xAxis;
    private final NumberAxis yAxis;

    public VisualisointiRahankaytto(int w, int h) {
        super();
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        areaChart = new AreaChart<>(xAxis, yAxis);
        areaChart.setPrefSize(w, h);
        areaChart.getXAxis().setLabel("Asiakas ID");
        areaChart.getYAxis().setLabel("Raha");
        this.getChildren().add(areaChart);
    }

    // rahankäyttö chartti

    public void updateMoneySpentData(HashMap<Asiakas, Double> spentMoneyPerAsiakas, double allMoneyAtCheckout) {
        String totalSpentMoney =  "Total Money Spent: " + String.format("%.2f €", allMoneyAtCheckout);
        areaChart = new AreaChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(totalSpentMoney + ".   " + "Money spent per asiakas shown above.");

        // järjestykseen
        Comparator<Asiakas> asiakasComparator = Comparator.comparingInt(Asiakas::getId);

        // Käytä treemapia järjestämään asiakkaita
        TreeMap<Asiakas, Double> sortedMoneyDistribution = new TreeMap<>(asiakasComparator);
        sortedMoneyDistribution.putAll(spentMoneyPerAsiakas);

        for (Map.Entry<Asiakas, Double> entry : sortedMoneyDistribution.entrySet()) {
            Asiakas asiakas = entry.getKey();
            double spentMoney = entry.getValue();
            series.getData().add(new XYChart.Data<>(String.valueOf(asiakas.getId()), spentMoney));
        }

        areaChart.getData().clear(); // siivoa
        areaChart.getData().add(series);

        this.getChildren().clear(); // Siivoa
        this.getChildren().add(areaChart);
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

    }

    @Override
    public void updateMeatDepActivity(boolean isReserved) {

    }

    @Override
    public void updateBeerDepActivity(boolean isReserved) {

    }

    @Override
    public void updateFishDepActivity(boolean isReserved) {

    }

    @Override
    public void updateCandyDepActivity(boolean isReserved) {

    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}
