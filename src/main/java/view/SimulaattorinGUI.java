package view;

import java.text.DecimalFormat;
import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
            uusiSivu.setOnAction(e -> openNewPage(primaryStage));

            aikaLabel = new Label("Simulointiaika:");
            aikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            aika = new TextField("Syötä aika");
            aika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            aika.setPrefWidth(150);

            viiveLabel = new Label("Viive:");
            viiveLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            viive = new TextField("Syötä viive");
            viive.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            viive.setPrefWidth(150);

            tulosLabel = new Label("Kokonaisaika:");
            tulosLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            tulos = new Label();
            tulos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            tulos.setPrefWidth(150);

            // New labels for customer information
            asiakasIdLabel = new Label("Asiakas ID:");
            asiakasIdLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            ruokalistaLabel = new Label("Ruokalista:");
            ruokalistaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            asiakasVisitsLabel = new Label("Asiakkaan vierailut:");
            asiakasVisitsLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            asiakasSpendingLabel = new Label("Asiakkaan kulutus:");
            asiakasSpendingLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            palvelupisteSpendingLabel = new Label("Palvelupisteen kulutus:");
            palvelupisteSpendingLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

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

            // Add new labels to the grid
            grid.add(asiakasIdLabel, 0, 5);
            grid.add(ruokalistaLabel, 0, 6);
            grid.add(asiakasVisitsLabel, 0, 7);
            grid.add(asiakasSpendingLabel, 0, 8);
            grid.add(palvelupisteSpendingLabel, 0, 9);

            naytto = new Visualisointi(400, 200);

            vBox.getChildren().addAll(grid, (Canvas) naytto);

            mainScene = new Scene(vBox);
            primaryStage.setScene(mainScene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openNewPage(Stage primaryStage) {

        // Create the layout for the new scene
        StackPane newLayout = new StackPane();
        Button backButton = new Button("Go Back");
        PieChart.Data slice1 = new PieChart.Data("Lihatiski", 25);
        PieChart.Data slice2 = new PieChart.Data("Kalatiski", 20);
        PieChart.Data slice3 = new PieChart.Data("Karkkitiski", 30);
        PieChart.Data slice4 = new PieChart.Data("Kaljatiski", 25);

        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(slice1, slice2, slice3,slice4);


        newLayout.getChildren().add(pieChart);
        backButton.setOnAction(event -> {
            // When the "Go Back" button is clicked, switch back to the initial scene
            primaryStage.setScene(mainScene); // Assuming 'scene' is the initial scene
        });
        newLayout.getChildren().add(backButton);

        // Create the new scene with the layout
        Scene newScene = new Scene(newLayout, 1080, 860);

        // Set the new scene to the stage
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





