package controller;

import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;
import view.GUIKontrolleri;
import view.ISimulaattorinUI;

import java.util.HashMap;

public class Kontrolleri implements IKontrolleriForM, IKontrolleriForV {   // UUSI

    private IMoottori moottori;
    private ISimulaattorinUI ui;

    private Asiakas asiakas;
    private Palvelupiste palvelupiste;

    private GUIKontrolleri guiKontrolleri;

    public Kontrolleri(ISimulaattorinUI ui) {
        this.ui = ui;
        guiKontrolleri = new GUIKontrolleri();
    }
    // Moottorin ohjausta:

    @Override
    public void kaynnistaSimulointi() {
        double palveluaikaMean = ui.getPalveluaikaMean();
        double palveluaikaVarianssi = ui.getPalveluaikaVarianssi();
        moottori = new OmaMoottori(this, palveluaikaMean, palveluaikaVarianssi); // luodaan uusi moottorisäie jokaista simulointia varten
        moottori.setSimulointiaika(ui.getAika());
        moottori.setViive(ui.getViive());
        ui.getVisualisointi().tyhjennaNaytto();
        ((Thread) moottori).start();
        //((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?
    }

    @Override
    public void hidasta() { // hidastetaan moottorisäiettä
        moottori.setViive((long) (moottori.getViive() * 1.10));
    }


    // Hae ja välitä ikäjakauma modelin ja viewin välillä
    @Override
    public HashMap<Integer, Integer> getAgeDistribution() {
        return Asiakas.getAgeDistribution();
    }

    public HashMap<String, Integer> getPalvelupisteDistribution() {
        return Palvelupiste.getPalveluLuku();
    }

    @Override
    public HashMap<String, Double> getPalvelupisteAikaDistribution() {
        return Palvelupiste.getAjatPerPalvelupiste();
    }

    @Override
    public HashMap<TapahtumanTyyppi, HashMap<String, Integer>> getSoldProducts() {
        return Asiakas.getSoldProducts();
    }

    @Override
    public double getPalveluaikaMean() {
        return guiKontrolleri.getPalveluaikaMean();
    }

    @Override
    public double getPalveluaikaVarianssi() {
        return guiKontrolleri.getPalveluaikaVarianssi();
    }

    @Override
    public HashMap<Asiakas, Double> getSpentMoneyDistribution() {
        return Asiakas.getSpentmoneyPerAsiakas();
    }

    @Override
    public void nopeuta() { // nopeutetaan moottorisäiettä
        moottori.setViive((long) (moottori.getViive() * 0.9));
    }


    // Simulointitulosten välittämistä käyttöliittymään.
    // Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:

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
