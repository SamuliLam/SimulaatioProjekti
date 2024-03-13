package controller;

public interface IKontrolleriForM {

    // Rajapinta, joka tarjotaan moottorille:

    void naytaLoppuaika(double aika);
    void visualisoiAsiakas();
    void asiakasPoistuu();
    void naytaTulokset(String tulos);
    void updateMeatDepActivity(boolean isReserved);

    void updateBeerDepActivity(boolean isReserved);

    void updateFishDepActivity(boolean isReserved);

    void updateCandyDepActivity(boolean isReserved);

    double getSaapumisValiaika();

    double getPalveluaikaMean();

    double getPalveluaikaVarianssi();

    int setKassaMaara();
}
