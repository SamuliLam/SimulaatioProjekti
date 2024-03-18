package simu.model;

import controller.IKontrolleriForM;
import dao.AsiakasDAO;
import dao.AsiakasOstoslistaDAO;
import dao.PalvelupisteDAO;
import dao.SimulationRunDAO;
import datasource.MariaDbConnection;
import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashSet;

/**
 * This class represents the main engine of the simulation model.
 * It manages the arrival process, service points, and executes the simulation events.
 */
public class OmaMoottori extends Moottori {
    /**
     * The arrival process of the simulation.
     */
    private Saapumisprosessi arrivalProcess;
    /**
     * The amount of checkouts in the simulation.
     */
    private int amountOfCheckouts = 1;
    /**
     * The service points in the simulation.
     */
    private Palvelupiste[] servicePoints;
    private static SimulationRunDAO SimulationRunDAO = new SimulationRunDAO(MariaDbConnection.getConnection());
    private static int simulationRunNumber = SimulationRunDAO.getLastRunNumber() + 1;

    /**
     * Constructs a new simulation engine with the given controller.
     *
     * @param controller the controller interface for this simulation engine
     */
    public OmaMoottori(IKontrolleriForM controller) {
        super(controller);
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

    /**
     * Initializes the first arrival process.
     */
    @Override
    protected void alustukset() {
        arrivalProcess.generoiSeuraava();
    }

    /**
     * Executes the given event in the simulation.
     *
     * @param t the event to execute
     */
    @Override
    protected void suoritaTapahtuma(Tapahtuma t) throws SQLException {
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
                controller.visualisoiAsiakas();
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
                customer.setDepartureTime(Kello.getInstance().getAika());
                customer.customerReport();
                controller.asiakasPoistuu();
                break;
            case CHECKOUTDEP2:
                customer = servicePoints[5].takeFromQue();
                removeEnumFromservicePointList(customer, TapahtumanTyyppi.CHECKOUTDEP);
                customer.addSpentMoneyAtCheckout(customer.getSpentMoney());
                Asiakas.addTotalSpentMoneyAtCheckout(customer.getSpentMoney());
                customer.addSoldProducts();
                customer.setDepartureTime(Kello.getInstance().getAika());
                customer.customerReport();
                controller.asiakasPoistuu();
                break;
            case CHECKOUTDEP3:
                customer = servicePoints[6].takeFromQue();
                removeEnumFromservicePointList(customer, TapahtumanTyyppi.CHECKOUTDEP);
                customer.addSpentMoneyAtCheckout(customer.getSpentMoney());
                Asiakas.addTotalSpentMoneyAtCheckout(customer.getSpentMoney());
                customer.addSoldProducts();
                customer.setDepartureTime(Kello.getInstance().getAika());
                customer.customerReport();
                controller.asiakasPoistuu();
                break;
            case CHECKOUTDEP4:
                customer = servicePoints[7].takeFromQue();
                removeEnumFromservicePointList(customer, TapahtumanTyyppi.CHECKOUTDEP);
                customer.addSpentMoneyAtCheckout(customer.getSpentMoney());
                Asiakas.addTotalSpentMoneyAtCheckout(customer.getSpentMoney());
                customer.addSoldProducts();
                customer.setDepartureTime(Kello.getInstance().getAika());
                customer.customerReport();
                controller.asiakasPoistuu();
                break;
        }
    }

    /**
     * Checks for the type of service point to be selected for the given customer.
     *
     * @param asiakas the customer to check
     * @return the index of the service point to select
     */
    private int checkForEnumType(Asiakas asiakas) {
        HashSet<TapahtumanTyyppi> servicePointList = asiakas.getServicePointList();
        if (servicePointList.contains(TapahtumanTyyppi.MEATDEP)) {
            return 0;
        } else if (servicePointList.contains(TapahtumanTyyppi.BEERDEP)) {
            return 1;
        } else if (servicePointList.contains(TapahtumanTyyppi.FISHDEP)) {
            return 2;
        } else if (servicePointList.contains(TapahtumanTyyppi.CANDYDEP)) {
            return 3;
        } else {
            return selectCheckoutPoint();
        }
    }

    /**
     * Selects the checkout point for the customer.
     *
     * @return the index of the checkout point to select
     */
    private int selectCheckoutPoint() {
        switch (amountOfCheckouts) {
            case 1:
                return 4;
            case 2:
                return servicePoints[4].getQueSize() < 4 ? 4 : 5;
            case 3:
                if (servicePoints[4].getQueSize() < 3) {
                    return 4;
                } else if (servicePoints[5].getQueSize() < 4) {
                    return 5;
                } else {
                    return 6;
                }
            case 4:
                if (servicePoints[4].getQueSize() < 2) {
                    return 4;
                } else if (servicePoints[5].getQueSize() < 3) {
                    return 5;
                } else if (servicePoints[6].getQueSize() < 4) {
                    return 6;
                } else {
                    return 7;
                }
            default:
                return 4;
        }
    }

    /**
     * Removes the given enum from the service point list of the given customer.
     *
     * @param asiakas    the customer to remove the enum from
     * @param servedType the enum to remove
     */
    private void removeEnumFromservicePointList(Asiakas asiakas, TapahtumanTyyppi servedType) {
        TapahtumanTyyppi arrmarket = TapahtumanTyyppi.ARRMARKET;
        asiakas.getServicePointList().remove(arrmarket);
        asiakas.getServicePointList().remove(servedType);
        Trace.out(Trace.Level.INFO, "Asiakkaan " + asiakas.getId() + " servicePointListsta poistettu: " + servedType);
    }

    /**
     * Tries to execute the next event in the simulation.
     */
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

    /**
     * Updates the simulation statistics.
     */
    @Override
    protected void tulokset() {
        StringBuilder tulokset = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        double formattedAika = Double.parseDouble(decimalFormat.format(Kello.getInstance().getAika()));
        tulokset.append("Simulointi päättyi kello ").append(Kello.getInstance().getAika()).append("\n");
        for (Palvelupiste p : servicePoints) {
            tulokset.append(p.customerReport()).append("\n");
        }
        tulokset.append(Asiakas.customersReport()).append("\n");

        controller.naytaLoppuaika(formattedAika);

        controller.naytaTulokset(tulokset.toString());

        // Lisätään simulointiajon numero
        SimulationRunDAO.addNewRunNumber();
        persistSimulationData();
    }

    /**
     * Persists the simulation data to the database.
     */
    public void persistSimulationData() {
        try {
            AsiakasDAO asiakasDao = new AsiakasDAO(MariaDbConnection.getConnection());
            asiakasDao.saveAllAsiakas(Asiakas.getCustomers(), getSimulationRunNumber());
            asiakasDao.updateSpentMoney(Asiakas.getCustomers(), getSimulationRunNumber());
            asiakasDao.updatePoistumisaika(Asiakas.getCustomers(), getSimulationRunNumber());

            AsiakasOstoslistaDAO ostoslistaDao = new AsiakasOstoslistaDAO(MariaDbConnection.getConnection());
            ostoslistaDao.saveAllSoldProducts(Asiakas.getSoldProducts(), getSimulationRunNumber());

            PalvelupisteDAO palvelupisteDAO = new PalvelupisteDAO(MariaDbConnection.getConnection());
            palvelupisteDAO.saveAllPalvelupisteData(Palvelupiste.getPalvelupisteet(), getSimulationRunNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Gets the simulation run number.
     *
     * @return the simulation run number
     */
    public static int getSimulationRunNumber() {
        return simulationRunNumber;
    }
}

