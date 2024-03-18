package simu.model;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi {
	/**
	 * The arrival event type.
	 */
	ARRMARKET("Saapuminen"),
	/**
	 * The meat service point event type.
	 */
	MEATDEP("Lihatiski"),
	/**
	 * The alcohol service point event type.
	 */
	BEERDEP("Alkoholiosasto"),
	/**
	 * The fish service point event type.
	 */
	FISHDEP("Kalatiski"),
	/**
	 * The candy service point event type.
	 */
	CANDYDEP("Karkkihylly"),
	/**
	 * The checkout service point event type.
	 */
	CHECKOUTDEP("Kassa 1"),
	/**
	 * The checkout service point event type.
	 */
	CHECKOUTDEP2("Kassa 2"),
	/**
	 * The checkout service point event type.
	 */
	CHECKOUTDEP3("Kassa 3"),
	/**
	 * The checkout service point event type.
	 */
	CHECKOUTDEP4("Kassa 4");
	/**
	 * The service point for this event type.
	 */
	private final String palvelupiste;

	TapahtumanTyyppi(String palvelupiste) {
		this.palvelupiste = palvelupiste;
	}


	public String getPalvelupiste() {
		return palvelupiste;
	}


}