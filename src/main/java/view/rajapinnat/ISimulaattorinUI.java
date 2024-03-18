package view.rajapinnat;


public interface ISimulaattorinUI {
    double getAika();

    long getViive();

    double getPalveluaikaMean();

    double getPalveluaikaVarianssi();

    double getSaampumisValiaika();

    int getKassaValue();

    void setLoppuaika(double aika);

    IVisualisointi getVisualisointi();

    void setTuloste(String tulokset);
}
