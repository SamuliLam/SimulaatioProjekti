package simu.model;

import controller.IKontrolleriForM;
import dao.SimulationRunDAO;
import datasource.MariaDbConnection;
import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashSet;


public class OmaMoottori extends Moottori {

	private Saapumisprosessi arrivalProcess;

	private int amountOfCheckouts = 1;
	private Palvelupiste[] servicePoints;
	static SimulationRunDAO SimulationRunDAO = new SimulationRunDAO(MariaDbConnection.getConnection());

	private static int simulationRunNumber = SimulationRunDAO.getLastRunNumber() + 1;

	public OmaMoottori(IKontrolleriForM controller) {

		super(controller);

		// MEATDEP, BEERDEP, FISHDEP, CANDYDEP, CHECKOUTDEP;

		servicePoints = new Palvelupiste[8];

		servicePoints[0] = new Palvelupiste(new Normal(controller.getPalveluaikaMean(), controller.getPalveluaikaVarianssi()), eventList, TapahtumanTyyppi.MEATDEP);
		servicePoints[1] = new Palvelupiste(new Normal(controller.getPalveluaikaMean(), controller.getPalveluaikaVarianssi()), eventList, TapahtumanTyyppi.BEERDEP);
		servicePoints[2] = new Palvelupiste(new Normal(controller.getPalveluaikaMean(), controller.getPalveluaikaVarianssi()), eventList, TapahtumanTyyppi.FISHDEP);
		servicePoints[3] = new Palvelupiste(new Normal(controller.getPalveluaikaMean(), controller.getPalveluaikaVarianssi()), eventList, TapahtumanTyyppi.CANDYDEP);
		servicePoints[4] = new Palvelupiste(new Normal(controller.getPalveluaikaMean(), controller.getPalveluaikaVarianssi()), eventList, TapahtumanTyyppi.CHECKOUTDEP);
		servicePoints[5] = new Palvelupiste(new Normal(controller.getPalveluaikaMean(), controller.getPalveluaikaVarianssi()), eventList, TapahtumanTyyppi.CHECKOUTDEP2);
		servicePoints[6] = new Palvelupiste(new Normal(controller.getPalveluaikaMean(), controller.getPalveluaikaVarianssi()), eventList, TapahtumanTyyppi.CHECKOUTDEP3);
		servicePoints[7] = new Palvelupiste(new Normal(controller.getPalveluaikaMean(), controller.getPalveluaikaVarianssi()), eventList, TapahtumanTyyppi.CHECKOUTDEP4);

		arrivalProcess = new Saapumisprosessi(new Negexp(controller.getSaapumisValiaika(), 5), eventList, TapahtumanTyyppi.ARRMARKET);

		controller.updateMeatDepActivity(false);
		controller.updateBeerDepActivity(false);
		controller.updateFishDepActivity(false);
		controller.updateCandyDepActivity(false);
	}


