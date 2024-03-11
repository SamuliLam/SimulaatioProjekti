package simu.model;

import java.text.DecimalFormat;
import java.util.*;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;
import simu.model.Tuotehallinta.GroceryCategory;

import java.util.ArrayList;
import java.util.LinkedList;

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

    public void addToQue(Asiakas a) {   // Jonon 1. asiakas aina palvelussa
        que.add(a);

    }

    public Asiakas takeFromQue() {  // Poistetaan palvelussa ollut
        reserved = false;
        return que.poll();
    }

    public void startService() {
        //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

        Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu " + eventTypeToBeScheduled.getPalvelupiste() + " asiakkaalle " + que.peek().getId());

        reserved = true;
        double serviceTime = generator.sample();

        // Simppeli hinnoittelu, joka perustuu palveluajan pituuteen ja se kerrotaan hintaPerAjalla. Myöhemmin kertoimen voi muuttaa tuotehintoihin perustuvaksi.

        // Haetaan asiakas jonosta ja tallennetaan se muuttujaan
        Asiakas customer = que.peek();

        if (customer != null){
            for (GroceryCategory category : customer.getGroceryList()){
                if (category.getCategory() == eventTypeToBeScheduled){
                    customer.addSpentMoney(category.getTotalItemPrice());
                }
            }
        }

		serviceTimes.add(serviceTime);
		servicePointVisits.put(eventTypeToBeScheduled.getPalvelupiste(), serviceTimes.size());
		eventList.lisaa(new Tapahtuma(eventTypeToBeScheduled, Kello.getInstance().getAika() + serviceTime));
	}

    public String report() {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");

        StringBuilder sb = new StringBuilder();
        double sum = 0;
        for (double d : serviceTimes) {
            sum += d;
        }
        calculateTotalTimePerPalvelupiste();
        double average = sum / serviceTimes.size();
        String formattedAverage = decimalFormat.format(average);

        Trace.out(Trace.Level.INFO, "Palvelupisteessä " + eventTypeToBeScheduled.getPalvelupiste() + " palveltiin " + serviceTimes.size() + " asiakasta");
        Trace.out(Trace.Level.INFO, "Palvelupisteessä " + eventTypeToBeScheduled.getPalvelupiste() + " palveluaikojen keskiarvo oli " + formattedAverage);


        sb.append("Palvelupisteessä ")
                .append(eventTypeToBeScheduled.getPalvelupiste())
                .append(" palveltiin ")
                .append(serviceTimes.size())
                .append(" asiakasta\n");

        sb.append("Palvelupisteessä ")
                .append(eventTypeToBeScheduled.getPalvelupiste())
                .append(" palveluaikojen keskiarvo oli ")
                .append(formattedAverage)
                .append("\n");

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

	public static HashMap<String, Integer> getPalveluLuku()
	{
		return servicePointVisits;
	}

    public static HashMap<String, Double> getServiceTimesPerServicePoint() { return serviceTimesPerServicePoint;}

}