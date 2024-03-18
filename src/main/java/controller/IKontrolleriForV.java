package controller;

import simu.model.Asiakas;
import simu.model.TapahtumanTyyppi;
import java.util.HashMap;

public interface IKontrolleriForV {

    void kaynnistaSimulointi();

    void nopeuta();

    void hidasta();
    // ikäjakauma interface
    HashMap<Integer, Integer> getAgeDistribution();
    // Palvelupisite interface
    HashMap<String, Integer> getPalvelupisteDistribution();

    // rahankäyttö per asiakas
    HashMap<Asiakas, Double> getSpentMoneyDistribution();

    // Aika per palvelupiste
    HashMap<String, Double> getPalvelupisteAikaDistribution();

    HashMap<TapahtumanTyyppi, HashMap<String, Integer>> getSoldProducts();

    double allMoney();
}
