package kurt.muaz.visualizer;

import kurt.muaz.genetic.Abstract.Genetic;
import kurt.muaz.genetic.Abstract.Individual;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * An abstract class that creates an interface to visualize
 * genetic algorithm steps and fitness.
 * @author Muaz Kurt
 */
public abstract class AbstractGeneticVisualizer extends JPanel implements Runnable
{
	private Color lineColor = new Color(44, 102, 230, 180);
	private Color pointColor = new Color(100, 100, 100, 180);
	private Color gridColor = new Color(200, 200, 200, 200);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	JFrame frame;


	Genetic local_genetic;
	private Queue<Double> scores;

	private volatile boolean STOP = true;
	private double  minScore = Double.MIN_VALUE,
					max = 0;
	private double last_fit = 0.0;
	private int generation = 0;

	/**
	 * Creates a generic definition of a Visualizer.
	 */
	AbstractGeneticVisualizer()
	{
		frame = new JFrame();
		scores = new ArrayBlockingQueue<>(40);
		setPreferredSize(new Dimension(800, 600));

		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Initializes the visualizer and adds buttons to it.
	 */
	public void init()
	{

		Button stop = new Button("STOP");
		stop.setBounds(80, 0, 60, 30);
		stop.addActionListener(e -> STOP = true);
		frame.getContentPane().add(stop);


		Button start = new Button("START");
		start.setBounds(140, 0, 60, 30);
		start.addActionListener(e -> STOP = false);
		frame.getContentPane().add(start);

		Button restart = new Button("RESTART");
		restart.setBounds(200, 0, 60, 30);
		restart.addActionListener(e -> {scores = new LinkedList<>(); STOP = true; repaint();});
		frame.getContentPane().add(restart);

		frame.getContentPane().add(this);
		frame.setVisible(true);
	}

	/**
	 * Paints the graph.
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int padding = 25;
		int labelPadding = 25;
		double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.size() - 1);
		double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (max - minScore);

		List<Point> graphPoints = new ArrayList<>();
		int i = 0;
		for (Double a_score : scores) {
			int x1 = (int) (i * xScale + padding + labelPadding);
			int y1 = (int) ((max - a_score) * yScale + padding);
			graphPoints.add(new Point(x1, y1));
			++i;
		}

		// draw white background
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding,
				getHeight() - 2 * padding - labelPadding);
		g2.setColor(Color.BLACK);

		// create hatch marks and grid lines for y axis.
		int pointWidth = 4;
		int numberYDivisions = 10;
		for (i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + labelPadding;
			int x1 = pointWidth + padding + labelPadding;
			int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
			if (scores.size() > 0) {
				g2.setColor(gridColor);
				g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y0);
				g2.setColor(Color.BLACK);
				String yLabel = ((int) ((minScore
						                         + (max - minScore) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
			}
			g2.drawLine(x0, y0, x1, y0);
		}

		// and for x axis
		for (i = 0; i < scores.size(); i++) {
			if (scores.size() > 1) {
				int x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1) + padding + labelPadding;
				int y0 = getHeight() - padding - labelPadding;
				int y1 = y0 - pointWidth;
				if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
					g2.setColor(gridColor);
					g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x0, padding);
					g2.setColor(Color.BLACK);
					String xLabel = i + "";
					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(xLabel);
					g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
				}
				g2.drawLine(x0, y0, x0, y1);
			}
		}

		// create x and y axes
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding,
				getHeight() - padding - labelPadding);

		Stroke oldStroke = g2.getStroke();
		g2.setColor(lineColor);
		g2.setStroke(GRAPH_STROKE);
		for (i = 0; i < graphPoints.size() - 1; i++) {
			int x1 = graphPoints.get(i).x;
			int y1 = graphPoints.get(i).y;
			int x2 = graphPoints.get(i + 1).x;
			int y2 = graphPoints.get(i + 1).y;
			g2.drawLine(x1, y1, x2, y2);
		}

		g2.setStroke(oldStroke);
		g2.setColor(pointColor);
		for (i = 0; i < graphPoints.size(); i++) {
			int x = graphPoints.get(i).x - pointWidth / 2;
			int y = graphPoints.get(i).y - pointWidth / 2;
			g2.fillOval(x, y, pointWidth, pointWidth);
		}
	}

	/**
	 *  Generates a spring.
	 * @return fitness of that spring.
	 */
	private double spring()
	{
		double return_val = last_fit;
		if (!STOP) {
			Individual fittest = local_genetic.spring();
			if (fittest.getFitness() > max) {
				max = fittest.getFitness();
				System.out.println("Generation: "
						                   + generation
						                   + " Fittest: "
						                   + fittest.getFitness()
						                   + " Max: " + max);
			}
			if (fittest.getFitness() < minScore)
				minScore = fittest.getFitness();

			++generation;
			if (scores.size() == 40)
				scores.remove();
			scores.add(fittest.getFitness());
			return_val = fittest.getFitness();
		}
		return return_val;
	}


	@Override
	public synchronized void run()
	{
		for(;true;){
			while (!this.STOP) {

				//For visualisation issue.
				for (long i = 0; i < 100000000; i++);

				if(this.spring() > 124.99)
					this.STOP = true;
				this.repaint();
			};
		}
	}
}