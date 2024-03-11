package simu.framework;


import controller.IKontrolleriForM; // UUSI

public abstract class Moottori extends Thread implements IMoottori{  // UUDET MÄÄRITYKSET
	
	private double simulointiaika = 0;
	private long viive = 0;
	
	private Kello kello;
	
	protected Tapahtumalista eventList;

	protected IKontrolleriForM kontrolleri; // UUSI
	

	public Moottori(IKontrolleriForM kontrolleri){  // UUSITTU
		
		this.controller = kontrolleri;  //UUSI

		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia
		
		eventList = new Tapahtumalista();
		
		// Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa 
		
		
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
			suoritaBTapahtumat();
			yritaCTapahtumat();
		}
		tulokset();
		
	}
	
	private void suoritaBTapahtumat(){
		while (eventList.getSeuraavanAika() == kello.getAika()){
			suoritaTapahtuma(eventList.poista());
		}
	}

	protected abstract void yritaCTapahtumat();

	
	private double nykyaika(){
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

	protected abstract void alustukset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
	protected abstract void suoritaTapahtuma(Tapahtuma t);  // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
	protected abstract void tulokset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
}