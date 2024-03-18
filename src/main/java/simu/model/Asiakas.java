package simu.model;

import eduni.distributions.Normal;
import simu.framework.*;
import simu.model.Tuotehallinta.GroceryCategory;
import simu.model.Tuotehallinta.Item;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * This class represents a customer in the simulation model.
 * It manages the customer's age, grocery list, and service points.
 */
public class Asiakas {
    /**
     * The random generator for the customer's age. Uses a normal distribution.
     */
    private static Normal ageRandom = new Normal(40, 100);
    /**
     * A list of customers.
     */
    private static List<Asiakas> customers = new ArrayList<>();
    /**
     * A map of customer ages and their distribution.
     */
    private static HashMap<Integer, Integer> ageDistribution = new HashMap<>();
    /**
     * A map of customer spent money.
     */
    private static HashMap<Asiakas, Double> spentmoneyPerAsiakas = new HashMap<>();
    /**
     * A map of sold products for each category.
     */
    private static HashMap<TapahtumanTyyppi, HashMap<String, Integer>> soldProducts = new HashMap<>();
    /**
     * The total spent money at checkout.
     */
    private static double totalSpentMoneyAtCheckout = 0;
    /**
     * The last customer id.
     */
    private static int lastId = 1;
    /**
     * The customer's age.
     */
    private final int customerAge;
    /**
     * The customer's grocery list.
     */
    private ArrayList<GroceryCategory> groceryList;
    /**
     * The customer's grocery category.
     */
    private GroceryCategory groceryCategory;

    /**
     * All the possible service points.
     */
    private TapahtumanTyyppi[] servicePointListEnumValues = TapahtumanTyyppi.values();
    /**
     * The customer's service point list.
     */
    private HashSet<TapahtumanTyyppi> servicePointList;
    /**
     * The customer's arrival time.
     */
    private double arrivalTime;
    /**
     * The customer's departure time.
     */
    private double departureTime;
    /**
     * The customer's id.
     */
    private int id;
    /**
     * The customer's spent money.
     */
    private double spentMoney;
    
    /**
     * The string for the euro currency.
     */
    private static final String EUROA = " euroa.";

    /**
     * The random generator for the customer's age.
     */
    Random random = new Random();

    /**
     * Constructs a new customer with a random age, grocery list, and service points.
     *
     * @throws SQLException if an SQL exception occurs
     */
    public Asiakas() throws SQLException {
        id = lastId++;
        servicePointList = new HashSet<>();
        groceryList = new ArrayList<>();
        generateRandomRuokalista();
        customerAge = (int) (ageRandom.sample());
        spentMoney = 0;
        arrivalTime = Kello.getInstance().getAika();
        updateAgeDistribution(customerAge);
        customers.add(this);
        customerCreationInfo();
    }

    /**
     * Prints the customer creation information.
     */
    public void customerCreationInfo() {
        Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + arrivalTime + " ja on " + customerAge + " vuotias.");
        Trace.out(Trace.Level.INFO, "Asiakkaan " + getId() + " ruokalista: \n" + printGroceryList());
    }

