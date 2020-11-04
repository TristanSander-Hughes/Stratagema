import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
public class Population {
	private int currentPopulationSize=0;
	private int mutability;
	private int mutationChance;
	private int MAXPOPULATIONSIZE;
	private int MAXMIGRATORS=25;
	private double LUCK = 10;
	private boolean producersPresent=false;
	private HashMap<String,Double> genotypeFitnesses;
	private HashMap<String,Integer> genotypeFrequencies;
	private ArrayList<String> genotypes;
	private ArrayList<Individual> populace;
	private boolean FailState=false;
	
	 Population(int maxSize,int mut,int chance,int initialSize)
		{
		 	MAXPOPULATIONSIZE=maxSize;
			 mutability = mut;
			 mutationChance = chance;
			 populace=new ArrayList<Individual>();
			 genotypes=new ArrayList<String>();
			 genotypeFitnesses=new HashMap<String,Double>();
			 genotypeFrequencies=new HashMap<String,Integer>();
			 for (int i = 0; i < initialSize; i++)
			 {
				 populace.add(new Individual(false, false, false, true, true, true, false, false, false, false, true, true, true));
			 }
			 currentPopulationSize = initialSize;
		}
	 void rectifyPopulationSize()
	 {
		 
		 if (currentPopulationSize > MAXPOPULATIONSIZE)
		 {
			 cull(currentPopulationSize - MAXPOPULATIONSIZE);
			
		 }
	 }
	 void cull(int number)
	 {
		 Random rand=new Random();
		 int position;
		
		 for (int i = 0; i < number; i++)
		 {
			 position = rand.nextInt(currentPopulationSize);
			 
			 
			populace.remove(position);
			currentPopulationSize--;
			 
		 }
	 }
	 void rectifySurvivalRate()
	 {
		 
		 for(int i=0; i<currentPopulationSize;i++)
		 {
			 if (!populace.get(i).getVitalLocus1() || !populace.get(i).getVitalLocus2() || !populace.get(i).getVitalLocus3())
			 {
				 populace.remove(i);
				 currentPopulationSize--;
			 }
			
		 }
		 
	 }
	 void reproduce()
	 {
		 
		ArrayList<Individual> tempPop=new ArrayList<Individual>();
		ArrayList<Individual> currentBreedingGroup=new ArrayList<Individual>();
		 int breedingGroupSize=Math.max(1,currentPopulationSize/4);
		 double maxFitness;
		 int maxIndex;
		 Random rand=new Random();
		 double currentFitness;
		 int currentLuck;
		 int muteRoll;
		// System.out.println("beginning reproduction");
		 while (tempPop.size()<currentPopulationSize*2)
		 {
			 currentBreedingGroup.clear();
			 //currentBreedingGroup.trimToSize();

			 
			 while (currentBreedingGroup.size() < breedingGroupSize)
			 {
				// System.out.println("Selecting breeding group: current size "+currentBreedingGroup.size()+" desired size:  "+breedingGroupSize+" with population: "+populace.size());
				 for (int i = 0; i < populace.size(); i++)
				 {
					 
					 int currentChance = rand.nextInt(100);
					// System.out.println("Current Chance " +currentChance);
					 if (currentChance <60)
					 {
						 currentBreedingGroup.add(populace.get(i));
					 }
				 }
			 }

			 currentLuck = rand.nextInt(100);
			 maxIndex = 0;
			 if (currentLuck >= LUCK)
			 {
				 
				 maxFitness = currentBreedingGroup.get(0).getFitness();//this throws an exception
				 for (int i = 0; i < currentBreedingGroup.size(); i++)
				 {
					 if (currentBreedingGroup.get(i).getFitness()>maxFitness)
					 {
						 maxFitness = currentBreedingGroup.get(i).getFitness();
						 maxIndex = i;
					 }
				 }
			 }
			 tempPop.add(new Individual(currentBreedingGroup.get(maxIndex).getFlaggela(), currentBreedingGroup.get(maxIndex).getXerophile(), currentBreedingGroup.get(maxIndex).getPsychrophile(), currentBreedingGroup.get(maxIndex).getVitalLocus1(), currentBreedingGroup.get(maxIndex).getVitalLocus2(), currentBreedingGroup.get(maxIndex).getVitalLocus3(), currentBreedingGroup.get(maxIndex).getHardenedBacterialShell(), currentBreedingGroup.get(maxIndex).getAggressor(), currentBreedingGroup.get(maxIndex).getProducer(), currentBreedingGroup.get(maxIndex).getConsumer(), currentBreedingGroup.get(maxIndex).getSuperficialLocus1(), currentBreedingGroup.get(maxIndex).getSuperficialLocus2(), currentBreedingGroup.get(maxIndex).getSuperficialLocus3()));
			 tempPop.add(new Individual(currentBreedingGroup.get(maxIndex).getFlaggela(), currentBreedingGroup.get(maxIndex).getXerophile(), currentBreedingGroup.get(maxIndex).getPsychrophile(), currentBreedingGroup.get(maxIndex).getVitalLocus1(), currentBreedingGroup.get(maxIndex).getVitalLocus2(), currentBreedingGroup.get(maxIndex).getVitalLocus3(), currentBreedingGroup.get(maxIndex).getHardenedBacterialShell(), currentBreedingGroup.get(maxIndex).getAggressor(), currentBreedingGroup.get(maxIndex).getProducer(), currentBreedingGroup.get(maxIndex).getConsumer(), currentBreedingGroup.get(maxIndex).getSuperficialLocus1(), currentBreedingGroup.get(maxIndex).getSuperficialLocus2(), currentBreedingGroup.get(maxIndex).getSuperficialLocus3()));


			 
		 }//the above code simply selects, then reproduces, from here on we must implement mutation and actual population update functionality
		
		 for (int i = 0; i < tempPop.size(); i++)
		 {
			 
			 muteRoll = rand.nextInt(100);
			 if (muteRoll <= mutationChance)
			 {
				 mutate(tempPop.get(i));
			 }
			 
		 }
		// cout << "Mutation Complete" << endl;
		 populace.clear();
		 populace=tempPop;
		 currentPopulationSize = populace.size();
		// cout << "swap over complete" << endl;
		 rectifyPopulationSize();
		// cout << "population size rectified" << endl;
		 rectifySurvivalRate();
		// System.out.println("Reproduction Complete");
	 }
	 void mutate(Individual currentIndividual)
	 {
		 boolean[] locii=new boolean[14];
		 locii[0] =currentIndividual.getFlaggela();
		 locii[1] = currentIndividual.getXerophile();
		 locii[2] = currentIndividual.getPsychrophile();
		 locii[3] = currentIndividual.getVitalLocus1();
		 locii[4] = currentIndividual.getVitalLocus2();
		 locii[5] = currentIndividual.getVitalLocus3();
		 locii[6] = currentIndividual.getHardenedBacterialShell();
		 locii[7] = currentIndividual.getAggressor();
		 locii[8] = currentIndividual.getProducer();
		 locii[9] = currentIndividual.getConsumer();
		 locii[10] = currentIndividual.getSterile();
		 locii[11] = currentIndividual.getSuperficialLocus1();
		 locii[12] = currentIndividual.getSuperficialLocus2();
		 locii[13] = currentIndividual.getSuperficialLocus3();
		 int mutIterations = mutability;
		 int mutIndex;
		 Random rand=new Random();
		 while (mutIterations > 0)
		 {
			 mutIterations--;
			
			 mutIndex = rand.nextInt(14);
			// cout<<mutIndex<<endl;
			 if (locii[mutIndex]==true)
			 {
				 locii[mutIndex] = false;
			 }
			 else
			 {
				 locii[mutIndex] = true;
			 }
		 }
		// for (int i = 0; i < 14; i++)
			// cout << locii[i] << endl;
		 currentIndividual.setFlaggela(locii[0]);
		 currentIndividual.setXerophile(locii[1]);
		 currentIndividual.setPsychrophile(locii[2]);
		 currentIndividual.setVitalLocus1(locii[3]);
		 currentIndividual.setVitalLocus2(locii[4]);
		 currentIndividual.setVitalLocus3(locii[5]);
		 currentIndividual.setHardenedBacterialShell(locii[6]);
		 currentIndividual.setAggressor(locii[7]);
		 currentIndividual.setProducer(locii[8]);
		 currentIndividual.setConsumer(locii[9]);
		 currentIndividual.setSterile(locii[10]);
		 currentIndividual.setSuperficialLocus1(locii[11]);
		 currentIndividual.setSuperficialLocus2(locii[12]);
		 currentIndividual.setSuperficialLocus3(locii[13]);
	 }
	 boolean matchGenotype(String type1,Individual type2)
	 {
		 

		 String tempType=type2.toString();
		 if(tempType.compareTo(type1)==0)
			return true;
		 
		 return false;
		 
		 
	 }
	 void detectGenotypes()
	 {
		 genotypeFrequencies.clear();
		 genotypes.clear();
		 genotypeFitnesses.clear();
		 String tempGenotype;
		 for (int i = 0; i < populace.size(); i++)
		 {
			 tempGenotype = "";
			 if(populace.get(i).getFlaggela()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(i).getXerophile()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(i).getPsychrophile()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(i).getVitalLocus1()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(i).getVitalLocus2()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(i).getVitalLocus3()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(i).getHardenedBacterialShell()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(i).getAggressor()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(i).getProducer()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(i).getConsumer()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(i).getSterile()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(i).getSuperficialLocus1()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(i).getSuperficialLocus2()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(i).getSuperficialLocus3()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 //map<vector<bool>, int>::iterator it = genotypeFrequencies.find(tempVector);
			 if (genotypeFrequencies.containsKey(tempGenotype))
			 {
				 genotypeFrequencies.put(tempGenotype, genotypeFrequencies.get(tempGenotype)+1);
			 }
			 else
			 {
				 genotypes.add(tempGenotype);
				 genotypeFrequencies.put(tempGenotype, 1);
				 genotypeFitnesses.put(tempGenotype,0.0 );
			 }
			 
		 }

	 }
	 ArrayList<String> getGenotypes()
	 {
		 return genotypes;
	 }
	 ArrayList<Integer> getFrequencies()//alter this to be less cumbersome
	 {
		 ArrayList<Integer> freqVector=new ArrayList<Integer>();
		 Iterator position=genotypeFrequencies.entrySet().iterator();
		 
		 while(position.hasNext())
		 {
			 freqVector.add((Integer)((Map.Entry)position.next()).getValue());
		 }
		 return freqVector;
	 }
	 int getPopulationSize()
	 {
		 currentPopulationSize=populace.size();
		 return currentPopulationSize;
	 }
	 int getMaxSize()
	 {
		 return MAXPOPULATIONSIZE;
	 }
	ArrayList<Double> getFitnesses()
	 {	
		 ArrayList<Double> fitnesses=new ArrayList<Double>();
		 Iterator position=genotypeFitnesses.entrySet().iterator();
		 while (position.hasNext())
		 {
			 fitnesses.add((Double)((Map.Entry)position.next()).getValue());
			
		 }
		 return fitnesses;
	 }
	 void setFitnesses(Double[] newFitnesses)
	 {
		 int i = 0;
		 Iterator<Map.Entry<String,Double>> position=genotypeFitnesses.entrySet().iterator();
		while (position.hasNext())
		 {
			 position.next().setValue(newFitnesses[i]);
			 i++;
		 }
		/* genotypeFitnesses=newFitnesses;*/
		 String tempGenotype;
		 for (int j = 0; j < populace.size(); j++)
		 {
			 tempGenotype = "";
			 //tempGenotype = tempGenotype + populace.get(j).getFlaggela() + populace.get(j).getXerophile() + populace.get(j).getPsychrophile() + populace.get(j).getVitalLocus1() + populace.get(j).getVitalLocus2() + populace.get(j).getVitalLocus3() + populace.get(j).getHardenedBacterialShell() + populace.get(j).getAggressor() + populace.get(j).getProducer() + populace.get(j).getConsumer() + populace.get(j).getSterile() + populace.get(j).getSuperficialLocus1() + populace.get(j).getSuperficialLocus2() + populace.get(j).getSuperficialLocus3();
			 if(populace.get(j).getFlaggela()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(j).getXerophile()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(j).getPsychrophile()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(j).getVitalLocus1()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(j).getVitalLocus2()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(j).getVitalLocus3()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(j).getHardenedBacterialShell()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(j).getAggressor()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(j).getProducer()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(j).getConsumer()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(j).getSterile()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(j).getSuperficialLocus1()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(j).getSuperficialLocus2()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(populace.get(j).getSuperficialLocus3()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 populace.get(j).setFitness(genotypeFitnesses.get(tempGenotype));

		 }
	 }
	 int detectProducers()
	 {
		 producersPresent = false;
		 int units = 0;
		 for (int i = 0; i < populace.size(); i++)
		 {
			 if (populace.get(i).getProducer())
			 {
				 producersPresent = true;
				 units++;
			 }
		 }
		 return units;
	 }
	 boolean getProducersPresent()
	 {
		 return producersPresent;
	 }
	 void consume(int units)
	 {
		 for (int i = 0; i < populace.size(); i++)
		 {
			 if (populace.get(i).getConsumer())
			 {
				 if (units > 0)
				 {
					 units--;
				 }
				 else
				 {
					 populace.remove(i);
					 currentPopulationSize--;
				 }
			 }
		 }
	 }
	 void aggress()
	 {
		 for (int i = 0; i < populace.size(); i++)
		 {
			 if (populace.get(i).getAggressor())
			 {
				 if (i + 1 != populace.size())
				 {
					 populace.remove( i + 1);
					 currentPopulationSize--;
				 }
				 else
				 {
					 populace.remove(0);
					 currentPopulationSize--;
				 }
			 }
		 }
	 }
	 void tradeAwayPops(int numberOfPops)
	 {
		 cull(numberOfPops);
	 }
	 
	 boolean checkEndState()
	 {
		 if(currentPopulationSize<=0)
			 return true;
		 return false;
	 }
	 boolean getFailState()
	 {
		 return FailState;

	 }
	 void integrateMigrants(ArrayList<String> types, ArrayList<Integer> frequencies)
	 {
		 
		 for(int i=0; i<types.size();i++)
		 {
			 for(int j=0; j<frequencies.get(i); j++)
			 {
				 populace.add(new Individual(types.get(i)));
				 currentPopulationSize++;
			 }
		 }
	 }
	 HashMap<String, Integer> getMigraters()
	 {
		 //complete this after meeting
		 Random rand=new Random();
		 HashMap<String,Integer> migrators=new HashMap<String,Integer>();
		 String currentType;
		 for(int i=0; i<MAXMIGRATORS;i++)
		 {
			 int next=rand.nextInt(genotypes.size());
			 currentType=genotypes.get(next);
			 //System.out.println(currentType);
			 //System.out.println(genotypeFrequencies.toString());
			 if(genotypeFrequencies.get(currentType)>1)
			 {
				 genotypeFrequencies.put(currentType, genotypeFrequencies.get(currentType)-1);
				 
			 }else
			 {
				genotypeFrequencies.remove(currentType);
				genotypeFitnesses.remove(currentType);
				genotypes.remove(genotypes.indexOf(currentType));
			 }
			 if(migrators.containsKey(currentType))
			 {
				 migrators.put(currentType, migrators.get(currentType)+1);
			 }else {
				 migrators.put(currentType, 1);
			 }
			 for(int j=0; j<populace.size();j++)
			 {
				 if(matchGenotype(currentType,populace.get(j))) {
					 populace.remove(j);
					 currentPopulationSize--;
					 break;
				 }
			 }
		 }
		 return migrators;
		 
	 }
}
