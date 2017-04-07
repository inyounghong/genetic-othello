import java.util.Arrays;
import java.util.Random;

public class AI implements Player{
	
	private double[] dna;
	private int dnaLen;
	private double[] stats;
	
	// Initialize AI from given DNA
	public AI (double[] d){
		dna = Arrays.copyOf(d, d.length);
		dnaLen = d.length;
		stats = new double[] {0, 0, 0};
	}
	
	// Initialize AI with a random DNA of length n
	public AI (int n) {
		
		dna = new double[n];
		dnaLen = n;
		stats = new double[] {0, 0, 0};
		
		Random r = new Random();
		double min = -10;
		double max = 10;
		
		for (int i = 0; i < n; i++) {
			dna[i] =  min + (max - min) * r.nextDouble();
		}
	}
	
	// Picks first possible valid move
	public String pickMove(Othello o){
		return o.getMoves()[0];
	}
	
	public double[] getDna() {
		return Arrays.copyOf(dna, dna.length);
	}
	
	public int getDnaLen() {
		return dnaLen;
	}
	
	public double[] getStats() {
		return Arrays.copyOf(stats, stats.length);
	}
	
	public void addWhiteWin() {
		stats[0] += 1;
		stats[2] += 1;
	}
	
	public void addBlackWin() {
		stats[1] += 1;
		stats[2] += 1;
	}
	
	public void addWhiteTie() {
		stats[0] += 0.5;
		stats[2] += 0.5;
	}
	
	public void addBlackTie() {
		stats[1] += 0.5;
		stats[2] += 0.5;
	}
	
	public void setStats(double[] s) {
		stats = s;
	}
	
}
