package simu.model;

import simu.framework.*;

import java.util.ArrayList;
import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava


public class Palvelupiste {

	private final LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus
	private final ContinuousGenerator generator;
	private final Tapahtumalista tapahtumalista;
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	private final ArrayList<TapahtumanTyyppi> palveupisteidenTyypit = new ArrayList<>();

	private ArrayList<Double> palveluajat = new ArrayList<>();
	
	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	
	private boolean varattu = false;


	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;

	}


	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		jono.add(a);
		
	}


	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}


	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
		
		varattu = true;
		double palveluaika = generator.sample();
		palveluajat.add(palveluaika);
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
	}

	public void raportti(){
		double sum = 0;
		for (double d: palveluajat){
			sum += d;
		}
		double keskiarvo = sum/palveluajat.size();
		Trace.out(Trace.Level.INFO, "Palvelupisteessä palveltiin " + palveluajat.size() + " asiakasta");
		Trace.out(Trace.Level.INFO, "Palvelupisteessä palveluaikojen keskiarvo oli " + keskiarvo);
	}


	public boolean onVarattu(){
		return varattu;
	}



	public boolean onJonossa(){
		return jono.size() != 0;
	}

}
