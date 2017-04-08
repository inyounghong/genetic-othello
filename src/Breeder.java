import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Breeder {
	
	private static double PERCENT_RETAINED = 0.5;
	private static double MUTATION_FACTOR = 0.9;
	private static double SELECTION_BIAS = 4.0;

	/*
	 * Returns a sorted array of the next generation AI's
	 */
	public static AI[] newGen (String label, AI[] batch) {
		
		int nTotal = batch.length;
		
		AI[] newGen = new AI[nTotal];
		
		// Sort batch array by total stats
	    Arrays.sort(batch, new Comparator<AI>() {
	        @Override
	        public int compare(AI p1, AI p2) {
	            return Double.compare(p2.getStats()[2], p1.getStats()[2]); 
	        }
	    });

	    ArrayList<AI> batchList = new ArrayList<AI>(Arrays.asList(batch));
	    
	    // Keep the AI with best T
	    newGen[0] = createAI(label, batchList.remove(0).getDna());
		
	    // Select up to PERCENT_RETAINED AI's with proportional weighting according to T
	    int ptr = 1;
	    while ((double)ptr/(double)nTotal < PERCENT_RETAINED) {
	    	double sum = sumArray(batchList, SELECTION_BIAS);
	    	
	    	for (AI a : batchList) {
	    		if (Math.random() < Math.pow(a.getStats()[2], SELECTION_BIAS)/sum) {
	    			newGen[ptr] = createAI(label, a.getDna());
	    			batchList.remove(a);
	    			ptr++;
	    			break;
	    		} else {
	    			sum -= Math.pow(a.getStats()[2], SELECTION_BIAS);
	    		}
	    	}
	    }
	    
	    // Breed the selected AIs generate nNew AI's
	    while (ptr < nTotal) {
	    	int r1 = (int) (Math.random() * ptr);
	    	int r2 = (int) (Math.random() * ptr);
	    	newGen[ptr] = combine(label, newGen[r1], newGen[r2]);
	    	ptr++;
	    }
	    
		return newGen;
	}
	
	// Returns the DNA length for the given AI label
	private static int labelLen(String label) {
		if (label.equals("mmpc") || label.equals("mm")) {
			return 0;
		}
		else if (label.equals("mmwc")) {
			return 10;
		}
		else if (label.equals("mmgc")){
			return 64;
		}
		return -1;
	}
	
	// Returns an AI with the given DNA
	public static AI createAI(String label, double[] dna) {
		if (label.equals("mmpc")) {
			return new MinMaxPieceCounter(dna);
		} else if (label.equals("mmwc")) {
			return new MinMaxWeightedCounter(dna);
		} else if (label.equals("mm")) {
			return new MinMaxAI(dna);
		}
		else if (label.equals("mmgc")){
			return new MinMaxGridCounter(dna);
		} else {
			return new AI(dna);
		}
	}
	
	// Returns an AI with random DNA
	public static AI createAI(String label) {
		int n = labelLen(label);
		if (label.equals("mmpc")) {
			return new MinMaxPieceCounter(n);
		} else if (label.equals("mmwc")) {
			return new MinMaxWeightedCounter(n);
		} else if (label.equals("mm")) {
			return new MinMaxAI(n);
		} else if (label.equals("mmgc")){
			return new MinMaxGridCounter(n);
		} else {
			return new AI(n);
		}
	}
	
	private static double sumArray(ArrayList<AI> batch, double w) {
		double sum = 0;
		for (AI a : batch) {
			sum += Math.pow(a.getStats()[2], w);
		}
		return sum;
	}
	
	
	/*
	 * Returns a new AI from the random combination of two AI's p1 and p2
	 */
	private static AI combine(String label, AI p1, AI p2) {
		
		int dnaLen = p1.getDnaLen();
		double[] dna1 = p1.getDna();
		double[] dna2 = p2.getDna();
		double[] newDna = new double[dnaLen];
		
		// Loop through DNA and randomly pick from p1 or p2's DNA
		for (int i = 0; i < dnaLen; i++) {
			if (Math.random() < 0.5) {
				newDna[i] = dna1[i];
			} else {
				newDna[i] = dna2[i];
			}
		}
		
		// Mutations
		while (Math.random() < MUTATION_FACTOR) {
			int r = (int) (Math.random() * dnaLen);
			if (Math.random() < 0.75) {
				// new = (old + [-2,2]) * [0.75, 1.25]
				newDna[r] = (newDna[r] + 4 * (Math.random() - 0.5)) * (Math.random() * 0.5 + 0.75);
			}
			else {
				// new = [-10,10]
				newDna[r] = Math.random() * 20 - 10;
			}
		}
		
		// Return new AI
		return createAI(label, newDna);
	}
}
