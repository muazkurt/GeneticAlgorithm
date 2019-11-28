import kurt.muaz.visualizer.AbstractGeneticVisualizer;
import kurt.muaz.visualizer.RandomGeneticVisualizer;
import kurt.muaz.visualizer.RankedGeneticVisualizer;
import kurt.muaz.visualizer.RouletteGeneticVisualizer;

/**
 *
 * @author Muaz Kurt
 */
public class Driver
{
    public static void main(String[] args)
    {
	    AbstractGeneticVisualizer randomGeneticVisualizer = new RandomGeneticVisualizer();
	    AbstractGeneticVisualizer rankedGeneticVisualizer = new RankedGeneticVisualizer();
	    AbstractGeneticVisualizer rouletteGeneticVisualizer = new RouletteGeneticVisualizer();
	    randomGeneticVisualizer.init();
	    rankedGeneticVisualizer.init();
	    rouletteGeneticVisualizer.init();
	    Thread rand = new Thread(randomGeneticVisualizer);
	    Thread rank = new Thread(rankedGeneticVisualizer);
	    Thread rult = new Thread(rouletteGeneticVisualizer);
	    rand.start();
	    rank.start();
	    rult.start();
    }
}