	@Override
	protected void alustukset() {
		arrivalProcess.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) throws SQLException {  // B-vaiheen tapahtumat
		// MEATDEP, BEERDEP, FISHDEP, CANDYDEP, CHECKOUTDEP;
		Asiakas customer;
		amountOfCheckouts = controller.setKassaMaara();
		System.out.println("Kassojen määrä: " + amountOfCheckouts);
		int palvelupisteValitsin = 0;
		switch (t.getTyyppi()) {
			case ARRMARKET:
				customer = new Asiakas();
				removeEnumFromservicePointList(customer, TapahtumanTyyppi.ARRMARKET);
				palvelupisteValitsin = checkForEnumType(customer);
				servicePoints[palvelupisteValitsin].addToQue(customer);
				arrivalProcess.generoiSeuraava();
				controller.visualisoiAsiakas(); // UUSI
				break;
			case MEATDEP:
				customer = servicePoints[0].takeFromQue();
				removeEnumFromservicePointList(customer, TapahtumanTyyppi.MEATDEP);
				palvelupisteValitsin = checkForEnumType(customer);
				servicePoints[palvelupisteValitsin].addToQue(customer);
				break;
			case BEERDEP:
				customer = servicePoints[1].takeFromQue();
				removeEnumFromservicePointList(customer, TapahtumanTyyppi.BEERDEP);
				palvelupisteValitsin = checkForEnumType(customer);
				servicePoints[palvelupisteValitsin].addToQue(customer);
				break;
			case FISHDEP:
				customer = servicePoints[2].takeFromQue();
				removeEnumFromservicePointList(customer, TapahtumanTyyppi.FISHDEP);
				palvelupisteValitsin = checkForEnumType(customer);
				servicePoints[palvelupisteValitsin].addToQue(customer);
				break;
			case CANDYDEP:
				customer = servicePoints[3].takeFromQue();
				removeEnumFromservicePointList(customer, TapahtumanTyyppi.CANDYDEP);
				palvelupisteValitsin = checkForEnumType(customer);
				servicePoints[palvelupisteValitsin].addToQue(customer);
				break;
			case CHECKOUTDEP:
				customer = servicePoints[4].takeFromQue();
				removeEnumFromservicePointList(customer, TapahtumanTyyppi.CHECKOUTDEP);
				customer.addSpentMoneyAtCheckout(customer.getSpentMoney());
				Asiakas.addTotalSpentMoneyAtCheckout(customer.getSpentMoney());
				customer.addSoldProducts();
				customer.report();
				controller.asiakasPoistuu();
				break;
			case CHECKOUTDEP2:
				customer = servicePoints[5].takeFromQue();
				removeEnumFromservicePointList(customer, TapahtumanTyyppi.CHECKOUTDEP);
				customer.addSpentMoneyAtCheckout(customer.getSpentMoney());
				Asiakas.addTotalSpentMoneyAtCheckout(customer.getSpentMoney());
				customer.addSoldProducts();
				customer.setdepartureTime(Kello.getInstance().getAika());
				customer.report();
				controller.asiakasPoistuu();
				break;
			case CHECKOUTDEP3:
				customer = servicePoints[6].takeFromQue();
				removeEnumFromservicePointList(customer, TapahtumanTyyppi.CHECKOUTDEP);
				customer.addSpentMoneyAtCheckout(customer.getSpentMoney());
				Asiakas.addTotalSpentMoneyAtCheckout(customer.getSpentMoney());
				customer.addSoldProducts();
				customer.setdepartureTime(Kello.getInstance().getAika());
				customer.report();
				controller.asiakasPoistuu();
				break;
			case CHECKOUTDEP4:
				customer = servicePoints[7].takeFromQue();
				removeEnumFromservicePointList(customer, TapahtumanTyyppi.CHECKOUTDEP);
				customer.addSpentMoneyAtCheckout(customer.getSpentMoney());
				Asiakas.addTotalSpentMoneyAtCheckout(customer.getSpentMoney());
				customer.addSoldProducts();
				customer.setdepartureTime(Kello.getInstance().getAika());
				customer.report();
				controller.asiakasPoistuu();
				break;
		}
	}
	private int checkForEnumType(Asiakas asiakas) {
		try {
			HashSet<TapahtumanTyyppi> servicePointList = asiakas.getservicePointList();
			int palvelupisteValitsija = 0;
			if (servicePointList.contains(TapahtumanTyyppi.MEATDEP)) {
				palvelupisteValitsija = 0;
			} else if (servicePointList.contains(TapahtumanTyyppi.BEERDEP)) {
				palvelupisteValitsija = 1;
			} else if (servicePointList.contains(TapahtumanTyyppi.FISHDEP)) {
				palvelupisteValitsija = 2;
			} else if (servicePointList.contains(TapahtumanTyyppi.CANDYDEP)) {
				palvelupisteValitsija = 3;
			}
			else
			{
				switch (amountOfCheckouts) {
					case 1:
						palvelupisteValitsija = 4;
						break;
					case 2:
						if (servicePoints[4].getQueSize() < 4) {
							palvelupisteValitsija = 4;
						} else if (servicePoints[5].getQueSize() < 6) {
							palvelupisteValitsija = 5;
						}
						break;
					case 3:
						if (servicePoints[4].getQueSize() < 3) {
							palvelupisteValitsija = 4;
						} else if (servicePoints[5].getQueSize() < 4) {
							palvelupisteValitsija = 5;
						}
						else if (servicePoints[6].getQueSize() < 6) {
							palvelupisteValitsija = 6;
						}
						break;
					case 4:
						if (servicePoints[4].getQueSize() < 2) {
							palvelupisteValitsija = 4;
						} else if (servicePoints[5].getQueSize() < 3) {
							palvelupisteValitsija = 5;
						}
						else if (servicePoints[6].getQueSize() < 4) {
							palvelupisteValitsija = 6;
						} else if (servicePoints[7].getQueSize() < 6) {
							palvelupisteValitsija = 7;
						}
						break;
					default:
						palvelupisteValitsija = 4;
						break;
				}
			}
			System.out.println("Current palvelupiste: " + palvelupisteValitsija);
			return palvelupisteValitsija;
		} catch (NullPointerException e) {
			// Virheen näyttääminen
			System.out.println("Error: ruokalista is null.");
			e.printStackTrace();
			return -1; // palauttaa
		}
	}
	private void removeEnumFromservicePointList(Asiakas asiakas, TapahtumanTyyppi servedType) {
		TapahtumanTyyppi arrmarket = TapahtumanTyyppi.ARRMARKET;
		asiakas.getservicePointList().remove(arrmarket);
		asiakas.getservicePointList().remove(servedType);
		Trace.out(Trace.Level.INFO,"Asiakkaan " + asiakas.getId() + " servicePointListsta poistettu: " + servedType);
	}

	@Override
	protected void yritaCTapahtumat() {
		controller.updateMeatDepActivity(servicePoints[0].isReserved());
		controller.updateFishDepActivity(servicePoints[2].isReserved());
		controller.updateCandyDepActivity(servicePoints[3].isReserved());
		for (Palvelupiste p : servicePoints) {
			if (!p.isReserved() && p.inQue()) {
				p.startService();
			}
		}
	}

	@Override
	protected void tulokset() {
		StringBuilder tulokset = new StringBuilder();
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		//double formattedAika = Double.parseDouble(decimalFormat.format(Kello.getInstance().getAika()));
		tulokset.append("Simulointi päättyi kello ").append(Kello.getInstance().getAika()).append("\n");
		for (Palvelupiste p : servicePoints) {
			tulokset.append(p.report()).append("\n");
		}
		tulokset.append(Asiakas.completeReport()).append("\n");

		// UUTTA graafista
		controller.naytaLoppuaika(Kello.getInstance().getAika());

		controller.naytaTulokset(tulokset.toString());

		// Lisätään simulointiajon numero
		SimulationRunDAO.addNewRunNumber();
	}

	public void setKassojenMaara(int kassaMaara)
	{
		this.amountOfCheckouts = kassaMaara;
	}

	public static int getSimulationRunNumber() {
		return simulationRunNumber;
	}
}

