package simu.model;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi {
	ARRMARKET("Saapuminen"), MEATDEP("Lihatiski"), BEERDEP("Alkoholiosasto"), FISHDEP("Kalatiski"), CANDYDEP("Karkkihylly"), CHECKOUTDEP("Kassa 1"), CHECKOUTDEP2("Kassa 2"),CHECKOUTDEP3("Kassa 3"), CHECKOUTDEP4("Kassa 4");
	private final String palvelupiste;

	TapahtumanTyyppi(String palvelupiste) {
		this.palvelupiste = palvelupiste;
	}


	public String getPalvelupiste() {
		return palvelupiste;
	}


}