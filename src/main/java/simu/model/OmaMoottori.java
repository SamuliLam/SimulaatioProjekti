package simu.model;

import controller.IKontrolleriForM;
import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

import java.util.HashSet;
import java.util.Random;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;

	private Palvelupiste[] palvelupisteet;

	public OmaMoottori(IKontrolleriForM kontrolleri){

		super(kontrolleri);

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
	protected void suoritaTapahtuma(Tapahtuma t) {  // B-vaiheen tapahtumat
		// MEATDEP, BEERDEP, FISHDEP, CANDYDEP, CHECKOUTDEP;
		Asiakas asiakas;
		switch (t.getTyyppi()) {

			case ARRMARKET:
				asiakas = new Asiakas();
				palvelupisteet[0].lisaaJonoon(asiakas);
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.ARRMARKET);
				saapumisprosessi.generoiSeuraava();
				kontrolleri.visualisoiAsiakas(); // UUSI
				break;
			case MEATDEP:
				asiakas = palvelupisteet[0].otaJonosta();
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.MEATDEP);
				palvelupisteet[1].lisaaJonoon(asiakas);
				break;
			case BEERDEP:
				asiakas = palvelupisteet[1].otaJonosta();
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.BEERDEP);
				palvelupisteet[2].lisaaJonoon(asiakas);
				break;
			case FISHDEP:
				asiakas = palvelupisteet[2].otaJonosta();
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.FISHDEP);
				palvelupisteet[3].lisaaJonoon(asiakas);
				break;
			case CANDYDEP:
				asiakas = palvelupisteet[3].otaJonosta();
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.CANDYDEP);
				palvelupisteet[4].lisaaJonoon(asiakas);
				break;
			case CHECKOUTDEP:
				asiakas = palvelupisteet[4].otaJonosta();
				Asiakas.addTotalSpentMoneyAtCheckout(asiakas.getSpentMoney());
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.CHECKOUTDEP);
				asiakas.setPoistumisaika(Kello.getInstance().getAika());
				asiakas.raportti();
				break;
		}
	}

	private void removeEnumFrompalvelupisteLista(Asiakas asiakas, TapahtumanTyyppi servedType) {
		TapahtumanTyyppi arrmarket = TapahtumanTyyppi.ARRMARKET;
        asiakas.getpalvelupisteLista().remove(arrmarket);
		asiakas.getpalvelupisteLista().remove(servedType);
		Trace.out(Trace.Level.INFO,"Asiakkaan " + asiakas.getId() + " palvelupisteListasta poistettu: " + servedType);
	}

	@Override
	protected void yritaCTapahtumat() {
		for (Palvelupiste p : palvelupisteet) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			}
		}
	}

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		for (Palvelupiste p : palvelupisteet) {
			p.raportti();
		}
		Asiakas.completeRaportti();

		// UUTTA graafista
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
	}
}

