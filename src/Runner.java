import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Runner {
	
	private static int BATCH_SIZE = 15; // arbitrary batch size for now
	private static boolean PRINT_BOARD   = false;
	private static boolean PRINT_DNA 	 = true;
	private static boolean PRINT_STATS 	 = true;
	private static boolean PRINT_OUTCOME = false;
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String args[]) {
		
		System.out.println("Play or Generate or File?");
		String s = sc.nextLine().toLowerCase();
		
		// Play against computer
		if (s.equals("play") || s.equals("p")) {
			playPC();
		}
		// Generate random batch
		else if (s.equals("generate") || s.equals("g")) {
			generate();
		}
		// Get batch from a text file
		else if (s.equals("file") || s.equals("f")) {
			file();
		}
		
	}
	
	// Runs a full tournament with the given batch of AI's and writes to file
	private static void runFullTournament(AI[] batch, String label) {
		
		// Determine how many times to run the tournament
		System.out.println("Run tournament how many times?");
		int runs = sc.nextInt();
		
		// Run through all tournaments
		while (runs--> 0) {
			System.out.println("Starting round of tournament " + runs);
			Tournament.runTournament(batch, PRINT_BOARD, PRINT_STATS, PRINT_OUTCOME);

			AI[] newGen = Breeder.newGen(label, batch);
			Storer.writeFile("record.txt", newGen, label);
			batch = newGen;
			
			// Print DNA at end of game
			if (PRINT_DNA) {
				for (AI p : batch) {
					for (double d : p.getDna()) {
						System.out.print(Math.round(d * 100.0)/100.0);
						System.out.print(" ");
					}
					System.out.println();
				}
			}
		}
	}
	
	// Runs file option
	private static void file() {
		AI[] batch = new AI[BATCH_SIZE];
//		System.out.println("Read from which file?");
//		String filename = sc.nextLine();
		String filename = "record.txt";
		
		try {
			// Load batch from filename
			String label = Storer.getLabelFromFile(filename);
			batch = Storer.readFile(filename);
			
			// Run full tournament
			runFullTournament(batch, label);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// User plays against computer
	private static void playPC() {
		
		Player p1, p2;
		
		// Label
		System.out.println("Label?");
		String label = sc.nextLine();
		
		// Determine white or black
		System.out.println("White or Black?");
		String s = sc.nextLine().toLowerCase();
		
//		double[] rein = new double[] {1, 9, 5, 3, 3}; 
		
		if (s.equals("white") || s.equals("w")) {
			p1 = Breeder.createAI(label);
			p2 = new PC();
		} else {
			p1 = new PC();
			p2 = Breeder.createAI(label);
		}
		
		// Determine outcome of game
		Tournament.play(p1, p2, PRINT_BOARD);
	}
	
	// Returns a randomly generated batch of AI's
	private static void generate() {
		
		AI[] batch = new AI[BATCH_SIZE];
		
		// Label
		System.out.println("Label?");
		String label = sc.nextLine();
		
		for (int i = 0; i < BATCH_SIZE; i++) {
			batch[i] = Breeder.createAI(label);
		}
		
		runFullTournament(batch, label);
	}

}
