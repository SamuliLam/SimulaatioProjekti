package view;


import javafx.beans.property.Property;

public interface IVisualisointi {

	void tyhjennaNaytto();
	
	void uusiAsiakas();
	void asiakasPoistuu();
	void updateMeatDepActivity(boolean isReserved);

	void updateBeerDepActivity(boolean isReserved);

	void updateFishDepActivity(boolean isReserved);

	void updateCandyDepActivity(boolean isReserved);


}

