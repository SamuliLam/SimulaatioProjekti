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
    private final double totalMoneyUsed;

    public VisualisointiRahankaytto(int w, int h) {
        super();
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        areaChart = new AreaChart<>(xAxis, yAxis);
        areaChart.setPrefSize(w, h);
        areaChart.getXAxis().setLabel("Asiakas ID");
        areaChart.getYAxis().setLabel("Raha");
        this.getChildren().add(areaChart);
        this.totalMoneyUsed = Asiakas.getTotalMoneySpent();

    }

    // rahankäyttö chartti

    public void updateMoneySpentData(HashMap<Asiakas, Double> spentMoneyPerAsiakas) {
        areaChart = new AreaChart<>(xAxis, yAxis);
        String totalSpentMoney =  "Total Money Spent: " + String.format("%.2f", totalMoneyUsed);
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
