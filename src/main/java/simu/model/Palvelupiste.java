package simu.model;

import java.util.*;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;
import simu.model.Tuotehallinta.GroceryCategory;

import java.util.ArrayList;
import java.util.LinkedList;

public class Palvelupiste {

	private final LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus
	private final ContinuousGenerator generator;
	private final Tapahtumalista tapahtumalista;
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	private static final HashMap<String, Integer> palvelupisteidenKaynti = new HashMap<>();
	private static ArrayList<Double> palveluajat = new ArrayList<>();

    private double totalTimeServiced = 0.0;
    private static final HashMap<Palvelupiste, Double> palveluAjatPerPalvelupiste = new HashMap<>();

    //JonoStartegia strategia; //optio: asiakkaiden järjestys

    private boolean varattu = false;


    public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi) {
        this.tapahtumalista = tapahtumalista;
        this.generator = generator;
        this.skeduloitavanTapahtumanTyyppi = tyyppi;

    }

    public void lisaaJonoon(Asiakas a) {   // Jonon 1. asiakas aina palvelussa
        jono.add(a);

    }

    public Asiakas otaJonosta() {  // Poistetaan palvelussa ollut
        varattu = false;
        return jono.poll();
    }

    public void aloitaPalvelu() {
        //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

        Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu " + skeduloitavanTapahtumanTyyppi.getPalvelupiste() + " asiakkaalle " + jono.peek().getId());

        varattu = true;
        double palveluaika = generator.sample();

        // Simppeli hinnoittelu, joka perustuu palveluajan pituuteen ja se kerrotaan hintaPerAjalla. Myöhemmin kertoimen voi muuttaa tuotehintoihin perustuvaksi.

        // Haetaan asiakas jonosta ja tallennetaan se muuttujaan
        Asiakas asiakas = jono.peek();

        if (asiakas != null){
            for (GroceryCategory category : asiakas.getGroceryList()){
                if (category.getCategory() == skeduloitavanTapahtumanTyyppi){
                    asiakas.addSpentMoney(category.getTotalItemPrice());
                    Asiakas.addTotalMoneySpent(category.getTotalItemPrice());
                }
            }
        }

		palveluajat.add(palveluaika);
		palvelupisteidenKaynti.put(skeduloitavanTapahtumanTyyppi.getPalvelupiste(), palveluajat.size());
        palveluAjatPerPalvelupiste.put(this, totalTimeServiced);
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika() + palveluaika));
	}

    public void raportti() {
        double sum = 0;
        for (double d : palveluajat) {
            sum += d;
        }
        double keskiarvo = sum / palveluajat.size();
        calculateTotalTimePerPalvelupiste();
        Trace.out(Trace.Level.INFO, "Palvelupisteessä " + skeduloitavanTapahtumanTyyppi.getPalvelupiste() + " palveltiin " + palveluajat.size() + " asiakasta");
        Trace.out(Trace.Level.INFO, "Palvelupisteessä " + skeduloitavanTapahtumanTyyppi.getPalvelupiste() + " palveluaikojen keskiarvo oli " + keskiarvo);
    }


    public void calculateTotalTimePerPalvelupiste()
    {
        for (int i = 0; i < palveluajat.size(); i++) {
            totalTimeServiced += palveluajat.get(i);
        }
    }
    public boolean onVarattu() {
        return varattu;
    }


    public boolean onJonossa() {
        return !jono.isEmpty();
    }

	public static HashMap<String, Integer> getPalveluLuku()
	{
		return palvelupisteidenKaynti;
	}

    public static HashMap<Palvelupiste, Double> getAjatPerPalvelupiste() { return palveluAjatPerPalvelupiste;}

}