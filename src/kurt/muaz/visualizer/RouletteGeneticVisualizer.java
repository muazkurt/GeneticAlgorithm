package kurt.muaz.visualizer;

import kurt.muaz.genetic.RouletteGenetic;

public class RouletteGeneticVisualizer extends AbstractGeneticVisualizer
{
	public RouletteGeneticVisualizer()
	{
		frame.setTitle("Roulette");
		local_genetic = new RouletteGenetic();
	}
}