package simu.model.Tuotehallinta;
import java.util.Random;


/**
 * This class represents an item in the simulation model.
 * It manages the name, price, and amount of the item.
 * An item is a product that can be bought by a customer.
 */
public class Item {
    /**
     * The name of the item.
     */
    private String name;
    /**
     * The price of the item.
     */
    private double price;

    private int amount;

    /**
     * Constructs a new item with the given name, price and amount.
     *
     * @param name  the name of the item
     * @param price the price of the item
     */
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
        generateRandomItemAmount();
    }
    /**
     * Gets the name of the item.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the item.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the amount of the item.
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }
    /**
     * Returns a string representation of the Item object.
     *
     * @return a string that contains the name, price, and amount of the item
     */
    @Override
    public String toString() {
        return "Tuote: "+ name +
                ", hinta " + price +
                ", määrä " + amount;
    }

    /**
     * Generates a random amount for the item in range of 1 to 3.
     */
    public void generateRandomItemAmount(){
        Random random = new Random();
        this.amount = random.nextInt(1, 3);
    }

    /**
     * Gets the quantity of the item.
     *
     * @return the quantity
     */
    public Integer getQuantity() {
        return amount;
    }
}
