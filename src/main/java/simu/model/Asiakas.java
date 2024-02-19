package simu.model;

import eduni.distributions.Normal;
import simu.framework.*;

import java.util.*;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
    private static Normal ageRandom = new Normal(40, 100);
    private static List<Asiakas> asiakkaat = new ArrayList<>();
    private static HashMap<Integer, Integer> ikaJakauma = new HashMap<>();

    private HashSet<TapahtumanTyyppi> palvelupisteLista;

    private ArrayList<GroceryCategory> groceryList;
    private GroceryCategory groceryCategory;

    private TapahtumanTyyppi palvelupisteListaEnumType;
    private TapahtumanTyyppi[] palvelupisteListaEnumValues = TapahtumanTyyppi.values();
    private static long sum = 0;

    private static double totalMoneySpent = 0;

    private double saapumisaika;
    private double poistumisaika;
    private int id;
    private static int i = 1;

    private int ika;

    private double spentMoney;


    Random random = new Random();

    public Asiakas() {
        id = i++;
        // palvelupisteLista määrätään asiakkaalle
        palvelupisteLista = new HashSet<>();
        groceryList = new ArrayList<>();
        // Luodaan random palvelupisteLista tyypettäin asiakkaalle
        generateRandomEnums();
        ika = (int) (ageRandom.sample());
        spentMoney = 0;
        saapumisaika = Kello.getInstance().getAika();
        Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaika + " ja on " + ika + " vuotias.");
//        Trace.out(Trace.Level.INFO, "Asiakkaan " + getId() + " palvelupisteLista: " + printpalvelupisteLista());
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
        return summa / asiakkaat.size();
    }


    public ArrayList<GroceryCategory> getGroceryList() {
        return groceryList;
    }

    public String groceryListToString() {
        StringBuilder sb = new StringBuilder();
        for (GroceryCategory groceryCategory : groceryList) {
            sb.append(groceryCategory.getItems().toString());
            if (groceryCategory != groceryList.get(groceryList.size() - 1)) {
                sb.append(", ");
            }
        }
        return sb.toString();
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
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " ruokalista: " + groceryListToString());
    }

    public static void completeRaportti() {
        Trace.out(Trace.Level.INFO, "Asiakkaita yhteensä: " + asiakkaat.size());
        Trace.out(Trace.Level.INFO, "Asiakkaiden keskimääräinen rahankulutus " + getAverageMoneySpent() + " euroa.");
        Trace.out(Trace.Level.INFO, "Asiakkaiden keskimääräinen ikä: " + getAverageAge());
        Trace.out(Trace.Level.INFO, "Asiakkaiden kuluttama rahamäärä yhteensä: " + getTotalMoneySpent() + " euroa.");
    }

    public HashSet<TapahtumanTyyppi> getpalvelupisteLista() {
        return palvelupisteLista;
    }

    public void generateRandomEnums() {
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


//    public String printpalvelupisteLista() {
//        StringBuilder sb = new StringBuilder();
//        for (TapahtumanTyyppi tyyppi : palvelupisteLista) {
//            try {
//                sb.append(tyyppi.getRuokatuote());
//                if (tyyppi != palvelupisteLista.toArray()[palvelupisteLista.size() - 1]) {
//                    sb.append(", ");
//                }
//            } catch (Exception e) {
//            }
//        }
//        return sb.toString();
//    }




}