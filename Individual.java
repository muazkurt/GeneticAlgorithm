import java.util.Random;
//Individual class
class Individual {
	public	final static int geneLength = 13;
	public double			fitness = 0.0,
							Alelle_x1,
							Alelle_x2;
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
			x2 = Math.abs(rn.nextInt() % (int)(Math.pow(2.0, geneLength) - x1));
		x1 = grayCode(x1);
		x2 = grayCode(x2);


		String tempString = Integer.toBinaryString(x1);
		gene_x1 = new StringBuilder();
		for(int i = tempString.length(); i < geneLength; ++i)
			gene_x1 = gene_x1.append("0");
		gene_x1.append(tempString);
		
		
		tempString = Integer.toBinaryString(x2);
		gene_x2 = new StringBuilder();
		for(int i = tempString.length(); i < geneLength; ++i)
			gene_x2 = gene_x2.append("0");
		gene_x2.append(tempString);
		
		calculate_alleles();
		fitness = 0;
	}

	//Calculate fitness
	public double calcFitness() {
		fitness = 20*Alelle_x1*Alelle_x2 
					+ 16*Alelle_x2 
					- 2*Alelle_x1*Alelle_x1 
					- Alelle_x2*Alelle_x2 
					- ((Alelle_x1 + Alelle_x2) * (Alelle_x1 + Alelle_x2));
		return fitness;
	}

	public double getfitness() {
        return fitness;
	}

	public void mutate()
	{
		//Flip values at the mutation point
        /*System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("x1 before mutation: " + gene_x1);
        System.out.println("x2 before mutation: " + gene_x2);
		System.out.println("Mutation Point: " + point);
		*/
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
		calcFitness();
		//System.out.println(this);
		
		/*System.out.println("x1 before mutation: " + gene_x1);
        System.out.println("x2 before mutation: " + gene_x2);
		calculate_alleles();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		*/
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
			int point = rn.nextInt(Individual.geneLength - 1) + 1;
			
			String temp = new String(gene_x1.substring(point));
			gene_x1.replace(point, geneLength, other.gene_x1.substring(point));
			other.gene_x1.replace(point, geneLength, temp);
				
			
			temp = new String(gene_x2.substring(point));
			gene_x2.replace(point, geneLength, other.gene_x2.substring(point));
			other.gene_x2.replace(point, geneLength, temp);

			/*System.out.println("PRE	");		
			System.out.println("this");		
			System.out.println(this);		
			System.out.println("other");		
			System.out.println(other);
			System.out.println("~~~~~~~~~~~~~~~~");		*/


			/*System.out.println("POST	");		
			System.out.println("this");		
			System.out.println(this);		
			System.out.println("other");		
			System.out.println(other);
			System.out.println("------------");		*/
		} while (!(calculate_alleles() && other.calculate_alleles()));
		calcFitness();
		//System.out.println("OW YES GECELER");
	}

	private boolean calculate_alleles()
	{
		Alelle_x1 = ((double) inversegrayCode(
			Integer.parseInt(gene_x1.toString(), 2))
			) 
			* max_value / Math.pow(2.0, geneLength);
		Alelle_x2 = ((double) inversegrayCode(
			Integer.parseInt(gene_x2.toString(), 2))) 
			* max_value / Math.pow(2.0, geneLength);
		return !(Alelle_x1 + Alelle_x2 > max_value);
	}

	private int inversegrayCode(int n) 
    { 
        int inv = 0; 
      
        // Taking xor until n becomes zero 
        for ( ; n != 0 ; n = n >> 1) 
            inv ^= n; 
      
        return inv; 
	} 
	
	private int grayCode(int n) 
	{ 
		
		// Right Shift the number 
		// by 1 taking xor with 
		// original number 
		return n ^ (n >> 1); 
	} 

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return new String("Gene_X1 =\t" + gene_x1 + "\twith double value:\t" + Alelle_x1 
					+ "\nGene_X2 =\t" + gene_x2 + "\twith double value\t" + Alelle_x2
					+ "\nFitness =\t" + fitness + "\n");
	}
}

