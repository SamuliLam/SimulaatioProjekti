package simu.model.Tuotehallinta;


import simu.model.TapahtumanTyyppi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroceryCategory {

    private TapahtumanTyyppi category;
    private List<Item> items;

    private Groceries groceries = new Groceries();

    public GroceryCategory(TapahtumanTyyppi category) {
        this.category = category;
        this.items = new ArrayList<>();
        generateRandomItems(category);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public TapahtumanTyyppi getCategory() {
        return category;
    }

    public String getItems() {
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append(item.toString());
            sb.append("\n");
        }
        return sb.toString();
    }


    public double getTotalItemPrice() {
        double totalPrice = 0;
        for (Item item : items) {
            totalPrice += item.getPrice() * item.getAmount();
        }
        return totalPrice;
    }

    public void generateRandomItems(TapahtumanTyyppi category) {
        Random random = new Random();
        int amount = random.nextInt(1, 3);
        switch (category) {
            case MEATDEP:
                for (int i = 0; i < amount; i++) {
                    int randomIndex = random.nextInt(1, groceries.getLIHATUOTTEET().size());
                    String randomKey = (String) groceries.getLIHATUOTTEET().keySet().toArray()[randomIndex];
                    double randomValue = groceries.getLIHATUOTTEET().get(randomKey);
                    //Only add the item if it's not already in the list
                    if (items.stream().noneMatch(item -> item.getName().equals(randomKey))) {
                        Item item = new Item(randomKey, randomValue);
                        addItem(item);
                    }
                }
                break;
            case FISHDEP:
                for (int i = 0; i < amount; i++) {
                    int randomIndex = random.nextInt(1, groceries.getKALATUOTTEET().size());
                    String randomKey = (String) groceries.getKALATUOTTEET().keySet().toArray()[randomIndex];
                    double randomValue = groceries.getKALATUOTTEET().get(randomKey);
                    //Only add the item if it's not already in the list
                    if (items.stream().noneMatch(item -> item.getName().equals(randomKey))) {
                        Item item = new Item(randomKey, randomValue);
                        addItem(item);
                    }
                }
                break;
            case BEERDEP:
                for (int i = 0; i < amount; i++) {
                    int randomIndex = random.nextInt(1, groceries.getALKOHOLITUOTTEET().size());
                    String randomKey = (String) groceries.getALKOHOLITUOTTEET().keySet().toArray()[randomIndex];
                    double randomValue = groceries.getALKOHOLITUOTTEET().get(randomKey);
                    //Only add the item if it's not already in the list
                    if (items.stream().noneMatch(item -> item.getName().equals(randomKey))) {
                        Item item = new Item(randomKey, randomValue);
                        addItem(item);
                    }
                }
                break;
            case CANDYDEP:
                for (int i = 0; i < amount; i++) {
                    int randomIndex = random.nextInt(1, groceries.getKARKKITUOTTEET().size());
                    String randomKey = (String) groceries.getKARKKITUOTTEET().keySet().toArray()[randomIndex];
                    double randomValue = groceries.getKARKKITUOTTEET().get(randomKey);
                    //Only add the item if it's not already in the list
                    if (items.stream().noneMatch(item -> item.getName().equals(randomKey))) {
                        Item item = new Item(randomKey, randomValue);
                        addItem(item);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return "GroceryCategory:" +
                category +
                ", items: " + items.toString();
    }

}
