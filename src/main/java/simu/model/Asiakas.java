package simu.model;

import dao.AsiakasDAO;
import eduni.distributions.Normal;
import simu.framework.*;
import simu.model.Tuotehallinta.GroceryCategory;
import simu.model.Tuotehallinta.Item;
import dao.AsiakasOstoslistaDAO;
import datasource.MariaDbConnection;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
    private static Normal ageRandom = new Normal(40, 100);
    private static List<Asiakas> asiakkaat = new ArrayList<>();
    private static HashMap<Integer, Integer> ikaJakauma = new HashMap<>();

    private static HashMap<Asiakas, Double> spentmoneyPerAsiakas = new HashMap<>();
    private static HashMap<TapahtumanTyyppi, HashMap<String, Integer>> soldProducts = new HashMap<>();
    private static long sum = 0;
    private static double totalMoneySpent = 0;
    private static double totalSpentMoneyAtCheckout = 0;
    private static int i = 1;
    private final int ika;
    Random random = new Random();
    private ArrayList<GroceryCategory> groceryList;
    private GroceryCategory groceryCategory;
    private TapahtumanTyyppi palvelupisteListaEnumType;
    private TapahtumanTyyppi[] palvelupisteListaEnumValues = TapahtumanTyyppi.values();
    private HashSet<TapahtumanTyyppi> palvelupisteLista;
    private double saapumisaika;
    private double poistumisaika;
    private int id;
    private double spentMoney;

    public Asiakas() throws SQLException {
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
        AsiakasDAO dao_customer = new AsiakasDAO(MariaDbConnection.getConnection());
        dao_customer.saveAsiakas(this);
    }

    // GETTERIT JA SETTERIT

    public static List<Asiakas> getAsiakkaat() {
        return asiakkaat;
    }

    public static double getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public static double getAverageMoneySpent() {
        return totalSpentMoneyAtCheckout / asiakkaat.size();
    }

    public static double getTotalSpentMoneyAtCheckout() {
        return totalSpentMoneyAtCheckout;
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

    public static HashMap<TapahtumanTyyppi, HashMap<String, Integer>> getSoldProducts() {
        return soldProducts;
    }

    public static HashMap<Integer, Integer> getAgeDistribution() {
        return ikaJakauma;
    }

    public static void addTotalSpentMoneyAtCheckout(double amount) {
        totalSpentMoneyAtCheckout += amount;
    }

    public static String completeRaportti() {
        StringBuilder sb = new StringBuilder();
        double totalMoneySpent = getTotalSpentMoneyAtCheckout();
        double averageMoneySpent = getAverageMoneySpent();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String formattedTotalMoneySpent = decimalFormat.format(totalMoneySpent);
        String formattedAverageMoneySpent = decimalFormat.format(averageMoneySpent);

        Trace.out(Trace.Level.INFO, "Asiakkaita yhteensä: " + asiakkaat.size());
        sb.append("Asiakkaita yhteensä: " + asiakkaat.size() + "\n");
        Trace.out(Trace.Level.INFO, "Asiakkaiden keskimääräinen rahankulutus " + formattedAverageMoneySpent + " euroa.");
        sb.append("Asiakkaiden keskimääräinen rahankulutus " + getAverageMoneySpent() + " euroa." + "\n");
        Trace.out(Trace.Level.INFO, "Asiakkaiden keskimääräinen ikä: " + getAverageAge());
        sb.append("Asiakkaiden keskimääräinen ikä: " + getAverageAge() + "\n");
        Trace.out(Trace.Level.INFO, "Asiakkaiden kuluttama rahamäärä yhteensä: " + formattedTotalMoneySpent + " euroa.");
        sb.append("Asiakkaiden kuluttama rahamäärä yhteensä: " + getTotalMoneySpent() + " euroa." + "\n");
        return sb.toString();
    }

    public double getPoistumisaika() {
        return poistumisaika;
    }

    public void setPoistumisaika(double poistumisaika) throws SQLException {
        this.poistumisaika = poistumisaika;
        AsiakasDAO.updatePoistumisaika(id, poistumisaika);
    }

    public double getSaapumisaika() {
        return saapumisaika;
    }

    public void setSaapumisaika(double saapumisaika) {
        this.saapumisaika = saapumisaika;
    }

    public int getId() {
        return id;
    }

    public double getSpentMoney() {
        return spentMoney;
    }

    public HashSet<TapahtumanTyyppi> getpalvelupisteLista() {
        return palvelupisteLista;
    }

    // MUUT METODIT

    public ArrayList<GroceryCategory> getGroceryList() {
        return groceryList;
    }

    public void addSoldProducts() {
        for (GroceryCategory productCategory : groceryList) {
            HashMap<String, Integer> productCounts = soldProducts.getOrDefault(productCategory.getCategory(), new HashMap<>());
            for (Item item : productCategory.getItems()) {
                productCounts.put(item.getName(), productCounts.getOrDefault(item.getName(), 0) + item.getQuantity());
            }
            soldProducts.put(productCategory.getCategory(), productCounts);
        }
    }

    public void addSpentMoney(double amount) {
        spentMoney += amount;
    }

    public void addSpentMoneyAtCheckout(double amount) {
        spentmoneyPerAsiakas.put(this, amount);
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
            sb.append(productCategory.itemsToString());

        }
        return sb.toString();
    }

    public void generateRandomRuokalista() throws SQLException {
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
        AsiakasOstoslistaDAO dao_products = new AsiakasOstoslistaDAO(MariaDbConnection.getConnection());
        saveShoppingListToDatabase(dao_products);
    }

    private void saveShoppingListToDatabase(AsiakasOstoslistaDAO dao) {
        try {
            // Check if the groceryList is not empty
            if (!groceryList.isEmpty()) {
                // Create a list to store items for saving to the database
                List<Item> itemsForDatabase = new ArrayList<>();

                // Loop through the groceryList to populate itemsForDatabase
                for (GroceryCategory category : groceryList) {
                    itemsForDatabase.addAll(category.getItems());
                }

                // Call the DAO method to save the shopping list items to the database
                dao.saveShoppingList(itemsForDatabase);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's requirements
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

    public int getIka() {
        return ika;
    }
}
