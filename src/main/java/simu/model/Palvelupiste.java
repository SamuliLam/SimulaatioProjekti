package simu.model;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

import dao.AsiakasDAO;
import dao.PalvelupisteDAO;
import datasource.MariaDbConnection;
import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;
import simu.model.Tuotehallinta.GroceryCategory;

import java.util.ArrayList;
import java.util.LinkedList;

import static dao.AsiakasDAO.updateSpentMoney;

public class Palvelupiste {

	private final LinkedList<Asiakas> que = new LinkedList<>(); // Tietorakennetoteutus
	private final ContinuousGenerator generator;
	private final Tapahtumalista eventList;
	private final TapahtumanTyyppi eventTypeToBeScheduled;
	private static final HashMap<String, Integer> servicePointVisits = new HashMap<>();
	private ArrayList<Double> serviceTimes = new ArrayList<>();

    private double totalTimeServiced = 0.0;
    private static final HashMap<String, Double> serviceTimesPerServicePoint = new HashMap<>();

    //JonoStartegia strategia; //optio: asiakkaiden järjestys

    private boolean reserved = false;


    public Palvelupiste(ContinuousGenerator generator, Tapahtumalista eventList, TapahtumanTyyppi tyyppi) {
        this.eventList = eventList;
        this.generator = generator;
        this.eventTypeToBeScheduled = tyyppi;

    }

    public void addToQue(Asiakas a) {
        que.add(a);
    }


    public Asiakas takeFromQue() {  // Poistetaan palvelussa ollut
        reserved = false;
        return que.poll();
    }

    public void startService() {
        // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
        Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu " + eventTypeToBeScheduled.getPalvelupiste() + " asiakkaalle " + que.peek().getId());

        reserved = true;
        double serviceTime = generator.sample();

        // Simppeli hinnoittelu, joka perustuu palveluajan pituuteen ja se kerrotaan hintaPerAjalla. Myöhemmin kertoimen voi muuttaa tuotehintoihin perustuvaksi.

        // Haetaan asiakas jonosta ja tallennetaan se muuttujaan
        Asiakas customer = que.peek();

        if (customer != null) {
            for (GroceryCategory category : customer.getGroceryList()) {
                if (category.getCategory() == eventTypeToBeScheduled) {
                    customer.addSpentMoney(category.getTotalItemPrice());
                    try { // Tämä on vain testiä varten, jotta saadaan asiakkaan rahankäyttö tallennettua tietokantaan
                        updateSpentMoney(customer.getId(), category.getTotalItemPrice(), OmaMoottori.getSimulationRunNumber());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

		serviceTimes.add(serviceTime);
		servicePointVisits.put(eventTypeToBeScheduled.getPalvelupiste(), serviceTimes.size());
        double tapahtumaAika = Kello.getInstance().getAika() + serviceTime;

        eventList.lisaa(new Tapahtuma(eventTypeToBeScheduled, tapahtumaAika));
	}

    public String report() {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");

        StringBuilder sb = new StringBuilder();
        double sum = 0;
        for (double d : serviceTimes) {
            sum += d;
        }
        calculateTotalTimePerPalvelupiste();
        double average = !serviceTimes.isEmpty() ? sum / serviceTimes.size() : 0;
        String formattedAverage = decimalFormat.format(average);

        Trace.out(Trace.Level.INFO, "Palvelupisteessä " + eventTypeToBeScheduled.getPalvelupiste() + " palveltiin " + serviceTimes.size() + " asiakasta");
        Trace.out(Trace.Level.INFO, "Palvelupisteessä " + eventTypeToBeScheduled.getPalvelupiste() + " palveluaikojen keskiarvo oli " + formattedAverage);


        sb.append("Palvelupisteessä ")
                .append(eventTypeToBeScheduled.getPalvelupiste())
                .append(" palveltiin ")
                .append(serviceTimes == null ? "0" : serviceTimes.size())
                .append(" asiakasta\n");

        sb.append("Palvelupisteessä ")
                .append(eventTypeToBeScheduled.getPalvelupiste())
                .append(" palveluaikojen keskiarvo oli ")
                .append(formattedAverage)
                .append("\n");
try {
            PalvelupisteDAO palvelupisteDAO = new PalvelupisteDAO(MariaDbConnection.getConnection());
            palvelupisteDAO.savePalvelupisteData(eventTypeToBeScheduled.getPalvelupiste(), serviceTimes.size(), average, OmaMoottori.getSimulationRunNumber());
        } catch (SQLException e) {
            e.printStackTrace(); // SQL virheen käsittely
        }
        return sb.toString();


    }


    public void calculateTotalTimePerPalvelupiste()
    {
        for (int i = 0; i < serviceTimes.size(); i++) {
            totalTimeServiced += serviceTimes.get(i);
        }
        serviceTimesPerServicePoint.put(eventTypeToBeScheduled.getPalvelupiste(), totalTimeServiced);
    }
    public boolean isReserved() {
        return reserved;
    }


    public boolean inQue() {
        return !que.isEmpty();
    }

    public int getQueSize() {
        return que.size();
    }

	public static HashMap<String, Integer> getServicePointVisits()
	{
		return servicePointVisits;
	}

    public static HashMap<String, Double> getServiceTimesPerServicePoint() { return serviceTimesPerServicePoint;}

}