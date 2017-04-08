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
	
	private static Player getPlayer(String label) {
		double[] g200 = new double[] {26.5, 3.1, -11.5, 7.3, -6.0, 2.9, -0.1, -6.2, 1.6, -4.5}; //mmwc
		double[] flat = new double[] {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,};
		double[] g300 = new double[] {69.59030198380006,
				-1.9827965467594504, -0.8298766286877772, 1.378402685579887,
				-5.360802561826524, -3.253144422841733, 0.6043467241542899,
				-7.699428493002588, -1.1513433597843223, -1.6699453630993306};
		if (label.equals("pc")) {
			return new PC();
		}
		else {
			System.out.println("Should DNA be flat or g200 (mmwc) or g300 (mmwc)?");
			String d = sc.nextLine().toLowerCase();
			double[] dna;
			if (d.equals("g200")) {
				dna = g200;
			}
			else if (d.equals("g300")) {
				dna = g300;
			}
			else {
				dna = flat;
			}
			return Breeder.createAI(label, dna);
		}
	}
	
	// User plays against computer
	private static void playPC() {
		
		Player p1, p2;
		
		// Label
		System.out.println("1st player label?");
		String label1 = sc.nextLine().toLowerCase();
		p1 = getPlayer(label1);
		
		System.out.println("2nd player label?");
		String label2 = sc.nextLine().toLowerCase();
		p2 = getPlayer(label2);
		
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
