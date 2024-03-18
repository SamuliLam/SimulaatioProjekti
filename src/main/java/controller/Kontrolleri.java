package controller;

import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;
import view.rajapinnat.ISimulaattorinUI;
import java.util.HashMap;

public class Kontrolleri implements IKontrolleriForM, IKontrolleriForV {
    private IMoottori moottori;
    private ISimulaattorinUI ui;
    public Kontrolleri(ISimulaattorinUI ui) {
        this.ui = ui;
    }

    @Override
    public void kaynnistaSimulointi() {
        moottori = new OmaMoottori(this);
        moottori.setSimulointiaika(ui.getAika());
        moottori.setViive(ui.getViive());
        ui.getVisualisointi().tyhjennaNaytto();
        ((Thread) moottori).start();
    }

    @Override
    public void hidasta() { // hidastetaan moottorisäiettä
        moottori.setViive((long) (moottori.getViive() * 1.10));
    }

    @Override
    public HashMap<Integer, Integer> getAgeDistribution() {
        return Asiakas.getAgeDistribution();
    }

    public HashMap<String, Integer> getPalvelupisteDistribution() {
        return Palvelupiste.getServicePointVisits();
    }

    @Override
    public HashMap<String, Double> getPalvelupisteAikaDistribution() {
        return Palvelupiste.getServiceTimesPerServicePoint();
    }

    @Override
    public HashMap<TapahtumanTyyppi, HashMap<String, Integer>> getSoldProducts() {
        return Asiakas.getSoldProducts();
    }

    @Override
    public double getSaapumisValiaika() {
        return ui.getSaampumisValiaika();
    }

    @Override
    public double allMoney() {
        return Asiakas.getTotalSpentMoneyAtCheckout();
    }

    @Override
    public double getPalveluaikaMean() {
        return ui.getPalveluaikaMean();
    }

    @Override
    public double getPalveluaikaVarianssi() {
        return ui.getPalveluaikaVarianssi();
    }

    @Override
    public HashMap<Asiakas, Double> getSpentMoneyDistribution() {
        return Asiakas.getSpentmoneyPerAsiakas();
    }

    @Override
    public void nopeuta() { // nopeutetaan moottorisäiettä
        moottori.setViive((long) (moottori.getViive() * 0.9));
    }

    @Override
    public void naytaLoppuaika(double aika) {
        Platform.runLater(() -> ui.setLoppuaika(aika));
    }

    @Override
    public void visualisoiAsiakas() {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualisointi().uusiAsiakas();
            }
        });
    }

    @Override
    public void asiakasPoistuu() {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualisointi().asiakasPoistuu();
            }
        });
    }

    @Override
    public void updateMeatDepActivity(boolean isReserved) {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualisointi().updateMeatDepActivity(isReserved);
            }
        });
    }

    @Override
    public void updateBeerDepActivity(boolean isReserved) {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualisointi().updateBeerDepActivity(isReserved);
            }
        });
    }

    @Override
    public void updateFishDepActivity(boolean isReserved) {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualisointi().updateFishDepActivity(isReserved);
            }
        });
    }

    @Override
    public void updateCandyDepActivity(boolean isReserved) {
        Platform.runLater(new Runnable() {
            public void run() {
                ui.getVisualisointi().updateCandyDepActivity(isReserved);
            }
        });
    }

    @Override
    public void naytaTulokset(String tulokset) {
        Platform.runLater(() -> ui.setTuloste(tulokset));
    }

    @Override
    public int setKassaMaara() {
        int kassat = ui.getKassaValue();
        return kassat;
    }
}
