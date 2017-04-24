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
		double[] grid300 = new double[] {-0.7355859774878493,
				6.343707135551983, 6.655929744156055, 0.5571086400825092,
				4.941674945540087, 5.146431202941262, 9.886470999566278,
				11.903565447753197, -4.264215548694784, -10.932541930936909,
				-9.215756639452593, -1.4844734031935651, -6.501550806478031,
				-9.74624487305145, -20.782849760809427, -9.083582967203034,
				1.6106806657737687, -8.812497356756161, 1.25874444848108,
				-5.251270260759695, -4.090422500051816, -3.4149106495686277,
				-10.53023169014652, 8.409133057578014, 1.2804389291823763,
				-4.759730336720631, -6.206228849864712, -1.705667483148663,
				1.500536329284777, 1.9528977218892791, -3.68561208216025,
				5.927168999312264, 6.160276306975322, -3.8908474556848316, 
				6.254526827054622, -4.893469231728091, -2.7225166067157454,
				-7.054123822258496, -1.1128648352678598, 1.9311355339860234,
				-0.30516262097490093, -7.788590031968496, 1.2391647236367007,
				0.7453562535066056, -0.6618834782465853, 0.5185566819664525,
				-5.5284813693305646, 2.940593537273701, 4.4328810650894015,
				-2.52639168496881, -9.952086556484378, -7.644982214095985,
				-4.107029331112275, -6.51480508125961, -11.938418804802216,
				6.967206389789689, 8.989474843004972, 2.6202834350692434,
				3.7173776462556036, 1.155619657221998, -0.32577958601399004,
				5.831287596071531, -3.014569907386391, 17.632896470653424};
		if (label.equals("pc")) {
			return new PC();
		}
		else {
			System.out.println("Should DNA be flat, g200, or g300 (mmwc), or grid300 (mmgc)?");
			String d = sc.nextLine().toLowerCase();
			double[] dna;
			if (d.equals("g200")) {
				dna = g200;
			}
			else if (d.equals("g300")) {
				dna = g300;
			}
			else if (d.equals("grid300")) {
				dna = grid300;
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
