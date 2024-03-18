package simu.framework;

import controller.IKontrolleriForM; // UUSI
import java.sql.SQLException;

public abstract class Moottori extends Thread implements IMoottori{  // UUDET MÄÄRITYKSET
	private double simulointiaika = 0;
	private long viive = 0;
	private Kello kello;
	protected Tapahtumalista eventList;
	protected IKontrolleriForM controller;

	public Moottori(IKontrolleriForM kontrolleri){
		
		this.controller = kontrolleri;

		kello = Kello.getInstance();
		
		eventList = new Tapahtumalista();
	}

	@Override
	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}

	@Override // UUSI
	public void setViive(long viive) {
		this.viive = viive;
	}

	@Override // UUSI 
	public long getViive() {
		return viive;
	}

	@Override
	public void run(){ // Entinen aja()
		alustukset(); // luodaan mm. ensimmäinen tapahtuma
		while (simuloidaan()){
			viive(); // UUSI
			kello.setAika(nykyaika());
            try {
                suoritaBTapahtumat();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            yritaCTapahtumat();
		}
		tulokset();
	}

	private void suoritaBTapahtumat() throws SQLException {
		while (eventList.getSeuraavanAika() == kello.getAika()){
			suoritaTapahtuma(eventList.poista());
		}
	}

	protected abstract void yritaCTapahtumat();
	private double nykyaika() {
		return eventList.getSeuraavanAika();
	}

	private boolean simuloidaan(){
		Trace.out(Trace.Level.INFO, "Kello on: " + kello.getAika());
		return kello.getAika() < simulointiaika;
	}

	private void viive() { // UUSI
		Trace.out(Trace.Level.INFO, "Viive " + viive);
		try {
			sleep(viive);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected abstract void alustukset();

	protected abstract void suoritaTapahtuma(Tapahtuma t) throws SQLException;

	protected abstract void tulokset();

}