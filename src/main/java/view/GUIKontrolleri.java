package view;

import controller.IKontrolleriForV;
import controller.Kontrolleri;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import simu.framework.Trace;
import simu.model.Asiakas;
import simu.model.TapahtumanTyyppi;

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
    private TextField mean;
    @FXML
    private TextField variance;


    MainApp mainApp;

    @FXML
    public void initialize() {

        kontrolleri = new Kontrolleri(this);
        Trace.setTraceLevel(Trace.Level.INFO);
        startButton.setOnAction(actionEvent -> handleStart());
        nopeutaButton.setOnAction(actionEvent -> handleNopeuta());
        hidastaButton.setOnAction(actionEvent -> handleHidasta());
        avaaStatistiikkaButton.setOnAction(actionEvent -> handleAvaaStatistiikka());
    }

    @FXML
    public void handleStart() {

        if (visualisointi == null) {
            visualisointi = new Visualisointi2(topConsoleCanvas);
            visualisointi.tyhjennaNaytto();
        }
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
        // to do
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
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

}
