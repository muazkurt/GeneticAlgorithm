import java.util.Random;

public class Tester
{
	public static void main(String[] args) 
	{
		Random rn = new Random();
		Genetic demo = new Genetic();
		double max = 0;

		//Calculate fitness of each individual
		demo.population.calculateFitness();
		double last_fit = 0.0;
		System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);
		//While population gets an individual with maximum fitness
		for (int i = 0; i < 20; )
		{
			++demo.generationCount;

			//Do selection
			demo.selection();

			//Do crossover
			demo.crossover();

			//Do mutation under a random probability
			if (rn.nextInt() % 20  < 12) {
				demo.mutation();
			}

			//Add fittest offspring to population
			demo.addFittestOffspring();

			//Calculate new fitness value
			demo.population.calculateFitness();
			if(demo.population.fittest > max)
				max = demo.population.fittest;
			System.out.println("Generation: " 
						+ demo.generationCount 
						+ " Fittest: " 
						+ demo.population.fittest
						+ " Max: " + max);
			if(Math.abs(last_fit - demo.population.fittest) > 1)
			{
				i = 0;
				last_fit = demo.population.fittest;
			}
			++i;
		}
		System.out.println("\nSolution found in generation " + demo.generationCount);
		System.out.println("Fitness: " + demo.population.fittest);
		System.out.println("X1: " + demo.population.individuals[0].Alelle_x1);
		System.out.println("x2: " + demo.population.individuals[0].Alelle_x2);
		System.out.println("Max: " + max);
	}
}

/*
public class FunctionGenetic extends Genetic
{
	

	public FunctionGenetic(){}

	public init()
	{

	}

	//Compute fitness
	double compute()
	{
		double x1 = 0, x2 = 0;
		return f (x1, x2);
	}

	//Selection
	void selection()
	{

	}
	
	//Crossover
	void crossover()
	{

	}

	//Mutation
	void mutation()
	{

	}
	
}*/