import kurt.muaz.genetic.Abstract.Genetic;
import kurt.muaz.genetic.Abstract.Individual;
import kurt.muaz.genetic.RandomGenetic;
import kurt.muaz.genetic.RankGenetic;
import kurt.muaz.genetic.RouletteGenetic;

import java.util.Random;

public class Tester
{
	public static void main(String[] args) 
	{
		int generation = 0;
		//for (int j = 0; j < 20; j++)
		{
			Genetic demo = new RankGenetic();
			double max = 0;

			double last_fit = 0.0;
			Individual fittest;
			int i = 0;
			do{
				fittest = demo.spring();

				if (fittest.getFitness() > max) {
					max = fittest.getFitness();
					System.out.println("Generation: "
							                   + generation
							                   + " Fittest: "
							                   + fittest.getFitness()
							                   + " Max: " + max);
				}
				if (Math.abs(last_fit - fittest.getFitness()) > 1) {
					i = 0;
					last_fit = fittest.getFitness();
				}
				++i;
				++generation;
			} while (fittest.getFitness() < 125);
			System.out.println("\nSolution found in generation " + generation);
			System.out.println(fittest);
			System.out.println("Max: " + max);
		}
	}
	/*

		Random rn = new Random();
		Genetic demo = new RouletteGenetic();
		double most_recent = 0.0;
		int generation = 0;
		for(int i = 0; i < 20;)
		{
			double local_item = demo.spring();
			if(Math.abs(most_recent - local_item) > 1)
			{
				i = 0;
				most_recent = local_item;
			}
			++i;
			System.out.println("Generation " + generation++ + " fitness: " + local_item);
		}
	}*/
}