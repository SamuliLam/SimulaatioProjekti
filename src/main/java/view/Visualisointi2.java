package view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Visualisointi2 implements IVisualisointi{
	private GraphicsContext gc;
	private Canvas cnv;
	int asiakasLkm = 0;
	int palvellutAsiakkaat = 0;
	boolean meatDepQueue;
	public Visualisointi2(Canvas canvas) {
		this.cnv = canvas;
		gc = cnv.getGraphicsContext2D();
		tyhjennaNaytto();

		cnv.widthProperty().addListener(evt -> tyhjennaNaytto());
		cnv.heightProperty().addListener(evt -> tyhjennaNaytto());
	}

	public void tyhjennaNaytto() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, cnv.getWidth(), cnv.getHeight());
	}

	public void uusiAsiakas() {
		asiakasLkm++;
		gc.setFill(Color.WHITE);
		gc.fillRect(220, 30, 50, 30);
		gc.setFill(Color.BLACK);
		gc.setFont(new Font(20));
		gc.fillText(asiakasLkm + "", 230, 50);
		gc.fillText("Asiakkaiden määrä: " , 50, 50);
		gc.fillText("Lihatiskin aktiivisuus: ", 50, 75);
		gc.fillText("Olutosaston aktiivisuus: ", 50, 100);
		gc.fillText("Kalatiskin aktiivisuus: ", 50, 125);
		gc.fillText("Karkkihyllyn aktiivisuus: ", 50, 150);
		gc.fillText("Palvellut asiakkaat: ", 50, 175);
	}

	public void asiakasPoistuu() {
		palvellutAsiakkaat++;
		gc.setFill(Color.WHITE);
		gc.fillRect(220, 155, 50, 30);
		gc.setFill(Color.BLACK);
		gc.setFont(new Font(20));
		gc.fillText(palvellutAsiakkaat + "", 230, 175);
	}
	public void updateMeatDepActivity(boolean meatDepQueue) {
		if (meatDepQueue) {
			gc.setFill(Color.GREEN);
			gc.fillOval(265, 57, 20, 20);
		} else {
			gc.setFill(Color.RED);
			gc.fillOval(265, 57, 20, 20); ;
		}
	}

	public void updateBeerDepActivity(boolean beerDepQueue) {
		if (beerDepQueue) {
			gc.setFill(Color.GREEN);
			gc.fillOval(265, 82, 20, 20);
		} else {
			gc.setFill(Color.RED);
			gc.fillOval(265, 82, 20, 20); ;
		}
	}

	public void updateFishDepActivity(boolean fishDepQueue) {
		if (fishDepQueue) {
			gc.setFill(Color.GREEN);
			gc.fillOval(265, 107, 20, 20);
		} else {
			gc.setFill(Color.RED);
			gc.fillOval(265, 107, 20, 20); ;
		}
	}
	public void updateCandyDepActivity(boolean candyDepQueue) {
		if (candyDepQueue) {
			gc.setFill(Color.GREEN);
			gc.fillOval(265, 132, 20, 20);
		} else {
			gc.setFill(Color.RED);
			gc.fillOval(265, 132, 20, 20); ;
		}
	}
}