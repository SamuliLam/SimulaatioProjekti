package view;

import controller.IKontrolleriForV;
import controller.Kontrolleri;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import simu.framework.Trace;

import java.util.HashMap;


public class GUIKontrolleri implements ISimulaattorinUI {

    private IKontrolleriForV kontrolleri;

    private IVisualisointi naytto;

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
    private Button ajaUudelleenButton;
    @FXML
    private TextArea bottomConsole;
    @FXML
    private Canvas topConsoleCanvas;
    MainApp mainApp;

    @FXML
    public void initialize() {
        kontrolleri = new Kontrolleri(this);
        Trace.setTraceLevel(Trace.Level.INFO);
        naytto = new Visualisointi2(100, 100);
        topConsoleCanvas = new Visualisointi2(100, 100);
        startButton.setOnAction(actionEvent -> handleStart());
        ajaUudelleenButton.setOnAction(actionEvent -> handleAjaUudelleen());
        nopeutaButton.setOnAction(actionEvent -> handleNopeuta());
        hidastaButton.setOnAction(actionEvent -> handleHidasta());
        avaaStatistiikkaButton.setOnAction(actionEvent -> handleAvaaStatistiikka());
    }

    @FXML
    public void handleStart() {
        kontrolleri.kaynnistaSimulointi();
        System.out.println("Start");
        startButton.setDisable(true);
    }

    @FXML
    public void handleAjaUudelleen() {
        System.out.println("Aja uudelleen");
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
        System.out.println("Avaa statistiikka");
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
        return naytto;
    }

    @Override
    public void updateAgeDistribution(HashMap<Integer, Integer> ageDistribution) {

    }

    public void setTuloste(String tuloste) {
        bottomConsole.appendText(tuloste);
    }
}
