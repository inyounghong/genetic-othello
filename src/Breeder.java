import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Breeder {
	
	private static double PERCENT_RETAINED = 0.5;
	private static double MUTATION_FACTOR = 0.75;

	/*
	 * Returns an array of the next generation of AI from the given batch array
	 */
	public static AI[] newGen (String label, AI[] batch) {
		
		int nTotal = batch.length;
		int nSelected = nTotal/2;	// Arbitrary number of AI's to select (rest are culled)
		
		AI[] newGen = new AI[nTotal];
		
		// Sort batch array by total stats
	    Arrays.sort(batch, new Comparator<AI>() {
	        @Override
	        public int compare(AI p1, AI p2) {
	            return Double.compare(p1.getStats()[2], p2.getStats()[2]); 
	        }
	    });
	    

	    ArrayList<AI> batchList = new ArrayList<AI>(Arrays.asList(batch));
	    
	    // Keep the AI with best T
	    newGen[0] = createAI(label, batchList.remove(0).getDna());
		
	    int ptr = 1;
	    
	    while ((double)ptr/(double)nTotal < PERCENT_RETAINED) {
	    	double sum = sumArray(batchList);
	    	
	    	for (AI a : batchList) {
	    		if (Math.random() < a.getStats()[2]/sum) {
	    			newGen[ptr] = createAI(label, a.getDna());
	    			batchList.remove(a);
	    			ptr++;
	    			break;
	    		} else {
	    			sum -= a.getStats()[2];
	    		}
	    	}
	    }
	    
	    // Breed the nSelected AI's proportionally by weighted stats to generate nNew AI's
	    while (ptr < nTotal) {
	    	int r1 = (int) (Math.random() * ptr);
	    	int r2 = (int) (Math.random() * ptr);
	    	newGen[ptr] = combine(label, newGen[r1], newGen[r2]);
	    	ptr++;
	    }
	    
		return newGen;
	}
	
	private static int labelLen(String label) {
		if (label.equals("mmpc") || label.equals("mm")) {
			return 0;
		}
		else if (label.equals("mmwc")) {
			return 10;
		}
		return -1;
	}
	
	public static AI createAI(String label, double[] dna) {
		if (label.equals("mmpc")) {
			return new MinMaxPieceCounter(dna);
		} else if (label.equals("mmwc")) {
			return new MinMaxWeightedCounter(dna);
		} else if (label.equals("mm")) {
			return new MinMaxAI(dna);
		} else {
			return new AI(dna);
		}
	}
	
	public static AI createAI(String label) {
		int n = labelLen(label);
		if (label.equals("mmpc")) {
			return new MinMaxPieceCounter(n);
		} else if (label.equals("mmwc")) {
			return new MinMaxWeightedCounter(n);
		} else if (label.equals("mm")) {
			return new MinMaxAI(n);
		} else {
			return new AI(n);
		}
	}
	
	private static double sumArray(ArrayList<AI> batch) {
		double sum = 0;
		for (AI a : batch) {
			sum += a.getStats()[2];
		}
		return sum;
	}
	
	// Returns the weighted stats of the given AI
//	private static double weightedStats(AI p) {
//		return W_WEIGHT * p.getStats()[0] + B_WEIGHT * p.getStats()[1] + T_WEIGHT * p.getStats()[2];
//	}
	
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
			newDna[r] = (newDna[r] + (Math.random() - 0.5)) * (Math.random() * 0.5 + 0.75);
		}
		
		// Return new AI
		return createAI(label, newDna);
	}
}
