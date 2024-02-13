package simu.model;

import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

import java.util.HashSet;
import java.util.Random;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;

	private Palvelupiste[] palvelupisteet;

	public OmaMoottori() {

		// MEATDEP, BEERDEP, FISHDEP, CANDYDEP, CHECKOUTDEP;

		palvelupisteet = new Palvelupiste[5];

		palvelupisteet[0] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.MEATDEP);
		palvelupisteet[1] = new Palvelupiste(new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.BEERDEP);
		palvelupisteet[2] = new Palvelupiste(new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.FISHDEP);
		palvelupisteet[3] = new Palvelupiste(new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.CANDYDEP);
		palvelupisteet[4] = new Palvelupiste(new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.CHECKOUTDEP);


		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.ARRMARKET);

	}


	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {
		// B-vaiheen tapahtumat
		Asiakas asiakas = null;
		int palvelupisteValitsin = 0;
		switch ((TapahtumanTyyppi) t.getTyyppi()) {
			case ARRMARKET:
				asiakas = new Asiakas();
				if (asiakas != null) {
					TapahtumanTyyppi servedType = checkEnum(asiakas);
					removeEnumFromRuokalista(asiakas, TapahtumanTyyppi.ARRMARKET);
					palvelupisteValitsin = checkForEnumType(asiakas,servedType);
					palvelupisteet[palvelupisteValitsin].lisaaJonoon(asiakas);
				}
				saapumisprosessi.generoiSeuraava();
				break;
			case MEATDEP:
				asiakas = (Asiakas) palvelupisteet[palvelupisteValitsin].otaJonosta();
				if (asiakas != null) {
					TapahtumanTyyppi servedType = checkEnum(asiakas);
					palvelupisteValitsin = checkForEnumType(asiakas,servedType);
					palvelupisteet[palvelupisteValitsin].lisaaJonoon(asiakas);
					removeEnumFromRuokalista(asiakas, TapahtumanTyyppi.MEATDEP);
				}
				break;
			case BEERDEP:
				asiakas = (Asiakas) palvelupisteet[palvelupisteValitsin].otaJonosta();
				if (asiakas != null) {
					TapahtumanTyyppi servedType = checkEnum(asiakas);
					palvelupisteValitsin = checkForEnumType(asiakas,servedType);
					palvelupisteet[palvelupisteValitsin].lisaaJonoon(asiakas);
					removeEnumFromRuokalista(asiakas, TapahtumanTyyppi.BEERDEP);
				}
				break;
			case FISHDEP:
				asiakas = (Asiakas) palvelupisteet[palvelupisteValitsin].otaJonosta();
				if (asiakas != null) {
					TapahtumanTyyppi servedType = checkEnum(asiakas);
					palvelupisteValitsin = checkForEnumType(asiakas,servedType);
					palvelupisteet[palvelupisteValitsin].lisaaJonoon(asiakas);
					removeEnumFromRuokalista(asiakas, TapahtumanTyyppi.FISHDEP);
				}
				break;
			case CANDYDEP:
				asiakas = (Asiakas) palvelupisteet[palvelupisteValitsin].otaJonosta();
				if (asiakas != null) {
					TapahtumanTyyppi servedType = checkEnum(asiakas);
					palvelupisteValitsin = checkForEnumType(asiakas,servedType);
					palvelupisteet[palvelupisteValitsin].lisaaJonoon(asiakas);
					removeEnumFromRuokalista(asiakas, TapahtumanTyyppi.CANDYDEP);
				}
				break;
			case CHECKOUTDEP:
				asiakas = (Asiakas) palvelupisteet[palvelupisteValitsin].otaJonosta();
				if (asiakas != null) {
					removeEnumFromRuokalista(asiakas, TapahtumanTyyppi.CHECKOUTDEP);
					asiakas.setPoistumisaika(Kello.getInstance().getAika());
					asiakas.raportti();
				}
				break;
		}
	}

	private int checkForEnumType(Asiakas asiakas, TapahtumanTyyppi tapahtumanTyyppi) {
		try {
			HashSet<TapahtumanTyyppi> ruokalista = asiakas.getRuokalista();
			int palvelupisteValitsija = 0;
			if (ruokalista.contains(TapahtumanTyyppi.MEATDEP)) {
				palvelupisteValitsija = 0;
			} else if (ruokalista.contains(TapahtumanTyyppi.BEERDEP)) {
				palvelupisteValitsija = 1;
			} else if (ruokalista.contains(TapahtumanTyyppi.FISHDEP)) {
				palvelupisteValitsija = 2;
			} else if (ruokalista.contains(TapahtumanTyyppi.CANDYDEP)) {
				palvelupisteValitsija = 3;
			}
			System.out.println("Current palvelupiste: " + palvelupisteValitsija);
			return palvelupisteValitsija;
		} catch (NullPointerException e) {
			// Virheen näyttääminen
			System.out.println("Error: ruokalista is null.");
			e.printStackTrace();
			return -1; // palauttaa
		}
	}

	private void removeEnumFromRuokalista(Asiakas asiakas, TapahtumanTyyppi servedType) {
		TapahtumanTyyppi arrmarket = TapahtumanTyyppi.ARRMARKET;
        asiakas.getRuokalista().remove(arrmarket);
		asiakas.getRuokalista().remove(servedType);
		Trace.out(Trace.Level.INFO,"Asiakkaan " + asiakas.getId() + " poistettu ruokalistasta: " + servedType);
	}

	@Override
	protected void yritaCTapahtumat() {
		for (Palvelupiste p : palvelupisteet) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			}
		}
	}

	protected TapahtumanTyyppi checkEnum(Asiakas asiakas) {
		HashSet<TapahtumanTyyppi> ruokalista = asiakas.getRuokalista();
		TapahtumanTyyppi servedType = null;
		for (TapahtumanTyyppi ruokalistaEnumType : ruokalista) {
			servedType = ruokalistaEnumType;
		}
		return servedType;
	}

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
		for (Palvelupiste p : palvelupisteet) {
			p.raportti();
		}
	}
}

