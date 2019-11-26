//Population class
class Population
{
	public static final int popSize = 20;
	Individual[] individuals = new Individual[popSize];
	double 	fittest	= 0,
			total	= 0;
	
	public Population()
	{
		for (int i = 0; i < Population.popSize; i++)
			individuals[i] = new Individual();
	}

	//Calculate fitness of each individual
	public void calculateFitness()
	{
		total = 0;
		for (int i = 1; i < individuals.length; ++i)
		{ 
			Individual key = individuals[i]; 
			double key_value = key.calcFitness();
			total += key_value;
			
			int j = i - 1;
			while (j >= 0 && individuals[j].calcFitness() < key_value)
			{ 
				individuals[j + 1] = individuals[j]; 
				j = j - 1; 
			} 
			individuals[j + 1] = key; 
		} 
		fittest = individuals[0].getfitness();
	}

}
