import java.util.Random;
//Main class
public class Genetic {

	Population population = new Population();
	Individual fittest;
	Individual secondFittest;
	int generationCount = 0;

	//Selection
	void selection() {
		Random x = new Random();
		if(population.total >= 1.0)
		{
			double number = x.nextInt((int) Math.abs(population.total));
			double local_total = 0.0;
			for (int i = 0; local_total < number; i++)
			{
				local_total += population.individuals[i].getfitness();
				fittest = population.individuals[i];
			}
			local_total = 0.0;
			for (int i = 0; local_total < number && i < Population.popSize; i++)
				if(fittest != population.individuals[i])
				{
					local_total += population.individuals[i].getfitness();
					secondFittest = population.individuals[i];
				}
		}
		else 
		{
			fittest = population.individuals[0];
			secondFittest = population.individuals[1];
		}
	}

	//Crossover
	void crossover()
	{
		fittest.crossover(secondFittest);
	}

	//Mutation
	void mutation() {
		fittest.mutate();
		secondFittest.mutate();
	}

	//Get fittest offspring
	Individual getFittestOffspring() {
		if (fittest.getfitness() > secondFittest.getfitness()) {
			return fittest;
		}
		return secondFittest;
	}


	//Replace least fittest individual from most fittest offspring
	void addFittestOffspring() {
		
		for (int i = 0; i < Population.popSize; ++i) {
			if(!(population.individuals[i] == fittest || population.individuals[i] == secondFittest))
				population.individuals[i] = new Individual();
		}
	}

}

/*import java.util.function.Function;

public abstract class Genetic
{
	Population a;
	void spring(double delta) {
		init(20);
		while (compute() > delta) {
			selection();
			crossover();
			mutation();
		}
	}

	// Generate the initial population
	abstract void init(int population_size);

	// Compute fitness
	double compute()
	{
		return f(1,1);
	}

	// Selection
	abstract void selection();

	// Crossover
	abstract void crossover();

	// Mutation
	abstract void mutation();
}*/