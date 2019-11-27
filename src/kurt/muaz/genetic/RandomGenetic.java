package kurt.muaz.genetic;

import kurt.muaz.genetic.Abstract.Genetic;
import kurt.muaz.genetic.Abstract.Population;

import java.util.Random;

public class RandomGenetic extends Genetic
{

	@Override
	public void selection()
	{
		Random rn = new Random();
		int fitindex = rn.nextInt(Population.popSize);
		fittest = population.getIndividual(fitindex);
		int fit2;
		do {
			fit2 = rn.nextInt(Population.popSize);
		} while(fitindex == fit2);
		secondFittest = population.getIndividual(fit2);
		population.select(fitindex, fit2);
	}

}
