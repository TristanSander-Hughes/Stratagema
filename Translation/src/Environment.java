import java.util.ArrayList;
import java.util.HashMap;

public class Environment {
	
	private int MAX_TEMPERATURE=100;
	private int MIN_TEMPERATURE=0;
	private int temperature;
	private int MAX_PH = 14;
	private int MIN_PH = 1;
	private int ph ;
	private boolean radiationPresent = false;
	private int glucose;
	private Population populace;
	private ArrayList<String> migrantTypes;
	private ArrayList<Integer> migrantFrequencies;
	private Player player;
	
	
	Environment(Player newPlayer)
	{
		player=newPlayer;
		ph = 7;
		glucose = 0;
		temperature = 25;
		populace = new Population(500,2,50,50);
		populace.detectGenotypes();
		Double[] newFitnesses;
		ArrayList<String> genotypes = populace.getGenotypes();
		int size = genotypes.size();
		newFitnesses = new Double[size];
		for (int i = 0; i < size; i++)
		{
			newFitnesses[i] = calculateFitnesses(genotypes.get(i));
		}
		populace.setFitnesses(newFitnesses);
		migrantTypes=new ArrayList<String>();
		migrantFrequencies=new ArrayList<Integer>();
		//add initialization of timekeeper when implemented
		//add initialization for playerInstance
	}
	
	int getTemperature()
	{
		return temperature;
	}
	int getPh()
	{
		return ph;
	}
	int getGlucose()
	{
		return glucose;
	}
	int getPopulationSize()
	{
		return populace.getPopulationSize();
	}
	boolean getRadiation()
	{
		return radiationPresent;
	}
	void setTemperature(int newTemp)
	{
		if (newTemp > MIN_TEMPERATURE)
		{
			if (newTemp < MAX_TEMPERATURE)
			{
				temperature = newTemp;
			}
			else
			{
				temperature = MAX_TEMPERATURE;
			}
		}
		else
		{
			temperature = MIN_TEMPERATURE;
		}
	}
	void setPh(int newPh)
	{
		if (newPh > MIN_PH)
		{
			if (newPh < MAX_PH)
			{
				ph = newPh;
			}
			else
			{
				temperature = MAX_PH;
			}
		}
		else
		{
			temperature = MIN_PH;
		}

	}
	void setGlucose(int newGlucose)
	{
		glucose = newGlucose;
	}
	void setRadiation(boolean newRad)
	{
		radiationPresent = newRad;
	}
	void triggerEndState()//implement once playerInstance is complete
	{
		player.endGame(false,null);
	}
	void reproductionEvent()
	{
		
		glucose = populace.detectProducers();
		populace.consume(glucose);
		populace.aggress();
		populace.reproduce();
		populace.integrateMigrants(migrantTypes,migrantFrequencies);
		if(populace.checkEndState())
		{
			triggerEndState();
			return;
		}
		migrantTypes.clear();
		migrantFrequencies.clear();
		populace.detectGenotypes();
		Double[] newFitnesses;
		ArrayList<String> genotypes = populace.getGenotypes();
		int size = genotypes.size();
		newFitnesses = new Double[size];
		for (int i = 0; i < size; i++)
		{
			newFitnesses[i] = calculateFitnesses(genotypes.get(i));
		}
		populace.setFitnesses(newFitnesses);
		
		
	}
	double calculateFitnesses(String genotype)
	{
		double fitness = 7.0;
		if (genotype.charAt(0)=='1')
		{
			fitness = fitness + 1;
		}
		if (genotype.charAt(1) == '1'&& temperature >= 45)
		{
			fitness = fitness + 1;
		}
		if (genotype.charAt(2) == '1'&&temperature<=15)
		{
			fitness = fitness + 1;
		}
		if (genotype.charAt(6) == '1'&&radiationPresent)
		{
			fitness = fitness + 1;
		}
		else if (radiationPresent)
		{
			fitness = fitness - 1;
		}
		if (genotype.charAt(9) == '1')
		{
			if (populace.getProducersPresent())
			{
				fitness = fitness + 1;
			}
			else
			{
				fitness = fitness - 1;
			}
		}/*
		if (genotype.at() == '')
		{

		}
		if (genotype.at() == '')
		{

		}*/

		//test this
		return fitness/14;
	}
	ArrayList<Double> getFitnesses()
	{
		return populace.getFitnesses();
	}
	ArrayList<Integer> getFrequencies()
	{
		return populace.getFrequencies();
	}
	ArrayList<String> getGenotypes()
	{
		return populace.getGenotypes();
	}
	void tradeAwayPops(int numberOfPops)
	{
		populace.tradeAwayPops(numberOfPops);
		if(populace.checkEndState())
		{
			triggerEndState();
		}
	}
	void receiveMigration(ArrayList<String> types, ArrayList<Integer> frequencies)
	{
		for(int i=0; i<types.size(); i++)
		{
			if(migrantTypes.contains(types.get(i)))
			{
				migrantFrequencies.set(migrantTypes.indexOf(types.get(i)),migrantFrequencies.get(migrantTypes.indexOf(types.get(i)))+frequencies.get(i));
			}else
			{
				migrantTypes.add(types.get(i));
				migrantFrequencies.add(frequencies.get(i));
			}
		}
	}
	HashMap<String,Integer> getMigraters()
	{
		return populace.getMigraters();
	}
	

}
