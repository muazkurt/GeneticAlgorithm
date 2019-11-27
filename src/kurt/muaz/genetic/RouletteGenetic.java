package src.kurt.muaz.genetic;

import src.kurt.muaz.genetic.Abstract.Genetic;
import src.kurt.muaz.genetic.Abstract.Population;

import java.util.Random;

public class RouletteGenetic extends Genetic
{
	@Override
	public void selection()
	{
		Random x = new Random();
		population.sortFitness();
		int fit = x.nextInt() % (int) population.getTotal();
		int fit_pos = 0;
		int sec_pos = 0;
		for (double local = 0; fit_pos < Population.popSize - 1&& local < fit; ++fit_pos)
			local += Math.abs(population.getIndividual(fit_pos).getFitness());
		fittest = population.getIndividual(fit_pos);
		int divide = ((int) Math.abs(population.getTotal() - Math.abs(fittest.getFitness())));

		if(divide == 0)
			secondFittest = population.getIndividual(0);
		else
		{
			fit = x.nextInt(divide);
			for (double local = 0; sec_pos < Population.popSize - 1&& local < fit; ++sec_pos) {
				if (sec_pos != fit_pos)
				{
					local += Math.abs(population.getIndividual(sec_pos).getFitness());
					secondFittest = population.getIndividual(sec_pos);
				}
			}
		}
		population.select(fit_pos, sec_pos);
	}
}
