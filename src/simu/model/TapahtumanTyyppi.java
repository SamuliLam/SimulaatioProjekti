package simu.model;

import simu.framework.ITapahtumanTyyppi;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi implements ITapahtumanTyyppi{
	ARRMARKET, MEATDEP, BEERDEP, FISHDEP, CANDYDEP, CHECKOUTDEP;

}
