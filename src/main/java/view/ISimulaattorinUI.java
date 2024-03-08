package view;

import java.util.HashMap;

public interface ISimulaattorinUI {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getAika();
	public long getViive();

	public double getPalveluaikaMean();

	public double getPalveluaikaVarianssi();

	int getKassaValue();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	public void setLoppuaika(double aika);
	
	// Kontrolleri tarvitsee  
	public IVisualisointi getVisualisointi();

	public void updateAgeDistribution(HashMap<Integer, Integer> ageDistribution);
	public void setTuloste(String tulokset);

	int getKassaValue();
}
