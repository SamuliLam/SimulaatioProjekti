package simu.model;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;
import simu.model.Tuotehallinta.GroceryCategory;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a service point in the simulation model.
 * It manages a queue of customers, tracks service times, and generates customerReports.
 */
public class Palvelupiste {
    /**
     * A queue of customers waiting for service at this service point.
     */
    private final LinkedList<Asiakas> que = new LinkedList<>();
    /**
     * The event generator for this service point.
     */
    private final ContinuousGenerator generator;
    /**
     * The event list for this service point.
     */
    private final Tapahtumalista eventList;
    /**
     * The type of event to be scheduled at this service point.
     */
    private final TapahtumanTyyppi eventTypeToBeScheduled;
    /**
     * A map of service point visits.
     */
    private static final HashMap<String, Integer> servicePointVisits = new HashMap<>();
    /**
     * A list of service times.
     */
    private ArrayList<Double> serviceTimes = new ArrayList<>();
    /**
     * The total time serviced.
     */
    private static List<Palvelupiste> palvelupisteet = new ArrayList<>();
    /**
     * A map of service times per service point.
     */
    private double totalTimeServiced = 0.0;
    /**
     * A flag indicating whether the service point is reserved.
     */
    private static final HashMap<String, Double> serviceTimesPerServicePoint = new HashMap<>();
    /**
     * A flag indicating whether the service point is reserved.
     */
    private boolean reserved = false;
    /**
     * Constructs a new service point with the given event generator and event list.
     *
     * @param generator the event generator for this service point
     * @param eventList the event list for this service point
     * @param tyyppi the type of event to be scheduled at this service point
     */
    public Palvelupiste(ContinuousGenerator generator, Tapahtumalista eventList, TapahtumanTyyppi tyyppi) {
        this.eventList = eventList;
        this.generator = generator;
        this.eventTypeToBeScheduled = tyyppi;
        palvelupisteet.add(this);
    }
    /**
     * Adds a customer to the queue at this service point.
     *
     * @param a the customer to add
     */
    public void addToQue(Asiakas a) {
        que.add(a);
    }
    /**
     * Removes the customer that was in service from the queue.
     *
     * @return the customer that was in service
     */
    public Asiakas takeFromQue() {  // Poistetaan palvelussa ollut
        reserved = false;
        return que.poll();
    }
    /**
     * Starts a new service for the customer at the front of the queue.
     */
    public void startService() {
        Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu " + eventTypeToBeScheduled.getPalvelupiste() + " asiakkaalle " + que.peek().getId());
        reserved = true;
        double serviceTime = generator.sample();
        Asiakas customer = que.peek();
        if (customer != null) {
            for (GroceryCategory category : customer.getGroceryList()) {
                if (category.getCategory() == eventTypeToBeScheduled) {
                    customer.addSpentMoney(category.getTotalItemPrice());
                }
            }
        }
        serviceTimes.add(serviceTime);
        servicePointVisits.put(eventTypeToBeScheduled.getPalvelupiste(), serviceTimes.size());
        double tapahtumaAika = Kello.getInstance().getAika() + serviceTime;
        eventList.lisaa(new Tapahtuma(eventTypeToBeScheduled, tapahtumaAika));
    }
    /**
     * Generates a customerReport of the service times at this service point.
     *
     * @return a string containing the customerReport
     */
    public String customerReport() {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        StringBuilder sb = new StringBuilder();
        double sum = 0;
        for (double d : serviceTimes) {
            sum += d;
        }
        calculateTotalTimePerPalvelupiste();
        String formattedAverage = decimalFormat.format(getAverageServiceTime());
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
        return sb.toString();
    }
    /**
     * Calculates the average service time.
     * @return the average service time
     */
    public double getAverageServiceTime() {
        double sum = 0;
        for (double d : serviceTimes) {
            sum += d;
        }
        return !serviceTimes.isEmpty() ? sum / serviceTimes.size() : 0;
    }
    /**
     * Calculates the total time per service point.
     */
    public void calculateTotalTimePerPalvelupiste() {
        for (int i = 0; i < serviceTimes.size(); i++) {
            totalTimeServiced += serviceTimes.get(i);
        }
        serviceTimesPerServicePoint.put(eventTypeToBeScheduled.getPalvelupiste(), totalTimeServiced);
    }
    /**
     * Checks if the service point is reserved.
     *
     * @return true if the service point is reserved, false otherwise
     */
    public boolean isReserved() {
        return reserved;
    }
    /**
     * Checks if there are any customers in the queue.
     *
     * @return true if there are customers in the queue, false otherwise
     */
    public boolean inQue() {
        return !que.isEmpty();
    }
    /**
     * Gets the size of the queue.
     *
     * @return the size of the queue
     */
    public int getQueSize() {
        return que.size();
    }
    /**
     * Gets the service point visits.
     *
     * @return a map of service point visits
     */
    public static HashMap<String, Integer> getServicePointVisits() {
        return servicePointVisits;
    }
    /**
     * Gets the service times per service point.
     *
     * @return a map of service times per service point
     */
    public static HashMap<String, Double> getServiceTimesPerServicePoint() {
        return serviceTimesPerServicePoint;
    }

    /**
     * Gets the event type to be scheduled at this service point.
     *
     * @return the event type to be scheduled
     */
    public TapahtumanTyyppi getEventTypeToBeScheduled() {
        return eventTypeToBeScheduled;
    }

    /**
     * Gets the number of service times.
     *
     * @return the number of service times
     */
    public Integer getServiceTimeSize() {
        return serviceTimes.size();
    }

    /**
     * Returns the list of all service points.
     *
     * @return the list of service points
     */
    public static List<Palvelupiste> getPalvelupisteet() {
        return palvelupisteet;
    }
}