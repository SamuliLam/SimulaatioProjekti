package controller;

import simu.model.Asiakas;
import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;

import java.util.ArrayList;
import java.util.HashMap;

public interface IKontrolleriForV {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    public void kaynnistaSimulointi();
    public void nopeuta();
    public void hidasta();
    // ikäjakauma interface
    HashMap<Integer, Integer> getAgeDistribution();

    // Palvelupisite interface
    HashMap<String, Integer> getPalvelupisteDistribution();

    // rahankäyttö per asiakas
    HashMap<Asiakas, Double> getSpentMoneyDistribution();

    HashMap<Palvelupiste, Double> getPalvelupisteAikaDistribution();

}
