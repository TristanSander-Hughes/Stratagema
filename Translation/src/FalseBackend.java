import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FalseBackend  {
	private Player playerInterface;
	private int MIGRATENUMBER=25;
	private long TIMERINTERVAL=20000;
	private int maxReproductions=3;
	private int currentReproduction=0;
	private Timer timer;
	public void begin()
	{
		timer=new Timer();
		timer.scheduleAtFixedRate(new reproducer(), TIMERINTERVAL, TIMERINTERVAL);
	}
	public void migrateOut(int popNumber, ArrayList<Integer> frequencies,ArrayList<String> genotypes)
	{
		System.out.println("Migrating out: "+popNumber);
		for(int i=0; i<genotypes.size();i++)
		{
			System.out.println(genotypes.get(i)+" "+frequencies.get(i));
			
		}
	}
	
	public void migrateIn()
	{
		Random rand=new Random();
		int chance;
		String newType;
		ArrayList<String> migraterTypes=new ArrayList<String>();
		ArrayList<Integer> migraterFrequencies=new ArrayList<Integer>();
		for(int i=0; i<MIGRATENUMBER; i++)
		{
			newType="";
			for(int j=0; j<14; j++)
			{
				
				chance=rand.nextInt(100);
				if(chance<50)
				{
					newType=newType+"1";
				}else
				{
					newType=newType+"0";
				}
			}
			if(migraterTypes.contains(newType))
			{
				migraterFrequencies.set(migraterTypes.indexOf(newType),migraterFrequencies.get(migraterTypes.indexOf(newType))+1);
			}else
			{
				migraterTypes.add(newType);
				migraterFrequencies.add(1);
			}
		}
		playerInterface.migrate(migraterTypes, migraterFrequencies);
	}
	public void TriggerReproduction()
	{
		migrateIn();
		playerInterface.triggerReproduction();
	}
	public void setInterface(Player newPlayer)
	{
		playerInterface=newPlayer;
	}
	public void stop()
	{
		timer.cancel();
	}
	public class reproducer extends TimerTask{
		public void run()
		{
			
			currentReproduction++;
			TriggerReproduction();
			if(currentReproduction==maxReproductions)
			{
				//playerInterface.endGame();
			}
			
		}
		
	}

}
