import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

public class Player {
	private Environment environmentInstance;
	private NetworkInterface netInterface;
	private InboundInterface inInterface;
	private UserInterface useInterface;
	private int currentStoredTemperature;
	private int currentStoredPh;
	private boolean currentStoredRadiationPresent;
	private int currentStoredGlucose;
	private ArrayList<Double> currentStoredGenotypeFitnesses;
	private ArrayList<Integer> currentStoredGenotypeFrequencies;
	private ArrayList<String> currentStoredGenotypes;
	private String ID;
	private int tempIncreases;
	private int tempDecreases;
	private int pHIncreases;
	private int pHDecreases;
	private int radiationAlters;
	private int PORT;
	private int generations;
	//private FalseBackend backend;
	private boolean gameOver=false;
	
	Player()
	{
		generations=0;
		environmentInstance = new Environment(this);
		Random rand=new Random();
		PORT=rand.nextInt(500)+1;
		PORT=PORT+4000;
		try {
		netInterface=new NetworkInterface();
		inInterface=new InboundInterface();
		netInterface.setPlayer(this);
		inInterface.setPlayer(this);
		inInterface.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		updateStoredData();
		generatePlayerID();
		//netInterface.initialize();
		tempIncreases=0;
		tempDecreases=0;
		pHIncreases=0;
		pHDecreases=0;
		radiationAlters=0;
		
	}
	void initialize()
	{
		netInterface.initialize();
	}
	void beginGame()
	{
		if(gameOver==true)
			return;
		useInterface.updateData();
		useInterface.enableAll();
	}
	
	//void setInterface(NetworkInterface* newInterface)
	//{
		//netInterface = newInterface;
	//}
	void updateStoredData()
	{
		currentStoredTemperature = environmentInstance.getTemperature();
		currentStoredPh = environmentInstance.getPh();
		currentStoredRadiationPresent = environmentInstance.getRadiation();
		currentStoredGlucose = environmentInstance.getGlucose();
		currentStoredGenotypes = environmentInstance.getGenotypes();
		currentStoredGenotypeFitnesses = environmentInstance.getFitnesses();
		currentStoredGenotypeFrequencies = environmentInstance.getFrequencies();
	}
	int getTemperature()
	{
		return currentStoredTemperature;
	}
	int getPh()
	{
		return currentStoredPh;
	}
	boolean getRadiation()
	{
		return currentStoredRadiationPresent;
	}
	int getGlucose()
	{
		return currentStoredGlucose;
	}
	ArrayList<Double> getFitnesses()
	{
		ArrayList<Double> temp = currentStoredGenotypeFitnesses;
		return temp;
	}
	ArrayList<Integer> getFrequencies()
	{
		ArrayList<Integer> temp = currentStoredGenotypeFrequencies;
		return temp;
	}
	ArrayList<String> getGenotypes()
	{
		ArrayList<String> temp = currentStoredGenotypes;
		return temp;
	}
	int getPopulationSize()
	{
		return environmentInstance.getPopulationSize();
	}
	void raiseTemperature()
	{
		environmentInstance.setTemperature(currentStoredTemperature + 10);
		currentStoredTemperature=environmentInstance.getTemperature();
	}
	void lowerTemperature()
	{
		environmentInstance.setTemperature(currentStoredTemperature - 10);
		currentStoredTemperature=environmentInstance.getTemperature();
	}
	void raisePh()
	{
		environmentInstance.setPh(currentStoredPh + 1);
		currentStoredPh=environmentInstance.getPh();
	}
	void lowerPh()
	{
		environmentInstance.setPh(currentStoredPh - 1);
		currentStoredPh=environmentInstance.getPh();
	}
	void alterRadiation()
	{
		if (environmentInstance.getRadiation()==true)
		{
			environmentInstance.setRadiation(false);
		}
		else
		{
			environmentInstance.setRadiation(true);
		}
		currentStoredRadiationPresent=environmentInstance.getRadiation();
	}
	void tradeAwayPops(int pops)
	{
		environmentInstance.tradeAwayPops(pops);
		
	}
	void triggerReproduction()
	{
		//in updated form we will add a loop here.
		//we will also include a parameter that reads generation count
		generations++;
		if(gameOver==true)
			return;
		useInterface.disableAll("Reproducing");
		environmentInstance.reproductionEvent();
		while(tempIncreases!=0)
		{
			tempIncreases--;
			raiseTemperature();
		}
		while(tempDecreases!=0)
		{
			tempDecreases--;
			lowerTemperature();
		}
		while(pHIncreases!=0)
		{
			pHIncreases--;
			raisePh();
		}
		while(pHDecreases!=0)
		{
			pHDecreases--;
			lowerPh();
		}
		while(radiationAlters!=0)
		{
			radiationAlters--;
			alterRadiation();
		}
		updateStoredData();
		migrateOut();
		updateStoredData();
		netInterface.initialize();
		//useInterface.enableAll();
		useInterface.updateLocalData();
		//useInterface.updateData();
		
		
		//maybe put a wait here
		/*try {
		this.wait(5000);
		}catch(Exception e) {
			e.printStackTrace();
		}*/
		//useInterface.updateData(getOpponentData(),getOpponentFitness());
		//implement buffer execution
		
	}
	void raiseOpponentTemperature(String opponentId)
	{
		//implement after network features are available
		netInterface.raiseOpponentTemperature(opponentId);
	}
	void lowerOpponentTemperature(String opponentId)
	{
		//implement after network features are available
		netInterface.lowerOpponentTemperature(opponentId);
	}
	void raiseOpponentPh(String opponentId)
	{
		//implement after network features are available
		netInterface.raiseOpponentPh(opponentId);
	}
	void lowerOpponentPh(String opponentId)
	{
		//implement after network features are available
		netInterface.lowerOpponentPh(opponentId);
	}
	void alterOpponentRadiation(String opponentId)
	{
		//implement after network features are available
		netInterface.alterOpponentRadiation(opponentId);
	}
	void migrate(ArrayList<String> types,ArrayList<Integer> frequencies)
	{
		//implement when the multiplayer interface is present and migration implemented
		environmentInstance.receiveMigration(types, frequencies);
	}
	void migrateOut()
	{
		HashMap<String,Integer> migrators=environmentInstance.getMigraters();
		netInterface.migrateOut(migrators);
	}
	String getMostFitGenotype()
	{
		double currMax;
		int maxIndex=0;
		currMax = currentStoredGenotypeFitnesses.get(0);
		for (int i = 1; i < currentStoredGenotypeFitnesses.size(); i++)
		{
			if (currMax < currentStoredGenotypeFitnesses.get(i))
			{
				currMax = currentStoredGenotypeFitnesses.get(i);
				maxIndex = i;
			}
		}
		return currentStoredGenotypes.get(maxIndex);

	}
	double getMaxFitness()
	{
		double currMax;
		currMax = currentStoredGenotypeFitnesses.get(0);
		for (int i = 1; i < currentStoredGenotypeFitnesses.size(); i++)
		{
			if (currMax < currentStoredGenotypeFitnesses.get(i))
			{
				currMax = currentStoredGenotypeFitnesses.get(i);
			}
		}
		return currMax;
	}
	void generatePlayerID()
	{
		String newID = "";
		int nextVal;
		Random rand=new Random();
		for (int i = 0; i < 7; i++)
		{
			nextVal = rand.nextInt(10);
			newID = newID + (nextVal);
		}
		ID = newID;
	}
	String getPlayerID()
	{
		return ID;
	}
	public void bufferTemperatureDecrease()
	{
		tempDecreases++;
	}
	public void bufferTemperatureIncrease()
	{
		tempIncreases++;
	}
	public void bufferPhDecrease()
	{
		pHDecreases++;
	}
	public void bufferPhIncrease()
	{
		pHIncreases++;
	}
	public void bufferAlterRadiation()
	{
		radiationAlters++;
	}
	public void setInterface(UserInterface newInterface)
	{
		useInterface=newInterface;
	}
	public void endGame(boolean won, String winner)
	{
		//inInterface.endConnection();
		if(won)
		{
			if(winner.equals(getPlayerID()))
			{
				useInterface.disableAll("You won!");
			}else
			useInterface.disableAll("Game won by: "+winner);
		}else {
		useInterface.disableAll("Game over: no population left");
		}
		gameOver=true;
		//backend.stop();
	}
	public HashMap<String,String> getOpponentData()
	{
		/*String response=netInterface.updateOpponentData();
		System.out.println(response);
		JSONParser parser=new JSONParser();
		ContainerFactory container=new ContainerFactory(){
			@Override
			public Map createObjectContainer() {
				return new LinkedHashMap<>();
			}
			@Override
			public List creatArrayContainer() {
				return new LinkedList<>();
			}
		};
		try {
			Map dataMap=(Map)parser.parse(response,container);
			return (HashMap<String,String>)dataMap;
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return null;*/
		return netInterface.updateOpponentData();
		
	}
	public HashMap<String,Double> getOpponentFitness()
	{
		/*String response=netInterface.updateOpponentFitness();
		JSONParser parser=new JSONParser();
		ContainerFactory container=new ContainerFactory(){
			@Override
			public Map createObjectContainer() {
				return new LinkedHashMap<>();
			}
			@Override
			public List creatArrayContainer() {
				return new LinkedList<>();
			}
		};
		try {
			Map dataMap=(Map)parser.parse(response,container);
			return (HashMap<String,Double>)dataMap;
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return null;*/
		return netInterface.updateOpponentFitness();
	}
	public int getPort()
	{
		return PORT;
	}
	public int getGenerations()
	{
		return generations;
	}

}
