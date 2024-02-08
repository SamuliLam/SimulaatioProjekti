package simu.model;

import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

public class OmaMoottori extends Moottori {

    private Saapumisprosessi saapumisprosessi;

    private Palvelupiste[] palvelupisteet;

    public OmaMoottori() {


        palvelupisteet = new Palvelupiste[2];


        palvelupisteet[0] = new Palvelupiste(new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.MEATDEP);
        palvelupisteet[1] = new Palvelupiste(new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.CHECKOUTDEP);
        //palvelupisteet[2]=new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP3);

        saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.ARRMARKET);

    }


    @Override
    protected void alustukset() {
        saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
    }

    @Override
    protected void suoritaTapahtuma(Tapahtuma t) {  // B-vaiheen tapahtumat

        Asiakas a;
        switch ((TapahtumanTyyppi) t.getTyyppi()) {

            case ARRMARKET:
                palvelupisteet[0].lisaaJonoon(new Asiakas());
                saapumisprosessi.generoiSeuraava();
                break;
            case MEATDEP:

                a = (Asiakas) palvelupisteet[0].otaJonosta();
                palvelupisteet[1].lisaaJonoon(a);
                break;
            case ARRCHECKOUT:
                a = (Asiakas) palvelupisteet[1].otaJonosta();
                a.setPoistumisaika(Kello.getInstance().getAika());
                break;
//			case DEP3:
//				       a = (Asiakas)palvelupisteet[2].otaJonosta();
//					   a.setPoistumisaika(Kello.getInstance().getAika());
//			           a.raportti();
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
        System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
        System.out.println("Tulokset ... puuttuvat vielä");
        for (Palvelupiste p : palvelupisteet) {
            p.raportti();
        }
    }


}
