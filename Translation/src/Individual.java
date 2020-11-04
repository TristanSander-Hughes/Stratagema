
public class Individual {
	
	private boolean flaggela=false;//improved mobility
	private boolean xerophile=false;//resistant to high temperatures
	private boolean psychrophile=false;//resistant to low temperatures
	private boolean vitalLocus1=true;
	private boolean vitalLocus2=true;
	private boolean vitalLocus3=true;
	private boolean hardenedBacterialShell=false;//radiation resistance
	private boolean aggressor=false;//kills one population memeber every generation
	private boolean producer=false;//generates 4 units of a resource that increases fitness of a consumer when consumed
	private boolean consumer=false;//consumes 1 unit of the resource produced by producers
	private boolean sterile=false;//if selected for reproduction, this individual will not produce and will only die
	private boolean superficialLocus1=false;//three noncoding loci
	private boolean superficialLocus2=false;
	private boolean superficialLocus3=false;
	private double fitness=0.0;
	
	
	public Individual(boolean flag,boolean xero,boolean psy,boolean vital1, boolean vital2, boolean vital3,boolean shell, boolean aggr, boolean prod, boolean cons, boolean super1, boolean super2, boolean super3)
	{
		flaggela = flag;
		xerophile = xero;
		psychrophile = psy;
		vitalLocus1 = vital1;
		vitalLocus2 = vital2;
		vitalLocus3 = vital3;
		hardenedBacterialShell = shell;
		aggressor = aggr;
		producer = prod;
		sterile = false;
		superficialLocus1 = super1;
		superficialLocus2 = super2;
		superficialLocus3 = super3;
	}
	public Individual(String stringGene)
	{
		if(stringGene.charAt(0)=='1')
		{
			flaggela=true;
		}else {
			flaggela=false;
		}
		if(stringGene.charAt(1)=='1')
		{
			xerophile=true;
		}else {
			xerophile=false;
		}
		if(stringGene.charAt(2)=='1')
		{
			psychrophile=true;
		}else {
			psychrophile=false;
		}
		if(stringGene.charAt(3)=='1')
		{
			vitalLocus1=true;
		}else {
			vitalLocus1=false;
		}
		if(stringGene.charAt(4)=='1')
		{
			vitalLocus2=true;
		}else {
			vitalLocus2=false;
		}
		if(stringGene.charAt(5)=='1')
		{
			vitalLocus3=true;
		}else {
			vitalLocus3=false;
		}
		if(stringGene.charAt(6)=='1')
		{
			hardenedBacterialShell=true;
		}else {
			hardenedBacterialShell=false;
		}
		if(stringGene.charAt(7)=='1')
		{
			aggressor=true;
		}else {
			aggressor=false;
		}
		if(stringGene.charAt(8)=='1')
		{
			producer=true;
		}else {
			producer=false;
		}
		if(stringGene.charAt(9)=='1')
		{
			consumer=true;
		}else {
			consumer=false;
		}
		if(stringGene.charAt(10)=='1')
		{
			sterile=true;
		}else {
			sterile=false;
		}
		if(stringGene.charAt(11)=='1')
		{
			superficialLocus1=true;
		}else {
			superficialLocus1=false;
		}
		if(stringGene.charAt(12)=='1')
		{
			superficialLocus2=true;
		}else {
			superficialLocus2=false;
		}
		if(stringGene.charAt(13)=='1')
		{
			superficialLocus3=true;
		}else {
			superficialLocus3=false;
		}
	}
	void setFitness(double newFitness)
	{
		fitness = newFitness;
	}
	double getFitness() 
	{
		return fitness;
	}
	boolean getFlaggela() 
	{
		return flaggela;
	}
	boolean getXerophile() 
	{
		return xerophile;
	}
	boolean getPsychrophile() 
	{
		return psychrophile;
	}
	boolean getVitalLocus1() 
	{
		return vitalLocus1;
	}
	boolean getVitalLocus2() 
	{
		return vitalLocus2;
	}
	boolean getVitalLocus3() 
	{
		return vitalLocus3;
	}
	boolean getHardenedBacterialShell() 
	{
		return hardenedBacterialShell;
	}
	 boolean getAggressor() 
	{
		return aggressor;
	}
	boolean getProducer() 
	{
		return producer;
	}
	boolean getConsumer() 
	{
		return consumer;
	}
	boolean getSterile() 
	{
		return sterile;
	}
	boolean getSuperficialLocus1() 
	{
		return superficialLocus1;
	}
	boolean getSuperficialLocus2() 
	{
		return superficialLocus2;
	}
	boolean getSuperficialLocus3() 
	{
		return superficialLocus3;
	}
	void setFlaggela(boolean flag)
	{
		flaggela = flag;
	}
	void setXerophile(boolean xero)
	{
		xerophile = xero;
	}
	void setPsychrophile(boolean psyc)
	{
		psychrophile = psyc;
	}
	void setVitalLocus1(boolean vital)
	{
		vitalLocus1 = vital;
	}
	void setVitalLocus2(boolean vital)
	{
		vitalLocus2 = vital;
	}
	void setVitalLocus3(boolean vital)
	{
		vitalLocus3 = vital;
	}
	void setHardenedBacterialShell(boolean shell)
	{
		hardenedBacterialShell = shell;
	}
	void setAggressor(boolean aggro)
	{
		aggressor = aggro;
	}
	void setProducer(boolean prod)
	{
		producer = prod;
	}
	void setConsumer(boolean cons)
	{
		consumer = cons;
	}
	void setSterile(boolean ster)
	{
		sterile = ster;
	}
	void setSuperficialLocus1(boolean superLocus)
	{
		superficialLocus1 = superLocus;
	}
	void setSuperficialLocus2(boolean superLocus)
	{
		superficialLocus2 = superLocus;
	}
	void setSuperficialLocus3(boolean superLocus)
	{
		superficialLocus3 = superLocus;
	}
	public String toString()
	{
		 String tempGenotype;
		  tempGenotype = "";
			 if(this.getFlaggela()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(this.getXerophile()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(this.getPsychrophile()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(this.getVitalLocus1()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(this.getVitalLocus2()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(this.getVitalLocus3()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(this.getHardenedBacterialShell()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(this.getAggressor()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(this.getProducer()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(this.getConsumer()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(this.getSterile()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(this.getSuperficialLocus1()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(this.getSuperficialLocus2()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 if(this.getSuperficialLocus3()==true)
			 {
				 tempGenotype=tempGenotype+"1";
			 }else
			 {
				 tempGenotype=tempGenotype+"0";
			 }
			 return tempGenotype;
	}
	
}
