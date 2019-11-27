package kurt.muaz.genetic.Abstract;

import java.util.Random;
//Min class
public abstract class Genetic {

	protected Population population = new Population();
	protected Individual fittest,
						 secondFittest;
	public final Individual spring()
	{
		//Do selection
		selection();
		//Do crossover
		crossover();
		//mutation
		mutation();
		//Calculate new fitness value
		return population.sortFitness();
	}

	//Selection
	public abstract void selection();

	//Crossover
	public void crossover()
	{
		fittest.crossover(secondFittest);
	}

	//Mutation
	public void mutation()
	{
		Random rn = new Random();
		if (rn.nextInt() % 20 <= 12)
			population.mutate();
	}

	@Override
	public String toString()
	{
		return fittest.toString();
	}
}