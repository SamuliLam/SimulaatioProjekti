package view.rajapinnat;


public interface IVisualisointi {

	void tyhjennaNaytto();
	
	void uusiAsiakas();
	void asiakasPoistuu();
	void updateMeatDepActivity(boolean isReserved);

	void updateBeerDepActivity(boolean isReserved);

	void updateFishDepActivity(boolean isReserved);

	void updateCandyDepActivity(boolean isReserved);

}

