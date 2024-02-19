package simu.model;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi {
	ARRMARKET("Saapuminen"), MEATDEP("Lihatiski"), BEERDEP("Alkoholiosasto"), FISHDEP("Kalatiski"), CANDYDEP("Karkkihylly"), CHECKOUTDEP("Kassa");
	private final String palvelupiste;

	TapahtumanTyyppi(String palvelupiste) {
		this.palvelupiste = palvelupiste;
    }


	public String getPalvelupiste() {
		return palvelupiste;
	}


}
