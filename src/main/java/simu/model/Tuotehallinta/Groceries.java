package simu.model.Tuotehallinta;

import java.util.HashMap;
import java.util.Random;

public class Groceries {
    private final HashMap<String, Double> LIHATUOTTEET = new HashMap<>();
    private final HashMap<String, Double> KALATUOTTEET = new HashMap<>();

    private final HashMap<String, Double> ALKOHOLITUOTTEET = new HashMap<>();

    private final HashMap<String, Double> KARKKITUOTTEET = new HashMap<>();

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




    public HashMap<String, Double> getLIHATUOTTEET() {
        return LIHATUOTTEET;
    }

    public HashMap<String, Double> getKALATUOTTEET() {
        return KALATUOTTEET;
    }

    public HashMap<String, Double> getALKOHOLITUOTTEET() {
        return ALKOHOLITUOTTEET;
    }

    public HashMap<String, Double> getKARKKITUOTTEET() {
        return KARKKITUOTTEET;
    }




}
