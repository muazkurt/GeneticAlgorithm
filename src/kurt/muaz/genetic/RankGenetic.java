package src.kurt.muaz.genetic;

import src.kurt.muaz.genetic.Abstract.Genetic;
import src.kurt.muaz.genetic.Abstract.Population;

public class RankGenetic extends Genetic
{
	@Override
	public void selection()
	{
		fittest = population.getIndividual(0);
		secondFittest = population.getIndividual(1);
		population.select(0, 1);
	}
}
