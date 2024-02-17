package simu.model;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi {
	ARRMARKET("Saapuminen", "saapuminen", 0), MEATDEP("Lihatiski", "sisäfilee", 2), BEERDEP("Alkoholiosasto", "kupari", 10), FISHDEP("Kalatiski", "lohi", 12), CANDYDEP("Karkkihylly", "aakkoset", 3.5), CHECKOUTDEP("Kassa", "kassa", 0);
	private final String ruokatuote;
	private final String palvelupiste;
	private final double hinta;
	TapahtumanTyyppi(String palvelupiste, String ruokatuote, double hinta) {
		this.palvelupiste = palvelupiste;
		this.ruokatuote = ruokatuote;
        this.hinta = hinta;
    }

	public String getRuokatuote() {
		return ruokatuote;
	}

	public String getPalvelupiste() {
		return palvelupiste;
	}

	public double getHinta() {
		return hinta;
	}
}
