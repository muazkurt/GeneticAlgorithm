package kurt.muaz.genetic.Abstract;

import java.util.Random;

//Population class
public class Population
{
	public static final int popSize = 20;
	private Individual[] individuals = new Individual[popSize];
	private double total	= 0;

	Population()
	{
		for (int i = 0; i < Population.popSize; i++)
			individuals[i] = new Individual();
		sortFitness();
	}

	//Calculate fitness of each individual
	public Individual sortFitness()
	{
		total = 0;
		for (int i = 1; i < individuals.length; ++i)
		{ 
			Individual key = individuals[i]; 
			double key_value = key.getFitness();
			total += Math.abs(key_value);
			
			int j = i - 1;
			while (j >= 0 && individuals[j].getFitness() < key_value)
			{ 
				individuals[j + 1] = individuals[j]; 
				j = j - 1; 
			} 
			individuals[j + 1] = key; 
		}
		return individuals[0];
	}

	void mutate()
	{
		individuals[0].mutate();
		individuals[1].mutate();
	}

	public double getTotal()
	{
		return total;
	}

	public Individual getIndividual(int index)
	{
		return individuals[index];
	}

	public void select(int first, int sec)
	{
		individuals[Population.popSize - 1] = individuals[first];
		individuals[Population.popSize - 2] = individuals[sec];
		Random rn = new Random();
		for (int i = 0; i < Population.popSize - 2; i++)
			if (!(i == first || i == sec))
				if (rn.nextInt(10) < 2)
					individuals[i] = new Individual();
	}
}
