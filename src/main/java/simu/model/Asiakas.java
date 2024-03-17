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
    private static List<Asiakas> customers = new ArrayList<>();
    private static HashMap<Integer, Integer> ageDistribution = new HashMap<>();

    private static HashMap<Asiakas, Double> spentmoneyPerAsiakas = new HashMap<>();
    private static HashMap<TapahtumanTyyppi, HashMap<String, Integer>> soldProducts = new HashMap<>();
    private static double totalMoneySpent = 0;
    private static double totalSpentMoneyAtCheckout = 0;
    private static int i = 1;
    private final int customerAge;
    Random random = new Random();
    private ArrayList<GroceryCategory> groceryList;
    private GroceryCategory groceryCategory;
    private TapahtumanTyyppi[] servicePointListEnumValues = TapahtumanTyyppi.values();
    private HashSet<TapahtumanTyyppi> servicePointList;
    private double arrivalTime;
    private double departureTime;
    private int id = 0;
    private double spentMoney;

    public Asiakas() throws SQLException {
        // Tarkistetaan edellisen asiakkaan id tietokannasta ja lisätään yksi
        AsiakasDAO dao = new AsiakasDAO(MariaDbConnection.getConnection());
        this.id = i++;
        // servicePointList määrätään asiakkaalle
        servicePointList = new HashSet<>();
        groceryList = new ArrayList<>();
        // Luodaan random servicePointList tyypettäin asiakkaalle
        generateRandomRuokalista();
        customerAge = (int) (ageRandom.sample());
        spentMoney = 0;
        arrivalTime = Kello.getInstance().getAika();
        Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + arrivalTime + " ja on " + customerAge + " vuotias.");
        Trace.out(Trace.Level.INFO, "Asiakkaan " + getId() + " ruokalista: \n" + printGroceryList());
        updateAgeDistribution(customerAge);
        customers.add(this);
        AsiakasDAO dao_customer = new AsiakasDAO(MariaDbConnection.getConnection());
    }

    // GETTERIT JA SETTERIT

    public static List<Asiakas> getCustomers() {
        return customers;
    }

    public static double getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public static double getAverageMoneySpent() {
        return totalSpentMoneyAtCheckout / customers.size();
    }

    public static double getTotalSpentMoneyAtCheckout() {
        return totalSpentMoneyAtCheckout;
    }

    public static HashMap<Asiakas, Double> getSpentmoneyPerAsiakas() {
        return spentmoneyPerAsiakas;
    }

    public static int getAverageAge() {
        int summa = 0;
        for (Map.Entry<Integer, Integer> entry : ageDistribution.entrySet()) {
            summa += (entry.getKey() * entry.getValue());
        }
        return summa / customers.size();
    }

    public static HashMap<TapahtumanTyyppi, HashMap<String, Integer>> getSoldProducts() {
        return soldProducts;
    }

    public static HashMap<Integer, Integer> getAgeDistribution() {
        return ageDistribution;
    }

    public static void addTotalSpentMoneyAtCheckout(double amount) {
        totalSpentMoneyAtCheckout += amount;
    }

    private static final String EUROA = " euroa.";

    public static String completeReport() {

        StringBuilder sb = new StringBuilder();

        double totalMoneySpent = getTotalSpentMoneyAtCheckout();
        double averageMoneySpent = getAverageMoneySpent();

        DecimalFormat decimalFormat = new DecimalFormat("#0.00");

        String formattedTotalMoneySpent = decimalFormat.format(totalMoneySpent);
        String formattedAverageMoneySpent = decimalFormat.format(averageMoneySpent);

        Trace.out(Trace.Level.INFO, "Asiakkaiden keskimääräinen rahankulutus " + formattedAverageMoneySpent + EUROA);
        Trace.out(Trace.Level.INFO, "Asiakkaiden keskimääräinen ikä: " + getAverageAge());
        Trace.out(Trace.Level.INFO, "Asiakkaiden kuluttama rahamäärä yhteensä: " + formattedTotalMoneySpent + EUROA);

        sb.append("Asiakkaiden keskimääräinen rahankulutus ").append(formattedAverageMoneySpent).append(EUROA).append("\n");
        sb.append("Asiakkaiden keskimääräinen ikä: ").append(getAverageAge()).append("\n");
        sb.append("Asiakkaiden kuluttama rahamäärä yhteensä: ").append(formattedTotalMoneySpent).append(EUROA).append("\n");

        return sb.toString();
    }

    public double getdepartureTime() {
        return departureTime;
    }

    public void setdepartureTime(double departureTime) throws SQLException {
        this.departureTime = departureTime;
    }

    public double getarrivalTime() {
        return arrivalTime;
    }

    public void setarrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getId() {
        return id;
    }

    public double getSpentMoney() {
        return spentMoney;
    }

    public HashSet<TapahtumanTyyppi> getservicePointList() {
        return servicePointList;
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

    public void updateAgeDistribution(int age) {
        if (ageDistribution.containsKey(age)) {
            ageDistribution.put(age, ageDistribution.get(age) + 1);
        } else {
            ageDistribution.put(age, 1);
        }
    }

    public String printGroceryList() {
        StringBuilder sb = new StringBuilder();
        for (GroceryCategory productCategory : groceryList) {
            sb.append(productCategory.itemsToString());

        }
        return sb.toString();
    }

    public void generateRandomRuokalista() throws SQLException {
        // lisätään ARRMARKET pakolliseks ja ensimmäiseksi.
        servicePointList.add(TapahtumanTyyppi.ARRMARKET);
        servicePointList.add(TapahtumanTyyppi.CHECKOUTDEP);
        // asetetaan random määrä ruokatyyppejä asiakkaalle
        // Miinustetaan tapahtumantyyppi määrästä kaikki oikeat palvelupisteet paitsi yksi, jotta vähintään yksi oikea palvlupiste generoitaisiin
        int randomservicePointListSize = random.nextInt(TapahtumanTyyppi.values().length - 3, TapahtumanTyyppi.values().length);

        System.out.println("servicePointList koko: " + randomservicePointListSize);

        // Random indexia käyttäen etsitään random enum asiakkaalle
        while (servicePointList.size() < randomservicePointListSize) {
            // Random index luku jolla arvotaan enumi asiakkaalle
            int randomIndex = random.nextInt(servicePointListEnumValues.length);
            TapahtumanTyyppi randomEnum = servicePointListEnumValues[randomIndex];
            // lisätään random enumtyyppi servicePointListan.
            if (!servicePointList.contains(randomEnum)) { // Tarkista onko olemassa
                servicePointList.add(randomEnum);
                if (randomEnum != TapahtumanTyyppi.ARRMARKET && randomEnum != TapahtumanTyyppi.CHECKOUTDEP) {
                    groceryCategory = new GroceryCategory(randomEnum);
                    groceryList.add(groceryCategory);

                }
            }
        }
    }

    public void report() {
        Trace.out(Trace.Level.INFO, "\nAsiakas " + id + " valmis! ");
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + arrivalTime);
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + departureTime);
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (departureTime - arrivalTime));
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " kulutti: " + spentMoney);
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " ruokalista: " + printGroceryList());
    }

    public int getIka() {
        return customerAge;
    }
}
