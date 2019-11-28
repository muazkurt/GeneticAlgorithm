package kurt.muaz.visualizer;

import kurt.muaz.genetic.RankGenetic;

public class RankedGeneticVisualizer extends AbstractGeneticVisualizer
{
	public RankedGeneticVisualizer()
	{
		frame.setTitle("Ranked");
		local_genetic = new RankGenetic();
	}
}
