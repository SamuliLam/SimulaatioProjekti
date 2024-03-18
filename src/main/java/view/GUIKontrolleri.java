package view;

import controller.IKontrolleriForV;
import controller.Kontrolleri;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import simu.framework.Trace;
import simu.model.Asiakas;
import simu.model.TapahtumanTyyppi;
import javafx.scene.image.ImageView;
import view.charts.*;
import view.rajapinnat.ISimulaattorinUI;
import view.rajapinnat.IVisualisointi;

import java.util.HashMap;

public class GUIKontrolleri implements ISimulaattorinUI {
    private IKontrolleriForV kontrolleri;
    private IVisualisointi visualisointi = null; // Työjuhta
    @FXML
    private Canvas topConsoleCanvas;
    @FXML
    private Button startButton;
    @FXML
    private TextField aikaField;
    @FXML
    private TextField viiveField;
    @FXML
    private Button nopeutaButton;
    @FXML
    private Button hidastaButton;
    @FXML
    private Button avaaStatistiikkaButton;
    @FXML
    private TextArea bottomConsole;
    @FXML
    private TextField saapumisValiaika;
    @FXML
    private TextField mean;
    @FXML
    private TextField variance;
    @FXML
    private AnchorPane canvasConsole;
    @FXML
    private Slider kassaSlider;
    @FXML
    private ImageView simuAikaInfo;
    @FXML
    private ImageView simuViiveInfo;
    @FXML
    private ImageView simuSaapumisInfo;
    @FXML
    private ImageView simuPalveluAikaInfo;
    @FXML
    private ImageView simuPoikkeamaInfo;
    @FXML
    private Tooltip simuAikaInfoTooltip;
    @FXML
    private Tooltip simuViiveInfoTooltip;
    @FXML
    private Tooltip simuSaapumisInfoTooltip;
    @FXML
    private Tooltip simuPalveluAikaInfoTooltip;
    @FXML
    private Tooltip simuPoikkeamaInfoTooltip;
    private int kassaValue;
    Scene mainScene;
    MainApp mainApp;

    @FXML
    public void initialize() {
        kontrolleri = new Kontrolleri(this);
        Trace.setTraceLevel(Trace.Level.INFO);

        if (visualisointi == null) {
            visualisointi = new Visualisointi(topConsoleCanvas);
            visualisointi.tyhjennaNaytto();
        }

        Tooltip.install(simuAikaInfo, simuAikaInfoTooltip);
        Tooltip.install(simuViiveInfo, simuViiveInfoTooltip);
        Tooltip.install(simuSaapumisInfo, simuSaapumisInfoTooltip);
        Tooltip.install(simuPalveluAikaInfo, simuPalveluAikaInfoTooltip);
        Tooltip.install(simuPoikkeamaInfo, simuPoikkeamaInfoTooltip);

        startButton.setOnAction(actionEvent -> handleStart());
        nopeutaButton.setOnAction(actionEvent -> handleNopeuta());
        hidastaButton.setOnAction(actionEvent -> handleHidasta());
        avaaStatistiikkaButton.setOnAction(actionEvent -> handleAvaaStatistiikka());

        topConsoleCanvas.widthProperty().bind(canvasConsole.widthProperty().subtract(canvasConsole.getPadding().getLeft() + canvasConsole.getPadding().getRight()));
        topConsoleCanvas.heightProperty().bind(canvasConsole.heightProperty().subtract(canvasConsole.getPadding().getTop() + canvasConsole.getPadding().getBottom()));
    }

    @FXML
    public void handleStart() {
        kontrolleri.kaynnistaSimulointi();
        System.out.println("Start");
        startButton.setDisable(true);
    }

    @FXML
    public void handleNopeuta() {
        kontrolleri.nopeuta();
        System.out.println("Nopeuta");
    }

    @FXML
    public void handleHidasta() {
        kontrolleri.hidasta();
        System.out.println("Hidasta");
    }

    @FXML
    public void handleAvaaStatistiikka() {
        openStatisticsPage();
    }

    @Override
    public double getAika() {
        return Double.parseDouble(aikaField.getText());
    }

    @Override
    public long getViive() {
        return Long.parseLong(viiveField.getText());
    }

    @Override
    public IVisualisointi getVisualisointi() {
        return visualisointi;
    }

    @Override
    public void updateAgeDistribution(HashMap<Integer, Integer> ageDistribution) {
        visualisointi.updateAgeDistribution(ageDistribution);
    }

    public void setTuloste(String tuloste) {
        bottomConsole.appendText(tuloste);
    }

