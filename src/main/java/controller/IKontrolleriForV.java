package controller;

import simu.model.Asiakas;
import simu.model.TapahtumanTyyppi;
import java.util.HashMap;

public interface IKontrolleriForV {

    void kaynnistaSimulointi();

    void nopeuta();

    void hidasta();

    HashMap<Integer, Integer> getAgeDistribution();

    HashMap<String, Integer> getPalvelupisteDistribution();

    HashMap<Asiakas, Double> getSpentMoneyDistribution();

    HashMap<String, Double> getPalvelupisteAikaDistribution();

    HashMap<TapahtumanTyyppi, HashMap<String, Integer>> getSoldProducts();

    double allMoney();
}
