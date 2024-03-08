package simu.model;

import controller.IKontrolleriForM;
import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import view.GUIKontrolleri;

import java.util.HashSet;
import java.util.Random;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;

	private int kassojenMaara = 1;
	private Palvelupiste[] palvelupisteet;
	private boolean MeatDepActivity;

	public OmaMoottori(IKontrolleriForM kontrolleri, double palveluaikaMean, double palveluaikaVarianssi) {

		super(kontrolleri);

		// MEATDEP, BEERDEP, FISHDEP, CANDYDEP, CHECKOUTDEP;

		palvelupisteet = new Palvelupiste[8];

		palvelupisteet[0] = new Palvelupiste(new Normal(palveluaikaMean, palveluaikaVarianssi), tapahtumalista, TapahtumanTyyppi.MEATDEP);
		palvelupisteet[1] = new Palvelupiste(new Normal(palveluaikaMean, palveluaikaVarianssi), tapahtumalista, TapahtumanTyyppi.BEERDEP);
		palvelupisteet[2] = new Palvelupiste(new Normal(palveluaikaMean, palveluaikaVarianssi), tapahtumalista, TapahtumanTyyppi.FISHDEP);
		palvelupisteet[3] = new Palvelupiste(new Normal(palveluaikaMean, palveluaikaVarianssi), tapahtumalista, TapahtumanTyyppi.CANDYDEP);
		palvelupisteet[4] = new Palvelupiste(new Normal(palveluaikaMean, palveluaikaVarianssi), tapahtumalista, TapahtumanTyyppi.CHECKOUTDEP);
		palvelupisteet[5] = new Palvelupiste(new Normal(palveluaikaMean,palveluaikaVarianssi), tapahtumalista, TapahtumanTyyppi.CHECKOUTDEP2);
		palvelupisteet[6] = new Palvelupiste(new Normal(palveluaikaMean,palveluaikaVarianssi), tapahtumalista, TapahtumanTyyppi.CHECKOUTDEP3);
		palvelupisteet[7] = new Palvelupiste(new Normal(palveluaikaMean, palveluaikaVarianssi), tapahtumalista, TapahtumanTyyppi.CHECKOUTDEP4);

		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.ARRMARKET);

		kontrolleri.updateMeatDepActivity(false);
		kontrolleri.updateBeerDepActivity(false);
		kontrolleri.updateFishDepActivity(false);
		kontrolleri.updateCandyDepActivity(false);
	}


	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {  // B-vaiheen tapahtumat
		// MEATDEP, BEERDEP, FISHDEP, CANDYDEP, CHECKOUTDEP;
		Asiakas asiakas;
		kassojenMaara = kontrolleri.setKassaMaara();
		System.out.println("Kassojen määrä: " + kassojenMaara);
		int palvelupisteValitsin = 0;
		switch (t.getTyyppi()) {
			case ARRMARKET:
				asiakas = new Asiakas();
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.ARRMARKET);
				palvelupisteValitsin = checkForEnumType(asiakas);
				palvelupisteet[palvelupisteValitsin].lisaaJonoon(asiakas);
				saapumisprosessi.generoiSeuraava();
				kontrolleri.visualisoiAsiakas(); // UUSI
				break;
			case MEATDEP:
				asiakas = palvelupisteet[0].otaJonosta();
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.MEATDEP);
				palvelupisteValitsin = checkForEnumType(asiakas);
				palvelupisteet[palvelupisteValitsin].lisaaJonoon(asiakas);
				break;
			case BEERDEP:
				asiakas = palvelupisteet[1].otaJonosta();
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.BEERDEP);
				palvelupisteValitsin = checkForEnumType(asiakas);
				palvelupisteet[palvelupisteValitsin].lisaaJonoon(asiakas);
				break;
			case FISHDEP:
				asiakas = palvelupisteet[2].otaJonosta();
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.FISHDEP);
				palvelupisteValitsin = checkForEnumType(asiakas);
				palvelupisteet[palvelupisteValitsin].lisaaJonoon(asiakas);
				break;
			case CANDYDEP:
				asiakas = palvelupisteet[3].otaJonosta();
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.CANDYDEP);
				palvelupisteValitsin = checkForEnumType(asiakas);
				palvelupisteet[palvelupisteValitsin].lisaaJonoon(asiakas);
				break;
			case CHECKOUTDEP:
				asiakas = palvelupisteet[4].otaJonosta();
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.CHECKOUTDEP);
				asiakas.addSpentMoneyAtCheckout(asiakas.getSpentMoney());
				Asiakas.addTotalSpentMoneyAtCheckout(asiakas.getSpentMoney());
				asiakas.addSoldProducts();
				asiakas.setPoistumisaika(Kello.getInstance().getAika());
				asiakas.raportti();
				kontrolleri.asiakasPoistuu();
				break;
			case CHECKOUTDEP2:
				asiakas = palvelupisteet[5].otaJonosta();
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.CHECKOUTDEP);
				asiakas.addSpentMoneyAtCheckout(asiakas.getSpentMoney());
				Asiakas.addTotalSpentMoneyAtCheckout(asiakas.getSpentMoney());
				asiakas.addSoldProducts();
				asiakas.setPoistumisaika(Kello.getInstance().getAika());
				asiakas.raportti();
				kontrolleri.asiakasPoistuu();
				break;
			case CHECKOUTDEP3:
				asiakas = palvelupisteet[6].otaJonosta();
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.CHECKOUTDEP);
				asiakas.addSpentMoneyAtCheckout(asiakas.getSpentMoney());
				Asiakas.addTotalSpentMoneyAtCheckout(asiakas.getSpentMoney());
				asiakas.addSoldProducts();
				asiakas.setPoistumisaika(Kello.getInstance().getAika());
				asiakas.raportti();
				kontrolleri.asiakasPoistuu();
				break;
			case CHECKOUTDEP4:
				asiakas = palvelupisteet[7].otaJonosta();
				removeEnumFrompalvelupisteLista(asiakas, TapahtumanTyyppi.CHECKOUTDEP);
				asiakas.addSpentMoneyAtCheckout(asiakas.getSpentMoney());
				Asiakas.addTotalSpentMoneyAtCheckout(asiakas.getSpentMoney());
				asiakas.addSoldProducts();
				asiakas.setPoistumisaika(Kello.getInstance().getAika());
				asiakas.raportti();
				kontrolleri.asiakasPoistuu();
				break;
		}
	}
	private int checkForEnumType(Asiakas asiakas) {
		try {
			HashSet<TapahtumanTyyppi> palvelupisteLista = asiakas.getpalvelupisteLista();
			int palvelupisteValitsija = 0;
			if (palvelupisteLista.contains(TapahtumanTyyppi.MEATDEP)) {
				palvelupisteValitsija = 0;
			} else if (palvelupisteLista.contains(TapahtumanTyyppi.BEERDEP)) {
				palvelupisteValitsija = 1;
			} else if (palvelupisteLista.contains(TapahtumanTyyppi.FISHDEP)) {
				palvelupisteValitsija = 2;
			} else if (palvelupisteLista.contains(TapahtumanTyyppi.CANDYDEP)) {
				palvelupisteValitsija = 3;
			}
			else
			{
				switch (kassojenMaara) {
					case 1:
						palvelupisteValitsija = 4;
						break;
					case 2:
						if (palvelupisteet[4].getJononPituus() < 4) {
							palvelupisteValitsija = 4;
						} else if (palvelupisteet[5].getJononPituus() < 6) {
							palvelupisteValitsija = 5;
						}
						break;
					case 3:
						if (palvelupisteet[4].getJononPituus() < 3) {
							palvelupisteValitsija = 4;
						} else if (palvelupisteet[5].getJononPituus() < 4) {
							palvelupisteValitsija = 5;
						}
						else if (palvelupisteet[6].getJononPituus() < 6) {
							palvelupisteValitsija = 6;
						}
						break;
					case 4:
						if (palvelupisteet[4].getJononPituus() < 2) {
							palvelupisteValitsija = 4;
						} else if (palvelupisteet[5].getJononPituus() < 3) {
							palvelupisteValitsija = 5;
						}
						else if (palvelupisteet[6].getJononPituus() < 4) {
							palvelupisteValitsija = 6;
						} else if (palvelupisteet[7].getJononPituus() < 6) {
							palvelupisteValitsija = 7;
						}
						break;
					default:
						palvelupisteValitsija = 4;
						break;
				}
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
	private void removeEnumFrompalvelupisteLista(Asiakas asiakas, TapahtumanTyyppi servedType) {
		TapahtumanTyyppi arrmarket = TapahtumanTyyppi.ARRMARKET;
		asiakas.getpalvelupisteLista().remove(arrmarket);
		asiakas.getpalvelupisteLista().remove(servedType);
		Trace.out(Trace.Level.INFO,"Asiakkaan " + asiakas.getId() + " palvelupisteListasta poistettu: " + servedType);
	}

	@Override
	protected void yritaCTapahtumat() {
		kontrolleri.updateMeatDepActivity(palvelupisteet[0].onVarattu());
		kontrolleri.updateBeerDepActivity(palvelupisteet[1].onVarattu());
		kontrolleri.updateFishDepActivity(palvelupisteet[2].onVarattu());
		kontrolleri.updateCandyDepActivity(palvelupisteet[3].onVarattu());
		for (Palvelupiste p : palvelupisteet) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			}
		}
	}

	@Override
	protected void tulokset() {
		StringBuilder tulokset = new StringBuilder();
		tulokset.append("Simulointi päättyi kello ").append(Kello.getInstance().getAika()).append("\n");
		for (Palvelupiste p : palvelupisteet) {
			tulokset.append(p.raportti()).append("\n");
		}
		tulokset.append(Asiakas.completeRaportti()).append("\n");

		// UUTTA graafista
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());

		kontrolleri.naytaTulokset(tulokset.toString());
	}

	public void setKassojenMaara(int kassaMaara)
	{
		this.kassojenMaara = kassaMaara;
	}
}

