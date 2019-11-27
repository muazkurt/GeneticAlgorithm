package kurt.muaz.genetic.Abstract;

import java.util.Random;
//Individual class
public class Individual {
	private final static int geneLength = 21;
	private double			fitness = 0.0,
							Allele_x1,
							Allele_x2;
	private final static double max_value = 5.0;
	private StringBuilder	gene_x1,
							gene_x2;

	 /**
	  * (0 + k * 5 / 2^12)
	  * 011 10010 and  100 11110  and the crossover point is between bits 3 and 4 (where bits are numbered from left to right starting at 1), then the children are   
	  * 011 11110 and  100 10010
	  */
	public Individual()
	{
		Random rn = new Random();
		int x1 = Math.abs(rn.nextInt() % (int)Math.pow(2.0, geneLength)),
			x2 = grayCode( Math.abs(rn.nextInt() % (int)(Math.pow(2.0, geneLength) - x1)));
		x1 = grayCode(x1);


		String tempString = Integer.toBinaryString(x1);
		gene_x1 = new StringBuilder();
		for(int i = tempString.length(); i < geneLength; ++i)
			gene_x1.append("0");
		gene_x1.append(tempString);
		
		
		tempString = Integer.toBinaryString(x2);
		gene_x2 = new StringBuilder();
		for(int i = tempString.length(); i < geneLength; ++i)
			gene_x2.append("0");
		gene_x2.append(tempString);

		calculate_alleles();
	}

	//Calculate fitness
	private double calcFitness() {
		fitness = 20* Allele_x1 * Allele_x2
					+ 16* Allele_x2
					- 2* Allele_x1 * Allele_x1
					- Allele_x2 * Allele_x2
					- ((Allele_x1 + Allele_x2) * (Allele_x1 + Allele_x2));
		return fitness;
	}

	public double getFitness() {return fitness;}

	public void mutate()
	{
		Random rn = new Random();
		StringBuilder	X1_LOCAL = new StringBuilder(gene_x1),
						X2_LOCAL = new StringBuilder(gene_x2);
		do {
			gene_x1 = X1_LOCAL;
			gene_x1 = X2_LOCAL;
			int point = rn.nextInt(Individual.geneLength - 1) + 1;
			gene_x1.setCharAt(point, (gene_x1.charAt(point) == '0' ? '1' : '0'));
			gene_x2.setCharAt(point, (gene_x2.charAt(point) == '0' ? '1' : '0'));
		} while(!calculate_alleles());
	}

	public void crossover(Individual other)
	{
		Random rn = new Random();
		//Select a random crossover point
		StringBuilder	X1_LOCAL = new StringBuilder(gene_x1),
						X2_LOCAL = new StringBuilder(gene_x2),
						X1_OTHER = new StringBuilder(other.gene_x1),
						X2_OTHER = new StringBuilder(other.gene_x2);
		do {
			gene_x1 = X1_LOCAL;
			gene_x2 = X2_LOCAL;
			other.gene_x1 = X1_OTHER;
			other.gene_x2 = X2_OTHER;
			int point_begin = rn.nextInt(Individual.geneLength);
			int point_end = point_begin + rn.nextInt(Individual.geneLength - point_begin);

			String temp = gene_x1.substring(point_begin, point_end);
			gene_x1.replace(point_begin, point_end, other.gene_x1.substring(point_begin, point_end));
			other.gene_x1.replace(point_begin, point_end, temp);


			temp = gene_x2.substring(point_begin, point_end);
			gene_x2.replace(point_begin, point_end, other.gene_x2.substring(point_begin, point_end));
			other.gene_x2.replace(point_begin, point_end, temp);
			if(gene_x1.length() != gene_x2.length() || gene_x1.length() != other.gene_x2.length() || other.gene_x1.length() != other.gene_x2.length())
				System.exit(-1);
		} while (!(calculate_alleles() && other.calculate_alleles()));
		//System.out.println(this);
		//System.out.println(other);
		//System.out.println("OW YES GECELER");
	}

	private boolean calculate_alleles()
	{
		Allele_x1 = ((double) inverseCode(Integer.parseInt(gene_x1.toString(), 2)))
				            * max_value / Math.pow(2.0, geneLength);
		Allele_x2 = ((double) inverseCode(Integer.parseInt(gene_x2.toString(), 2)))
				            * max_value / Math.pow(2.0, geneLength);
		calcFitness();
		return !(Allele_x1 + Allele_x2 > max_value);
	}

	private int inverseCode(int n)
    { 
        int inv = 0;
        while (n != 0)
        {
	        inv ^= n;
	        n = n >> 1;
        }
        return inv; 
	} 
	
	private int grayCode(int n) {return n ^ (n >> 1);}

	@Override
	public String toString() {
		return "Gene_X1 =\t" + gene_x1 + "\twith double value:\t" + Allele_x1
					+ "\nGene_X2 =\t" + gene_x2 + "\twith double value\t" + Allele_x2
					+ "\nFitness =\t" + fitness + "\n";
	}
}

