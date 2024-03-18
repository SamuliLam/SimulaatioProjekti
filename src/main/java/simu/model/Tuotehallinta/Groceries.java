package simu.model.Tuotehallinta;

import java.util.HashMap;
/**
 * This class represents the groceries in the simulation model.
 * It manages the different categories of products and their prices.
 */
public class Groceries {
    /**
     * The meat products in the simulation.
     */
    private final HashMap<String, Double> LIHATUOTTEET = new HashMap<>();
    /**
     * The fish products in the simulation.
     */
    private final HashMap<String, Double> KALATUOTTEET = new HashMap<>();
    /**
     * The alcohol products in the simulation.
     */
    private final HashMap<String, Double> ALKOHOLITUOTTEET = new HashMap<>();
    /**
     * The candy products in the simulation.
     */
    private final HashMap<String, Double> KARKKITUOTTEET = new HashMap<>();
    /**
     * Constructs the groceries with predefined products and their prices.
     */
    public Groceries() {
        LIHATUOTTEET.put("Atria nautajauheliha", 4.59);
        LIHATUOTTEET.put("Naudan ulkofilee", 19.95);
        LIHATUOTTEET.put("Atria Hornet", 3.99);

        KALATUOTTEET.put("Kokonainen kuha", 24.99);
        KALATUOTTEET.put("Kirjolohi", 26.99);
        KALATUOTTEET.put("Findus kalafileet", 2.99);

        ALKOHOLITUOTTEET.put("Karhu 24-pack", 32.64);
        ALKOHOLITUOTTEET.put("Lonkero Pineapple 6-pack", 17.94);
        ALKOHOLITUOTTEET.put("Corona Extra", 2.59);

        KARKKITUOTTEET.put("Hedelmä Aakkoset", 3.09);
        KARKKITUOTTEET.put("Remix karkkipussi", 3.49);
        KARKKITUOTTEET.put("Fazer maitosuklaalevy", 3.09);
    }
    /**
     * Gets the meat products in the simulation.
     *
     * @return the meat products
     */
    public HashMap<String, Double> getLIHATUOTTEET() {
        return LIHATUOTTEET;
    }
    /**
     * Gets the fish products in the simulation.
     *
     * @return the fish products
     */
    public HashMap<String, Double> getKALATUOTTEET() {
        return KALATUOTTEET;
    }
    /**
     * Gets the alcohol products in the simulation.
     *
     * @return the alcohol products
     */
    public HashMap<String, Double> getALKOHOLITUOTTEET() {
        return ALKOHOLITUOTTEET;
    }
    /**
     * Gets the candy products in the simulation.
     *
     * @return the candy products
     */
    public HashMap<String, Double> getKARKKITUOTTEET() {
        return KARKKITUOTTEET;
    }
}