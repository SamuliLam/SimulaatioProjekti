package simu.framework;

import simu.model.TapahtumanTyyppi;
public class Tapahtuma implements Comparable<Tapahtuma> {
	private TapahtumanTyyppi tyyppi;
	private double aika;

	public Tapahtuma(TapahtumanTyyppi tyyppi, double aika){
		this.tyyppi = tyyppi;
		this.aika = aika;
	}

	public TapahtumanTyyppi getTyyppi() {
		return tyyppi;
	}

	public double getAika() {
		return aika;
	}

	@Override
	public int compareTo(Tapahtuma arg) {
		if (this.aika < arg.aika) return -1;
		else if (this.aika > arg.aika) return 1;
		return 0;
	}
}
