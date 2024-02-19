package simu.model;
import java.util.Random;

public class Item {
    private String name;
    private double price;

    private int amount;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
        generateRandomItemAmount();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }

    public void generateRandomItemAmount(){
        Random random = new Random();
        this.amount = random.nextInt(1, 3);
    }


}
