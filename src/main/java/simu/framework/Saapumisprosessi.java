package simu.framework;

import eduni.distributions.*;
import simu.model.TapahtumanTyyppi;

public class Saapumisprosessi {
	private ContinuousGenerator generaattori;
	private Tapahtumalista eventList;
	private TapahtumanTyyppi tyyppi;

	public Saapumisprosessi(ContinuousGenerator g, Tapahtumalista tl, TapahtumanTyyppi tyyppi){
		this.generaattori = g;
		this.eventList = tl;
		this.tyyppi = tyyppi;
	}

	public void generoiSeuraava(){
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika()+generaattori.sample());
		eventList.lisaa(t);
	}
}
