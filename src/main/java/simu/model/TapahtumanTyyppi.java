package simu.model;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi {
	ARRMARKET("Saapuminen", "saapuminen"), MEATDEP("Lihatiski", "sisäfilee"), BEERDEP("Alkoholiosasto", "kupari"), FISHDEP("Kalatiski", "lohi"), CANDYDEP("Karkkihylly", "aakkoset"), CHECKOUTDEP("Kassa", "kassa");
	private final String ruokatuote;
	private final String palvelupiste;
	TapahtumanTyyppi(String palvelupiste, String ruokatuote) {
		this.palvelupiste = palvelupiste;
		this.ruokatuote = ruokatuote;
	}

	public String getRuokatuote() {
		return ruokatuote;
	}

	public String getPalvelupiste() {
		return palvelupiste;
	}
}
