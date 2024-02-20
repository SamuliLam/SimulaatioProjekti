package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simu.model.Asiakas;

public class Visualisointi extends Canvas implements IVisualisointi{

	private final GraphicsContext gc;
	private Asiakas asiakas;
	double i = 0;
	double j = 10;
	
	
	public Visualisointi(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		tyhjennaNaytto();
	}
	

	public void tyhjennaNaytto() {
		gc.setFill(Color.YELLOW);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void uusiAsiakas() {
		gc.setFill(Color.RED);
		gc.fillOval(i,j,10,10);
		
		i = (i + 10) % this.getWidth();
		//j = (j + 12) % this.getHeight();
		if (i==0) j+=10;			
	}

	@Override
	public void asiakasPoistuu() {

	}

	@Override
	public void updateMeatDepActivity(boolean isReserved) {

	}

	@Override
	public void updateBeerDepActivity(boolean isReserved) {

	}

	@Override
	public void updateFishDepActivity(boolean isReserved) {

	}

	@Override
	public void updateCandyDepActivity(boolean isReserved) {

	}

}
