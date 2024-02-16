package simu.model;

import eduni.distributions.Normal;
import simu.framework.*;

import java.util.*;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
	private static Normal ageRandom = new Normal(40, 100);
	private static List<Asiakas> asiakkaat = new ArrayList<>();
	private static HashMap<Integer, Integer> ikaJakauma = new HashMap<>();
	private static long sum = 0;

	private static double totalMoneySpent = 0;
	private double jonotusaika;

	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;

	private int ika;

	private double spentMoney;
	private HashSet<TapahtumanTyyppi> ruokalista;
	private TapahtumanTyyppi ruokalistaEnumType;
	private TapahtumanTyyppi[] ruokalistaEnumValues = TapahtumanTyyppi.values();
	Random random = new Random();
	public Asiakas() {
		id = i++;
		// Ruokalista määrätään asiakkaalle
		ruokalista = new HashSet<>();
		// Luodaan random ruokalista tyypettäin asiakkaalle
		generateRandomEnums();
		ika = (int) (ageRandom.sample());
		spentMoney = 0;
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaika + " ja on " + ika + " vuotias.");
		Trace.out(Trace.Level.INFO, "Asiakkaan " + getId() + " Ruokalista: " + printRuokalista());
		updateIkaJakauma(ika);
		asiakkaat.add(this);
	}

	public static List<Asiakas> getAsiakkaat() {
		return asiakkaat;
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}
	public void lisaaJonotusaika(double aika) {
		jonotusaika += aika;
	}
	public double getJonotusaika() {
		return jonotusaika;
	}

	public static void addTotalMoneySpent(double amount) {
		totalMoneySpent += amount;
	}

	public void addSpentMoney(double amount) {
		spentMoney += amount;
	}

	public static double getTotalMoneySpent() {
		return totalMoneySpent;
	}

	public static double getAverageMoneySpent() {
		return totalMoneySpent / asiakkaat.size();
	}

	public void updateIkaJakauma(int ikä) {
		if (ikaJakauma.containsKey(ikä)) {
			ikaJakauma.put(ikä, ikaJakauma.get(ikä) + 1);
		} else {
			ikaJakauma.put(ikä, 1);
		}

	}

	public double getSpentMoney() {
		return spentMoney;
	}

	public static int getAverageAge() {
		int summa = 0;
		for (Map.Entry<Integer, Integer> entry : ikaJakauma.entrySet()) {
			summa += (entry.getKey() * entry.getValue());
		}
		return summa / asiakkaat.size();
	}

	public int getId() {
		return id;
	}

	public void raportti() {
		Trace.out(Trace.Level.INFO, "\nAsiakas " + id + " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + saapumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + poistumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (poistumisaika - saapumisaika));
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " kulutti: " + spentMoney);
	}

	public static void completeRaportti() {
		Trace.out(Trace.Level.INFO, "Asiakkaita yhteensä: " + asiakkaat.size());
		Trace.out(Trace.Level.INFO, "Asiakkaiden keskimääräinen rahankulutus " + getAverageMoneySpent() + " euroa.");
		Trace.out(Trace.Level.INFO, "Asiakkaiden keskimääräinen ikä: " + getAverageAge());
	}

	public HashSet<TapahtumanTyyppi> getRuokalista() {
		return ruokalista;
	}

	public  void generateRandomEnums()
	{
		// lisätään ARRMARKET pakolliseks ja ensimmäiseksi.
		ruokalista.add(TapahtumanTyyppi.ARRMARKET);
		ruokalista.add(TapahtumanTyyppi.CHECKOUTDEP);
		// asetetaan random määrä ruokatyyppejä asiakkaalle
		int randomRuokalistaSize = random.nextInt(3,TapahtumanTyyppi.values().length);

		System.out.println("Ruokalista koko: " + randomRuokalistaSize);

		// Random indexia käyttäen etsitään random enum asiakkaalle
		while (ruokalista.size() < randomRuokalistaSize) {
			// Random index luku jolla arvotaan enumi asiakkaalle
			int randomIndex = random.nextInt(ruokalistaEnumValues.length);
			TapahtumanTyyppi randomEnum = ruokalistaEnumValues[randomIndex];
			// lisätään random enumtyyppi ruokalistaan.
			if (!ruokalista.contains(randomEnum)) { // Tarkista onko olemassa
				ruokalista.add(randomEnum);
			}
		}
	}

	public String printRuokalista() {
		StringBuilder sb = new StringBuilder();
		for (TapahtumanTyyppi tyyppi : ruokalista) {
			try {
				sb.append(tyyppi.getRuokatuote());
				if (tyyppi != ruokalista.toArray()[ruokalista.size() - 1]) {
					sb.append(", ");
				}
			} catch (Exception e) {
			}
		}
		return sb.toString();
	}



}