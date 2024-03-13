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
import java.util.HashMap;


public class GUIKontrolleri implements ISimulaattorinUI {

    private IKontrolleriForV kontrolleri;

    @FXML
    private Canvas topConsoleCanvas;
    private IVisualisointi visualisointi = null; // Työjuhta

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
            visualisointi = new Visualisointi2(topConsoleCanvas);
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

        // Bind the canvas size to the size of the parent AnchorPane
        // This helps to keep the canvas size in sync when the stage is resized
        // Subtract the padding amount of parent AnchorPane to get the canvas size and nice borders for the canvas
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
    public void setLoppuaika(double aika) {
        // TODO
    }

    @Override
    public IVisualisointi getVisualisointi() {
        return visualisointi;
    }

    @Override
    public void updateAgeDistribution(HashMap<Integer, Integer> ageDistribution) {

    }

    public void setTuloste(String tuloste) {
        bottomConsole.appendText(tuloste);
    }

    public Canvas getTopConsoleCanvas() {
        return topConsoleCanvas;
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
    public double getSaampumisValiaika(){
        return Double.parseDouble(saapumisValiaika.getText());
    }

    public void openStatisticsPage(){
        BorderPane layout = new BorderPane();

        // ... rest of the code from openStatisticsPage ...
        TilePane searchElements = new TilePane();
        ComboBox<String> categories = new ComboBox<>();
        categories.getItems().addAll("Ikäjakauma", "Palvelupiste", "Myynti", "Aika", "Ruokalista");
        Button searchButton = new Button("Search");

        searchElements.setAlignment(Pos.CENTER);
        Button backButton = new Button("Go Back");
        backButton.setAlignment(Pos.TOP_LEFT);
        BorderPane.setMargin(backButton, new Insets(10, 10, 10, 10));
        searchElements.getChildren().addAll(categories, searchButton);

        layout.setCenter(searchElements);
        layout.setTop(backButton);
        VisualisointiIkajakauma statisticCanvas = new VisualisointiIkajakauma(700, 800);
        VisualisointiPalvelupiste palveluCanvas = new VisualisointiPalvelupiste(700, 800);
        VisualisointiRahankaytto rahaCanvas = new VisualisointiRahankaytto(700, 800);
        VisualisointiPalveluajat aikaCanvas = new VisualisointiPalveluajat(700, 800);
        VisualisointiTuotteet soldProductsCanvas = new VisualisointiTuotteet(700, 800);

        backButton.setOnAction(event -> {
            mainApp.getPrimaryStage().setScene(mainApp.getMainScene());
        });

        searchButton.setOnAction(event -> {
            String selectedCategory = categories.getValue();
            if (selectedCategory != null && selectedCategory.equals("Ikäjakauma")) {
                // Hae ikäjakauman kontrollerista
                HashMap<Integer, Integer> ageDistribution = kontrolleri.getAgeDistribution();
                // Lisää tiedot kanvasiin
                statisticCanvas.updateAgeDistributionData(ageDistribution);
                layout.setBottom(statisticCanvas);
            }
            else if (selectedCategory != null && selectedCategory.equals("Palvelupiste")) {
                // Hae palvelupiste jakauma
                HashMap<String , Integer> palvelupisteDistribution = kontrolleri.getPalvelupisteDistribution();
                // Lisää tiedot kanvasiin
                palveluCanvas.updateServicePointVisitData(palvelupisteDistribution);
                layout.setBottom(palveluCanvas);
            }
            else if (selectedCategory != null && selectedCategory.equals("Myynti")) {
                // Hae rahankäyttö jakauma
                HashMap<Asiakas, Double> rahankayttoDistribution = kontrolleri.getSpentMoneyDistribution();
                // Lisää tiedot kanvasiin
                rahaCanvas.updateMoneySpentData(rahankayttoDistribution);
                layout.setBottom(rahaCanvas);
            }
            else if (selectedCategory != null && selectedCategory.equals("Aika")) {
                // Hae aikakäyttö jakauma
                HashMap<String, Double> servicePointTimeData = kontrolleri.getPalvelupisteAikaDistribution();
                // Lisää tiedot kanvasiin
                aikaCanvas.updateServicePointTimeData(servicePointTimeData);
                layout.setBottom(aikaCanvas);
            } else if (selectedCategory != null && selectedCategory.equals("Ruokalista")) {
                // Hae ruokalista
                HashMap<TapahtumanTyyppi, HashMap<String, Integer>> soldProducts = kontrolleri.getSoldProducts();
                // Lisää tiedot kanvasiin
                soldProductsCanvas.updateSoldProductsData(soldProducts);
                layout.setBottom(soldProductsCanvas);
            }
        });

        Scene newScene = new Scene(layout, 1080, 900);
        mainApp.getPrimaryStage().setScene(newScene);
        mainApp.getPrimaryStage().show();

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void enableStartButton() {
        startButton.setDisable(false);
    }


    public int getKassaValue() {
        kassaValue = (int) kassaSlider.getValue();
        return kassaValue; }
}