/**
     * Gets the list of customers.
     *
     * @return the list of customers
     */
    public static List<Asiakas> getCustomers() {
        return customers;
    }

    /**
     * Gets the average money spent by customers.
     *
     * @return the average money spent
     */
    public static double getAverageMoneySpent() {
        return totalSpentMoneyAtCheckout / customers.size();
    }

    /**
     * Gets the total money spent by customers.
     *
     * @return the total money spent
     */
    public static double getTotalSpentMoneyAtCheckout() {
        return totalSpentMoneyAtCheckout;
    }

    /**
     * Gets the map of money spent by each customer.
     *
     * @return a map where the key is the customer and the value is the money spent by that customer
     */
    public static HashMap<Asiakas, Double> getSpentmoneyPerAsiakas() {
        return spentmoneyPerAsiakas;
    }

    /**
     * Calculates and returns the average age of all customers.
     *
     * @return the average age of customers
     */
    public static int getAverageAge() {
        int summa = 0;
        for (Map.Entry<Integer, Integer> entry : ageDistribution.entrySet()) {
            summa += (entry.getKey() * entry.getValue());
        }
        return summa / customers.size();
    }

    /**
     * Gets the map of sold products for each category.
     *
     * @return a map where the key is the category and the value is a map of products and their counts
     */
    public static HashMap<TapahtumanTyyppi, HashMap<String, Integer>> getSoldProducts() {
        return soldProducts;
    }
    
    /**
     * Gets the map of customer ages and their distribution.
     *
     * @return a map where the key is the age and the value is the distribution of that age
     */
    public static HashMap<Integer, Integer> getAgeDistribution() {
        return ageDistribution;
    }

    /**
     * Adds the given amount to the total spent money at checkout.
     *
     * @param amount the amount to add
     */
    public static void addTotalSpentMoneyAtCheckout(double amount) {
        totalSpentMoneyAtCheckout += amount;
    }
    
    /**
     * Gets the customer's departure time.
     *
     * @return the departure time
     */
    public double getDepartureTime() {
        return departureTime;
    }

    /**
     * Gets the customer's arrival time.
     *
     * @return the arrival time
     */
    public double getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Gets the customer's id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the customer's age.
     *
     * @return the age
     */
    public int getIka() {
        return customerAge;
    }

    /**
     * Gets the customer's spent money.
     *
     * @return the spent money
     */
    public double getSpentMoney() {
        return spentMoney;
    }

    /**
     * Gets the customer's service point list.
     *
     * @return the service point list
     */
    public HashSet<TapahtumanTyyppi> getServicePointList() {
        return servicePointList;
    }

    /**
     * Gets the customer's grocery list.
     *
     * @return the grocery list
     */
    public ArrayList<GroceryCategory> getGroceryList() {
        return groceryList;
    }

    /**
     * Sets the customer's id.
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the customer's departure time.
     *
     * @param departureTime the departure time
     * @throws SQLException if an SQL exception occurs
     */
    public void setDepartureTime(double departureTime) throws SQLException {
        this.departureTime = departureTime;
    }

    /**
     * This method updates the soldProducts map with the products bought by the customer.
     * For each category in the customer's grocery list, it retrieves or creates a map of product counts.
     * Then, for each item in the category, it increments the count of that item in the product counts map.
     * Finally, it updates the soldProducts map with the updated product counts map for the category.
     */
    public void addSoldProducts() {
        for (GroceryCategory productCategory : groceryList) {
            HashMap<String, Integer> productCounts = soldProducts.getOrDefault(productCategory.getCategory(), new HashMap<>());
            for (Item item : productCategory.getItems()) {
                productCounts.put(item.getName(), productCounts.getOrDefault(item.getName(), 0) + item.getQuantity());
            }
            soldProducts.put(productCategory.getCategory(), productCounts);
        }
    }

    /**
     * Adds the given amount to the customer's spent money.
     *
     * @param amount the amount to add
     */
    public void addSpentMoney(double amount) {
        spentMoney += amount;
    }
    
    /**
     * Adds the given amount to the customer's spent money at checkout.
     *
     * @param amount the amount to add
     */
    public void addSpentMoneyAtCheckout(double amount) {
        spentmoneyPerAsiakas.put(this, amount);
    }

    /**
     * Updates the age distribution map with the given age.
     *
     * @param age the age to update
     */
    public void updateAgeDistribution(int age) {
        if (ageDistribution.containsKey(age)) {
            ageDistribution.put(age, ageDistribution.get(age) + 1);
        } else {
            ageDistribution.put(age, 1);
        }
    }

    /**
     * Prints the customer's grocery list.
     *
     * @return the string representation of the grocery list
     */
    public String printGroceryList() {
        StringBuilder sb = new StringBuilder();
        for (GroceryCategory productCategory : groceryList) {
            sb.append(productCategory.itemsToString());
        }
        return sb.toString();
    }

    /**
     * Generates a random grocery list for the customer.
     *
     * @throws SQLException if an SQL exception occurs
     */
    public void generateRandomRuokalista() throws SQLException {
        servicePointList.add(TapahtumanTyyppi.ARRMARKET);
        servicePointList.add(TapahtumanTyyppi.CHECKOUTDEP);
        int randomServicePointListSize = random.nextInt(TapahtumanTyyppi.values().length - 3, TapahtumanTyyppi.values().length);
        while (servicePointList.size() < randomServicePointListSize) {
            TapahtumanTyyppi randomEnum = getRandomServicePoint();
            if (!servicePointList.contains(randomEnum)) {
                servicePointList.add(randomEnum);
                if (randomEnum != TapahtumanTyyppi.ARRMARKET && randomEnum != TapahtumanTyyppi.CHECKOUTDEP) {
                    groceryList.add(new GroceryCategory(randomEnum));
                }
            }
        }
    }

    /**
     * Gets a random service point from the service point list.
     *
     * @return a random service point
     */
    private TapahtumanTyyppi getRandomServicePoint() {
        int randomIndex = random.nextInt(servicePointListEnumValues.length);
        return servicePointListEnumValues[randomIndex];
    }

    /**
     * This method generates a report for the customer.
     * It logs the following information:
     * - The customer's id and status (finished)
     * - The customer's arrival time
     * - The customer's departure time
     * - The duration of the customer's stay
     * - The amount of money the customer spent
     * - The customer's grocery list
     */
    public void customerReport() {
        Trace.out(Trace.Level.INFO, "\nAsiakas " + id + " valmis! ");
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + arrivalTime);
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + departureTime);
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (departureTime - arrivalTime));
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " kulutti: " + spentMoney);
        Trace.out(Trace.Level.INFO, "Asiakas " + id + " ruokalista: " + printGroceryList());
    }

    /**
     * This method generates a report for all customers.
     * It logs the following information:
     * - The average money spent by customers
     * - The average age of customers
     * - The total money spent by customers
     * @return a string containing the report of customers
     */
    public static String customersReport() {
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

}
