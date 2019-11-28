package kurt.muaz.visualizer;

import kurt.muaz.genetic.RandomGenetic;

public class RandomGeneticVisualizer extends  AbstractGeneticVisualizer
{
	public RandomGeneticVisualizer()
	{
		local_genetic = new RandomGenetic();
		frame.setTitle("Random");
	}
}