    @Override
    public double getPalveluaikaMean() {
        return Double.parseDouble(mean.getText());
    }

    @Override
    public double getPalveluaikaVarianssi() {
        return Double.parseDouble(variance.getText());
    }

    @Override
    public double getSaampumisValiaika() {
        return Double.parseDouble(saapumisValiaika.getText());
    }

    public void openStatisticsPage() {
        BorderPane layout = createLayout();
        Scene newScene = new Scene(layout, 1080, 900);
        mainApp.getPrimaryStage().setScene(newScene);
        mainApp.getPrimaryStage().show();
    }

    private BorderPane createLayout() {
        BorderPane layout = new BorderPane();
        TilePane searchElements = new TilePane();
        ComboBox<String> categories = createCategoriesComboBox();
        Button searchButton = createSearchButton(layout, categories);
        Button backButton = createBackButton();

        searchElements.setAlignment(Pos.CENTER);
        searchElements.getChildren().addAll(categories, searchButton);

        layout.setCenter(searchElements);
        layout.setTop(backButton);

        return layout;
    }

    private ComboBox<String> createCategoriesComboBox() {
        ComboBox<String> categories = new ComboBox<>();
        categories.getItems().addAll("Ikäjakauma", "Palvelupiste", "Myynti", "Aika", "Ruokalista");
        return categories;
    }

    private Button createSearchButton(BorderPane layout, ComboBox<String> categories) {
        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> handleSearchButton(layout, categories));
        return searchButton;
    }

    private Button createBackButton() {
        Button backButton = new Button("Go Back");
        backButton.setAlignment(Pos.TOP_LEFT);
        BorderPane.setMargin(backButton, new Insets(10, 10, 10, 10));
        backButton.setOnAction(event -> mainApp.getPrimaryStage().setScene(mainApp.getMainScene()));
        return backButton;
    }

    private void handleSearchButton(BorderPane layout, ComboBox<String> categories) {
        String selectedCategory = categories.getValue();
        if (selectedCategory != null) {
            switch (selectedCategory) {
                case "Ikäjakauma":
                    handleAgeDistribution(layout, new IkajakaumaChart(700, 800));
                    break;
                case "Palvelupiste":
                    handleServicePointDistribution(layout, new PalvelupisteetChart(700, 800));
                    break;
                case "Myynti":
                    handleSpentMoneyDistribution(layout, new RahankayttoChart(700, 800));
                    break;
                case "Aika":
                    handleServicePointTimeDistribution(layout, new PalveluajatChart(700, 800));
                    break;
                case "Ruokalista":
                    handleSoldProductsDistribution(layout, new TuoteChart(700, 800));
                    break;
            }
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public int getKassaValue() {
        kassaValue = (int) kassaSlider.getValue();
        return kassaValue;
    }

    @Override
    public void setLoppuaika(double aika) {
        // TODO
    }

    private void handleAgeDistribution(BorderPane layout, IkajakaumaChart statisticCanvas) {
        HashMap<Integer, Integer> ageDistribution = kontrolleri.getAgeDistribution();
        statisticCanvas.updateAgeDistributionData(ageDistribution);
        layout.setBottom(statisticCanvas);
    }

    private void handleServicePointDistribution(BorderPane layout, PalvelupisteetChart palveluCanvas) {
        HashMap<String, Integer> palvelupisteDistribution = kontrolleri.getPalvelupisteDistribution();
        palveluCanvas.updateServicePointVisitData(palvelupisteDistribution);
        layout.setBottom(palveluCanvas);
    }

    private void handleSpentMoneyDistribution(BorderPane layout, RahankayttoChart rahaCanvas) {
        HashMap<Asiakas, Double> rahankayttoDistribution = kontrolleri.getSpentMoneyDistribution();
        rahaCanvas.updateMoneySpentData(rahankayttoDistribution, kontrolleri.allMoney());
        layout.setBottom(rahaCanvas);
    }

    private void handleServicePointTimeDistribution(BorderPane layout, PalveluajatChart aikaCanvas) {
        HashMap<String, Double> servicePointTimeData = kontrolleri.getPalvelupisteAikaDistribution();
        aikaCanvas.updateServicePointTimeData(servicePointTimeData);
        layout.setBottom(aikaCanvas);
    }

    private void handleSoldProductsDistribution(BorderPane layout, TuoteChart soldProductsCanvas) {
        HashMap<TapahtumanTyyppi, HashMap<String, Integer>> soldProducts = kontrolleri.getSoldProducts();
        soldProductsCanvas.updateSoldProductsData(soldProducts);
        layout.setBottom(soldProductsCanvas);
    }
}
