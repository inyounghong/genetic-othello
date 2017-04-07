import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//for saving and retrieving AIs (label and DNA) and stats
public class Storer {
	
	public static AI[] readFile(String filename) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new FileReader(filename));
		
		// Get label and number of AI's in batch
		String label = sc.nextLine();
		int n = Integer.parseInt(sc.nextLine());
		
		AI[] players = new AI[n];
		
		// Read each line which represents the DNA for an AI, and add the AI to players
		for (int i = 0; i < n; i++) {
			String line = sc.nextLine();
			players[i] = Breeder.createAI(label, parseDna(line));
		}
		
		System.out.println("Generating batch of " + n + " " + label + " from file.");
		return players;
	}
	
	public static String getLabelFromFile(String filename) throws FileNotFoundException {
		Scanner sc = new Scanner(new FileReader(filename));
		String label = sc.nextLine();
		return label;
	}
	
	/*
	 * Writes a batch to a file. First line is the label. Second line is the number of players
	 * in the batch. Next lines are the DNA values separated by a space (ie "1.0 3.0 2.0").
	 */
	public static void writeFile(String filename, AI[] batch, String label) {
		try{
		    PrintWriter writer = new PrintWriter(filename, "UTF-8");
		    writer.println(label);
		    writer.println(batch.length);
		    for (AI p : batch) {
		    	writer.println(dnaToString(p.getDna()));
		    }
		    writer.close();
		} catch (IOException e) {
			System.out.println("Writing error");
		   // do something
		}
	}
	
	/* 
	 * Takes a string of DNA values separated by a space (ie "1.0 3.0 2.0") and returns 
	 * an array of doubles [1.0, 3.0, 2.0]
	 */
	private static double[] parseDna(String line) {
		String[] dna = line.split(" ");
		double[] doubleDna = new double[dna.length];
		for (int i = 0; i < dna.length; i++) {
			doubleDna[i] = Double.parseDouble(dna[i]);
		}
		return doubleDna;
	}
	
	// Takes the DNA array and converts to a string of values separated by a space
	private static String dnaToString(double[] dna) {
		String s = "";
		for (double d : dna) {
			s += d + " ";
		}
		return s.trim();
	}
}
