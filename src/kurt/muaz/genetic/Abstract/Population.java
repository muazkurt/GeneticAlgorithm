package src.kurt.muaz.genetic.Abstract;

import java.util.Random;

//Population class
public class Population
{
	public static final int popSize = 14;
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
		Random rn = new Random();
		int to_mutate = rn.nextInt(Population.popSize / 2);
		for (int i = 0; i < to_mutate; i++)
			individuals[rn.nextInt(Population.popSize)].mutate();
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
		for (int i = 0; i < popSize; i++) {
			if (i == first || i == sec) ;
			else individuals[i] = new Individual();
		}
	}
}
