package view;

import java.text.DecimalFormat;
import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;

import java.util.HashMap;

public class SimulaattorinGUI extends Application implements ISimulaattorinUI {

    private IKontrolleriForV kontrolleri;
    private TextField aika;
    private TextField viive;
    private Label tulos;
    private Label aikaLabel;
    private Label viiveLabel;
    private Label tulosLabel;

    private Button kaynnistaButton;
    private Button hidastaButton;
    private Button nopeutaButton;
    private Button avaaStatistics;
    Scene mainScene;
    private IVisualisointi naytto;
    private TextArea console;

    @Override
    public void init() {
        Trace.setTraceLevel(Level.INFO);
        kontrolleri = new Kontrolleri(this);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });

            primaryStage.setTitle("Simulaattori");

            kaynnistaButton = new Button();
            kaynnistaButton.setText("Käynnistä simulointi");
            kaynnistaButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    kontrolleri.kaynnistaSimulointi();
                    kaynnistaButton.setDisable(true);
                }
            });

            hidastaButton = new Button();
            hidastaButton.setText("Hidasta");
            hidastaButton.setOnAction(e -> kontrolleri.hidasta());

            nopeutaButton = new Button();
            nopeutaButton.setText("Nopeuta");
            nopeutaButton.setOnAction(e -> kontrolleri.nopeuta());

            avaaStatistics = new Button();
            avaaStatistics.setText("Avaa statistiikka sivun");
            avaaStatistics.setOnAction(e -> openStatisticsPage(primaryStage));

            aikaLabel = new Label("Simulointiaika:");
            aikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            aika = new TextField();
            aika.setPromptText("Syötä aika");
            aika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            aika.setPrefWidth(150);

            viiveLabel = new Label("Viive:");
            viiveLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            viive = new TextField();
            viive.setPromptText("Syötä viive");
            viive.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            viive.setPrefWidth(150);

            tulosLabel = new Label("Kokonaisaika:");
            tulosLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            tulos = new Label();
            tulos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            tulos.setPrefWidth(150);

            GridPane main = new GridPane();
            VBox vBoxButtons = new VBox();
            vBoxButtons.setSpacing(10);
            vBoxButtons.setAlignment(Pos.CENTER);

            VBox consoles = new VBox();
            consoles.setSpacing(10);
            consoles.setAlignment(Pos.CENTER);
            vBoxButtons.getChildren().addAll(aika, viive, kaynnistaButton, hidastaButton, nopeutaButton, avaaStatistics);

            naytto = new Visualisointi2(600, 200);
            console = new TextArea();
            console.setEditable(false);
            Label consoleLabel = new Label("Simulaattorin konsoli:");
            Label tulosteLabel = new Label("Tuloste:");
            console.setPrefWidth(600); console.setPrefHeight(200);
            consoles.getChildren().addAll(consoleLabel, (Canvas) naytto, tulosteLabel, console);

            main.add(vBoxButtons, 0, 0);
            main.add(consoles, 1, 0);
            main.setHgap(10);

            GridPane.setMargin(vBoxButtons, new Insets(10, 10, 10, 10));
            GridPane.setMargin(consoles, new Insets(30, 10, 30, 10));

            mainScene = new Scene(main);
            primaryStage.setScene(mainScene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openStatisticsPage(Stage primaryStage) {
        BorderPane layout = new BorderPane();

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
        VisualisointiStatistiikka statisticCanvas = new VisualisointiStatistiikka(700, 600);
        VisualisointiPalvelupiste palveluCanvas = new VisualisointiPalvelupiste(700, 600);

        backButton.setOnAction(event -> {
            primaryStage.setScene(mainScene);
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
        });
        Scene newScene = new Scene(layout, 900, 900);

        primaryStage.setScene(newScene);
    }

    @Override
    public double getAika() {
        return Double.parseDouble(aika.getText());
    }

    @Override
    public long getViive() {
        return Long.parseLong(viive.getText());
    }

    @Override
    public void setLoppuaika(double aika) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.tulos.setText(formatter.format(aika));
    }

    @Override
    public void updateAgeDistribution(HashMap<Integer, Integer> ageDistribution) {

    }
    @Override
    public IVisualisointi getVisualisointi() {
        return naytto;
    }

    public void setTuloste(String tuloste) {
        console.appendText(tuloste);
    }
}