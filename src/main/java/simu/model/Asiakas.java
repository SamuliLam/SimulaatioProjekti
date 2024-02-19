package simu.model;

import eduni.distributions.Normal;
import simu.framework.*;
import simu.model.Tuotehallinta.GroceryCategory;

import java.text.DecimalFormat;
import java.util.*;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
    private static Normal ageRandom = new Normal(40, 100);
    private static List<Asiakas> asiakkaat = new ArrayList<>();
    private static HashMap<Integer, Integer> ikaJakauma = new HashMap<>();

    private static HashMap<Asiakas, Double> spentmoneyPerAsiakas = new HashMap<>();
    private ArrayList<GroceryCategory> groceryList;
    private GroceryCategory groceryCategory;

    private TapahtumanTyyppi palvelupisteListaEnumType;
    private TapahtumanTyyppi[] palvelupisteListaEnumValues = TapahtumanTyyppi.values();

    private HashSet<TapahtumanTyyppi> palvelupisteLista;
    private static long sum = 0;

    private static double totalMoneySpent = 0;

    private double saapumisaika;
    private double poistumisaika;
    private int id;
    private static int i = 1;

    private final int ika;

    private double spentMoney;


    Random random = new Random();

    public Asiakas() {
        id = i++;
        // palvelupisteLista määrätään asiakkaalle
        palvelupisteLista = new HashSet<>();
        groceryList = new ArrayList<>();
        // Luodaan random palvelupisteLista tyypettäin asiakkaalle
        generateRandomRuokalista();
        ika = (int) (ageRandom.sample());
        spentMoney = 0;
        saapumisaika = Kello.getInstance().getAika();
        Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaika + " ja on " + ika + " vuotias.");
        Trace.out(Trace.Level.INFO, "Asiakkaan " + getId() + " ruokalista: \n" + printRuokalista());
        updateIkaJakauma(ika);
        asiakkaat.add(this);
    }

    // GETTERIT JA SETTERIT

    public static List<Asiakas> getAsiakkaat() {
        return asiakkaat;
    }

    public double getPoistumisaika() {
        return poistumisaika;
    }

    public double getSaapumisaika() {
        return saapumisaika;
    }

    public static double getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public static double getAverageMoneySpent() {
        return totalMoneySpent / asiakkaat.size();
    }

    public int getId() {
        return id;
    }

    public double getSpentMoney() {
        return spentMoney;
    }

    public static HashMap<Asiakas, Double> getSpentmoneyPerAsiakas() {
        return spentmoneyPerAsiakas;
    }
    public static int getAverageAge() {
        int summa = 0;
        for (Map.Entry<Integer, Integer> entry : ikaJakauma.entrySet()) {
            summa += (entry.getKey() * entry.getValue());
        }
        return summa / asiakkaat.size();
    }

    public HashSet<TapahtumanTyyppi> getpalvelupisteLista() {
        return palvelupisteLista;
    }

    public ArrayList<GroceryCategory> getGroceryList() {
        return groceryList;
    }


    public void setPoistumisaika(double poistumisaika) {
        this.poistumisaika = poistumisaika;
    }


    public void setSaapumisaika(double saapumisaika) {
        this.saapumisaika = saapumisaika;
    }

    public static HashMap<Integer, Integer> getAgeDistribution() {
        return ikaJakauma;
    }

    // MUUT METODIT


    public static void addTotalMoneySpent(double amount) {
        totalMoneySpent += amount;
    }

    public void addSpentMoney(double amount) {
        spentMoney += amount;
        spentmoneyPerAsiakas.put(this, spentMoney);
    }


    public void updateIkaJakauma(int age) {
        if (ikaJakauma.containsKey(age)) {
            ikaJakauma.put(age, ikaJakauma.get(age) + 1);
        } else {
            ikaJakauma.put(age, 1);
        }
    }

    public String printRuokalista() {
        StringBuilder sb = new StringBuilder();
        for (GroceryCategory productCategory : groceryList) {
            sb.append(productCategory.getItems());

        }
        return sb.toString();
    }

    public void generateRandomRuokalista() {
        // lisätään ARRMARKET pakolliseks ja ensimmäiseksi.
        palvelupisteLista.add(TapahtumanTyyppi.ARRMARKET);
        palvelupisteLista.add(TapahtumanTyyppi.CHECKOUTDEP);
        // asetetaan random määrä ruokatyyppejä asiakkaalle
        int randompalvelupisteListaSize = random.nextInt(3, TapahtumanTyyppi.values().length);

        System.out.println("palvelupisteLista koko: " + randompalvelupisteListaSize);

        // Random indexia käyttäen etsitään random enum asiakkaalle
        while (palvelupisteLista.size() < randompalvelupisteListaSize) {
            // Random index luku jolla arvotaan enumi asiakkaalle
            int randomIndex = random.nextInt(palvelupisteListaEnumValues.length);
            TapahtumanTyyppi randomEnum = palvelupisteListaEnumValues[randomIndex];
            // lisätään random enumtyyppi palvelupisteListaan.
            if (!palvelupisteLista.contains(randomEnum)) { // Tarkista onko olemassa
                palvelupisteLista.add(randomEnum);
                if (randomEnum != TapahtumanTyyppi.ARRMARKET && randomEnum != TapahtumanTyyppi.CHECKOUTDEP) {
                    groceryCategory = new GroceryCategory(randomEnum);
                    groceryList.add(groceryCategory);

                }
            }

        }
    }

    public void raportti() {
        Trace.out(Trace.Level.INFO, "\nAsiakas " + id + " valmis! ");
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + saapumisaika);
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + poistumisaika);
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (poistumisaika - saapumisaika));
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " kulutti: " + spentMoney);
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " ruokalista: " + printRuokalista());
    }

    public static void completeRaportti() {
        double totalMoneySpent = getTotalMoneySpent();
        double averageMoneySpent = getAverageMoneySpent();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String formattedTotalMoneySpent = decimalFormat.format(totalMoneySpent);
        String formattedAverageMoneySpent = decimalFormat.format(averageMoneySpent);

        Trace.out(Trace.Level.INFO, "Asiakkaita yhteensä: " + asiakkaat.size());
        Trace.out(Trace.Level.INFO, "Asiakkaiden keskimääräinen rahankulutus " + formattedAverageMoneySpent + " euroa.");
        Trace.out(Trace.Level.INFO, "Asiakkaiden keskimääräinen ikä: " + getAverageAge());
        Trace.out(Trace.Level.INFO, "Asiakkaiden kuluttama rahamäärä yhteensä: " + formattedTotalMoneySpent + " euroa.");
    }

}