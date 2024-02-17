package view;

import java.text.DecimalFormat;
import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;


import simu.model.Asiakas;
import simu.model.Palvelupiste;

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
    private Button uusiSivu;

    Label asiakasIdLabel;
    Label ruokalistaLabel;
    Label asiakasVisitsLabel;
    Label asiakasSpendingLabel;
    Label palvelupisteSpendingLabel;
    Scene mainScene;
    private IVisualisointi naytto;

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

            uusiSivu = new Button();
            uusiSivu.setText("Avaa uusi paska");
            uusiSivu.setOnAction(e -> openStatisticsPage(primaryStage));

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

            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(10);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setVgap(10);
            grid.setHgap(5);

            grid.add(aikaLabel, 0, 0);
            grid.add(aika, 1, 0);
            grid.add(viiveLabel, 0, 1);
            grid.add(viive, 1, 1);
            grid.add(tulosLabel, 0, 2);
            grid.add(tulos, 1, 2);
            grid.add(kaynnistaButton, 0, 3);
            grid.add(nopeutaButton, 0, 4);
            grid.add(hidastaButton, 1, 4);
            grid.add(uusiSivu, 2,5);

            naytto = new Visualisointi(400, 200);

            vBox.getChildren().addAll(grid, (Canvas) naytto);

            mainScene = new Scene(vBox);
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
        categories.getItems().addAll("placeholder", "placeholder", "placeholder", "placeholder", "placeholder");
        Button searchButton = new Button("Search");

        searchElements.setAlignment(Pos.CENTER);
        Button backButton = new Button("Go Back");
        backButton.setAlignment(Pos.TOP_LEFT);
        BorderPane.setMargin(backButton, new Insets(10, 10, 10, 10));
        searchElements.getChildren().addAll(categories, searchButton);

        layout.setCenter(searchElements);
        layout.setTop(backButton);
        Visualisointi statisticCanvas = new Visualisointi(400, 200);
        layout.setBottom(statisticCanvas);
        backButton.setOnAction(event -> {
            primaryStage.setScene(mainScene);
        });

        Scene newScene = new Scene(layout, 400, 400);

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

    // Update the labels with customer information
    public void updateCustomerInfo(int customerId, String menu, int visits, double spending, double servicePointSpending) {
        // Set the text of the labels with the provided data
        // For example:
        asiakasIdLabel.setText("Asiakas ID: " + "1");
        ruokalistaLabel.setText("Ruokalista: " + "Homo");
        asiakasVisitsLabel.setText("Asiakkaan vierailut: " + visits);
        asiakasSpendingLabel.setText("Asiakkaan kulutus: " + Asiakas.getTotalMoneySpent());
        palvelupisteSpendingLabel.setText("Palvelupisteen kulutus: " + Asiakas.getAverageAge());
    }

    @Override
    public IVisualisointi getVisualisointi() {
        return naytto;
    }
}