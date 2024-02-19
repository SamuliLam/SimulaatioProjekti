package controller;

import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;
import view.ISimulaattorinUI;

import java.util.HashMap;

public class Kontrolleri implements IKontrolleriForM, IKontrolleriForV{   // UUSI
	
	private IMoottori moottori; 
	private ISimulaattorinUI ui;

	private Asiakas asiakas;
	private Palvelupiste palvelupiste;
	
	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
		
	}

	
	// Moottorin ohjausta:
		
	@Override
	public void kaynnistaSimulointi() {
		moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(ui.getViive());
		ui.getVisualisointi().tyhjennaNaytto();
		((Thread)moottori).start();
		//((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?
		HashMap<Integer, Integer> ageDistribution = Asiakas.getAgeDistribution();
		// Lähetä ikäjakauma view modeliin
		ui.updateAgeDistribution(ageDistribution);
	}
	
	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*1.10));
	}

	// Hae ja välitä ikäjakauma modelin ja viewin välillä
	@Override
	public HashMap<Integer, Integer> getAgeDistribution() {
		return Asiakas.getAgeDistribution();
	}

	public HashMap<String, Integer> getPalvelupisteDistribution() {
		HashMap<String, Integer> palvelupisteDistribution = Palvelupiste.getPalveluLuku();
		return palvelupisteDistribution;
	}

	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*0.9));
	}
	
	
	
	// Simulointitulosten välittämistä käyttöliittymään.
	// Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:
		
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(()->ui.setLoppuaika(aika)); 
	}

	
	@Override
	public void visualisoiAsiakas() {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualisointi().uusiAsiakas();
			}
		});
	}
}
