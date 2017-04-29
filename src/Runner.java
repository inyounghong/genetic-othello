import java.io.FileNotFoundException;
import java.util.Scanner;

public class Runner {
	
	private static int BATCH_SIZE = 20; // arbitrary batch size for now
	private static boolean PRINT_BOARD   = false;
	private static boolean PRINT_OUTCOME = false;
	private static boolean PRINT_STATS 	 = true;
	private static boolean PRINT_DNA 	 = true;
	
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
	
	// Pretty print DNA
	private static void printDna(AI[] batch) {
		for (int i = 0; i < batch.length; i++) {
			System.out.print("(" + i + ") ");
			for (double d : batch[i].getDna()) {
				System.out.print(Math.round(d * 10.0)/10.0);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	// Pretty print Stats
	private static void printStats(AI[] batch) {
		for (int i = 0; i < batch.length; i++) {
			System.out.print("(" + i + ") ");
			for (double d: batch[i].getStats()) {
				System.out.print(d + " ");
			}
			System.out.println();
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
			
			Tournament.runTournament(batch, PRINT_BOARD, PRINT_OUTCOME);
			
			// Print DNA at end of round
			if (PRINT_DNA) {
				printDna(batch);
			}
			
			// Print stats at the end of each tournament round
			if (PRINT_STATS) {
				printStats(batch);
			}
			
			// Write to record
			Storer.writeFile("record.txt", batch, label);

			if (runs != 0) { //only new if not the end
				AI[] newGen = Breeder.newGen(label, batch);
				batch = newGen;
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
	
	// Gets player according to text (pc or filename)
	private static Player getPlayer(String text) {
		// Play against user
		if (text.equals("pc")) {
			return new PC();
		}
		
		AI playerAI = null;
		try {
			playerAI = Storer.readPlayerFromFile(text + ".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return playerAI;
	}
	
	// User plays against computer
	private static void playPC() {
		
		Player p1, p2;
		
		// Label
		System.out.println("1st player? (pc or filename)");
		String text1 = sc.nextLine().toLowerCase();
		p1 = getPlayer(text1);
		
		System.out.println("2nd player? (pc or filename)");
		String text2 = sc.nextLine().toLowerCase();
		p2 = getPlayer(text2);
		
		// Determine outcome of game
		Tournament.play(p1, p2, true);
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
