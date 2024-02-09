package simu.model;

import eduni.distributions.Normal;
import simu.framework.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
    private static Normal ageRandom = new Normal(40, 100);
    private static List<Asiakas> asiakkaat = new ArrayList<>();
    private static HashMap<Integer, Integer> ikaJakauma = new HashMap<>();
    private static long sum = 0;
    private static int i = 1;

    private static double totalMoneySpent = 0;

    private double saapumisaika;
    private double poistumisaika;
    private int id;
    private static int asiakkadenMaara = 0;
    private static long sum = 0;
    private int ika;

    private double spentMoney;
    private ArrayList<TapahtumanTyyppi> ruokalista;

    public Asiakas() {
        id = ++asiakkadenMaara;
        ruokalista = new ArrayList<>();
        ika = (int) (ageRandom.sample());
        spentMoney = 0;
        saapumisaika = Kello.getInstance().getAika();
        Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaika + " ja on " + ika + " vuotias.");
        updateIkaJakauma(ika);
        asiakkaat.add(this);
    }

    public static List<Asiakas> getAsiakkaat() {
        return asiakkaat;
    }

    public double getPoistumisaika() {
        return poistumisaika;
    }

    public void setPoistumisaika(double poistumisaika) {
        this.poistumisaika = poistumisaika;
    }

    public double getSaapumisaika() {
        return saapumisaika;
    }

    public void setSaapumisaika(double saapumisaika) {
        this.saapumisaika = saapumisaika;
    }

    public static void addTotalMoneySpent(double amount) {
        totalMoneySpent += amount;
    }

    public void addSpentMoney(double amount) {
        spentMoney += amount;
    }

    public static double getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public static double getAverageMoneySpent() {
        return totalMoneySpent / asiakkaat.size();
    }

    public void updateIkaJakauma(int ikä) {
        if (ikaJakauma.containsKey(ikä)) {
            ikaJakauma.put(ikä, ikaJakauma.get(ikä) + 1);
        } else {
            ikaJakauma.put(ikä, 1);
        }

    }

    public double getSpentMoney() {
        return spentMoney;
    }

    public static int getAverageAge() {
        int summa = 0;
        for (Map.Entry<Integer, Integer> entry : ikaJakauma.entrySet()) {
            summa += (entry.getKey() * entry.getValue());
        }
        return summa / asiakkadenMaara;
    }

    public int getId() {
        return id;
    }

    public void raportti() {
        Trace.out(Trace.Level.INFO, "\nAsiakas " + id + " valmis! ");
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + saapumisaika);
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + poistumisaika);
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (poistumisaika - saapumisaika));
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " kulutti: " + spentMoney);
    }

    public static void completeRaportti() {
        Trace.out(Trace.Level.INFO, "Asiakkaita yhteensä: " + asiakkaat.size());
        Trace.out(Trace.Level.INFO, "Asiakkaiden keskimääräinen rahankulutus " + getAverageMoneySpent() + " euroa.");
    }
}
