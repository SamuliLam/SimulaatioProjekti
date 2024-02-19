package controller;

import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;

import java.util.HashMap;

public interface IKontrolleriForV {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    public void kaynnistaSimulointi();
    public void nopeuta();
    public void hidasta();

    // ikäjakauma interface
    HashMap<Integer, Integer> getAgeDistribution();

    HashMap<String, Integer> getPalvelupisteDistribution();
}
