package simu.model;

import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

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

        //palvelupisteet[2]=new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP3);

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
        switch ((TapahtumanTyyppi) t.getTyyppi()) {

			case ARRMARKET:
				palvelupisteet[0].lisaaJonoon(new Asiakas());
				saapumisprosessi.generoiSeuraava();
				break;
			case MEATDEP:
				asiakas = (Asiakas) palvelupisteet[0].otaJonosta();
				palvelupisteet[1].lisaaJonoon(asiakas);
				break;
			case BEERDEP:
				asiakas = (Asiakas) palvelupisteet[1].otaJonosta();
				palvelupisteet[2].lisaaJonoon(asiakas);
				break;
			case FISHDEP:
				asiakas = (Asiakas) palvelupisteet[2].otaJonosta();
				palvelupisteet[3].lisaaJonoon(asiakas);
				break;
			case CANDYDEP:
				asiakas = (Asiakas) palvelupisteet[3].otaJonosta();
				palvelupisteet[4].lisaaJonoon(asiakas);
				break;
			case CHECKOUTDEP:
				asiakas = (Asiakas) palvelupisteet[4].otaJonosta();
				asiakas.setPoistumisaika(Kello.getInstance().getAika());
				asiakas.raportti();
				break;
		}
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
        System.out.println("Tulokset: ");
        System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
        System.out.println("Tulokset ... puuttuvat vielä");


        // Tulostetaan palvelupisteiden raportit
        for (Palvelupiste p : palvelupisteet) {
            p.raportti();
        }
        // Voidaan tulostaa myös raportti jokaisesta asiakkaasta tismaalleen samalla tavalla kuin palvelupisteistä


        // Kutsumalla asiakas-luokan metodia completeRaportti saadaan kaikista asiakkaista raportti
        // Tällä hetkellä raportti sisältää asiakkaiden määrän ja keskimääräisen rahankäytön
        Asiakas.completeRaportti();
    }


}

