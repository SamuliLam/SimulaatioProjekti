package simu.model.Tuotehallinta;

import simu.model.TapahtumanTyyppi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
/**
 * This class represents a category of groceries in the simulation model.
 * It manages the items within a specific category and their total price.
 */
public class GroceryCategory {
    /**
     * The category of this grocery category.
     */
    private TapahtumanTyyppi category;
    /**
     * The items in this grocery category.
     */
    private List<Item> items;
    /**
     * The groceries in this grocery category.
     */
    private Groceries groceries = new Groceries();
    /**
     * The random generator for this grocery category.
     */
    private static Random random = new Random();
    /**
     * Constructs a new grocery category with the given category.
     *
     * @param category the category for this grocery category
     */
    public GroceryCategory(TapahtumanTyyppi category) {
        this.category = category;
        this.items = new ArrayList<>();
        generateRandomItems(category);
    }
    /**
     * Adds the given item to this grocery category.
     *
     * @param item the item to add
     */
    public void addItem(Item item) {
        items.add(item);
    }
    /**
     * Gets the category of this grocery category.
     *
     * @return the category
     */
    public TapahtumanTyyppi getCategory() {
        return category;
    }
    /**
     * Gets the items in this grocery category.
     *
     * @return the items
     */
    public List<Item> getItems() {
        return items;
    }
    /**
     * Gets the total price of the items in this grocery category.
     *
     * @return the total price
     */
    public double getTotalItemPrice() {
        double totalPrice = 0;
        for (Item item : items) {
            totalPrice += item.getPrice() * item.getAmount();
        }
        return totalPrice;
    }
    /**
     * Converts the items in this grocery category to a string.
     *
     * @return the string representation of the items
     */
    public String itemsToString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append(item.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
    /**
     * Generates random items for this grocery category based on the given category.
     *
     * @param category the category to generate items for
     */
    public void generateRandomItems(TapahtumanTyyppi category) {
        int amount = random.nextInt(1, 3);
        switch (category) {
            case MEATDEP:
                generateItemsForCategory(amount, groceries.getLIHATUOTTEET());
                break;
            case FISHDEP:
                generateItemsForCategory(amount, groceries.getKALATUOTTEET());
                break;
            case BEERDEP:
                generateItemsForCategory(amount, groceries.getALKOHOLITUOTTEET());
                break;
            case CANDYDEP:
                generateItemsForCategory(amount, groceries.getKARKKITUOTTEET());
                break;
            default:
                break;
        }
    }
    /**
     * Generates random items for the given category.
     *
     * @param amount the amount of items to generate
     * @param categoryItems the items to generate from
     */
    private void generateItemsForCategory(int amount, HashMap<String, Double> categoryItems) {
        for (int i = 0; i < amount; i++) {
            int randomIndex = random.nextInt(0, categoryItems.size());
            String randomKey = (String) categoryItems.keySet().toArray()[randomIndex];
            double randomValue = categoryItems.get(randomKey);
            if (items.stream().noneMatch(item -> item.getName().equals(randomKey))) {
                Item item = new Item(randomKey, randomValue);
                addItem(item);
            }
        }
    }

    @Override
    public String toString() {
        return "GroceryCategory:" +
                category +
                ", items: " + items.toString();
    }
}
