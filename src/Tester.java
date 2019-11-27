package src;

/*import src.kurt.muaz.genetic.Abstract.Genetic;
import src.kurt.muaz.genetic.Abstract.Individual;
import src.kurt.muaz.genetic.RandomGenetic;
import src.kurt.muaz.genetic.RankGenetic;
import src.kurt.muaz.genetic.RouletteGenetic;

import java.util.Random;

public class Tester
{
	public static void main(String[] args) 
	{
		int generation = 0;
		//for (int j = 0; j < 20; j++)
		{
			Genetic demo = new RouletteGenetic();
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
}
*/
/*
import java.awt.Graphics2D;

import java.awt.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
public class Tester extends JComponent {

	Graphics2D g2;
	Polygon p = new Polygon();
	final double MAX_X = 2 * Math.PI; 
	final double SCALE = this.getWidth()/2 / MAX_X;

	public void paintComponent(Graphics g)
	{	 
		//w is x, and h is y (as in x/y values in a graph)
		int w = this.getWidth()/2;
		int h = this.getHeight()/2;

		Graphics2D g1 = (Graphics2D) g;
		g1.setStroke(new BasicStroke(2));
		g1.setColor(Color.black);
		g1.drawLine(0,h,w*2,h);
		g1.drawLine(w,0,w,h*2); 
//		g1.drawString("0", w, h + 13);

		g2 = (Graphics2D) g;
		w = this.getWidth();
		for (int x = 0; x <= w; ++x) {
			p.addPoint(x, h - 13);
		}
		g2.drawPolyline(p.xpoints, p.ypoints, p.npoints);
	}

	public static void main(String[] args) 
	{
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setTitle("Graphs");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);	
		Tester draw = new Tester();
		int w = draw.getWidth() / 2;
		for (int x = 0; x <= w; ++x) 
			draw.p.addPoint(x, draw.getHeight() / 2 -  (int)Math.round(draw.SCALE * Math.random()));
		draw.drawPolygon(draw.p.xpoints, draw.p.ypoints, draw.p.npoints);		
		draw.repaint();
		frame.add(draw); 
		frame.setVisible(true);
	}
}

*/
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rodrigo
 */
public class Tester extends JPanel {

	private int width = 800;
	private int heigth = 400;
	private int padding = 25;
	private int labelPadding = 25;
	private Color lineColor = new Color(44, 102, 230, 180);
	private Color pointColor = new Color(100, 100, 100, 180);
	private Color gridColor = new Color(200, 200, 200, 200);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private int pointWidth = 4;
	private int numberYDivisions = 10;
	private Queue<Double> scores;

	public Tester(Queue<Double> scores) {
		this.scores = scores;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.size() - 1);
		double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

		List<Point> graphPoints = new ArrayList<>();
		int i = 0;
		for (Double a_score : scores) {
			int x1 = (int) (i * xScale + padding + labelPadding);
			int y1 = (int) ((getMaxScore() - a_score) * yScale + padding);
			graphPoints.add(new Point(x1, y1));
			++i;
		}

		// draw white background
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding,
				getHeight() - 2 * padding - labelPadding);
		g2.setColor(Color.BLACK);

		// create hatch marks and grid lines for y axis.
		for (i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + labelPadding;
			int x1 = pointWidth + padding + labelPadding;
			int y0 = getHeight()
					- ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
			int y1 = y0;
			if (scores.size() > 0) {
				g2.setColor(gridColor);
				g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
				g2.setColor(Color.BLACK);
				String yLabel = ((int) ((getMinScore()
						+ (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
			}
			g2.drawLine(x0, y0, x1, y1);
		}

		// and for x axis
		for (i = 0; i < scores.size(); i++) {
			if (scores.size() > 1) {
				int x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1) + padding + labelPadding;
				int x1 = x0;
				int y0 = getHeight() - padding - labelPadding;
				int y1 = y0 - pointWidth;
				if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
					g2.setColor(gridColor);
					g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
					g2.setColor(Color.BLACK);
					String xLabel = i + "";
					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(xLabel);
					g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
				}
				g2.drawLine(x0, y0, x1, y1);
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
			int ovalW = pointWidth;
			int ovalH = pointWidth;
			g2.fillOval(x, y, ovalW, ovalH);
		}
	}

	// @Override
	// public Dimension getPreferredSize() {
	// return new Dimension(width, heigth);
	// }

	private double getMinScore() {
		double minScore = Double.MAX_VALUE;
		for (Double score : scores) {
			minScore = Math.min(minScore, score);
		}
		return minScore;
	}

	private double getMaxScore() {
		double maxScore = Double.MIN_VALUE;
		for (Double score : scores) {
			maxScore = Math.max(maxScore, score);
		}
		return maxScore;
	}

	public void setScores(Queue<Double> scores) {
		this.scores = scores;
		invalidate();
		this.repaint();
	}

	public Queue<Double> getScores() {
		return scores;
	}

	private static void createAndShowGui() {
		
    }
    
    public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setTitle("Graphs");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);	

		Queue<Double> scores = new LinkedList<>();
		Random random = new Random();
		int maxDataPoints = 40;
		int maxScore = 10;
		for (int i = 0; i < maxDataPoints; i++) {
			 scores.add((double) random.nextDouble() * maxScore);
			//scores.add((double) i);
		}
		Tester mainPanel = new Tester(scores);
		mainPanel.setPreferredSize(new Dimension(800, 600));
		frame.getContentPane().add(mainPanel);

		frame.setVisible(true);

		for(int x = 0; true; ++x)
		{
			scores.remove();
			scores.add((double) random.nextDouble() * maxScore);
/*			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/			mainPanel.setScores(scores);
		}
   }
}