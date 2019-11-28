package kurt.muaz.genetic;

import kurt.muaz.genetic.Abstract.Genetic;

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
