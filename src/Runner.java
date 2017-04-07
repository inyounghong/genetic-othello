import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Runner {
	
	private static int BATCH_SIZE = 10; // arbitrary batch size for now
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String args[]) {
		
//		playPC();
		
		System.out.println("Play or Generate or File?");
		String s = sc.nextLine().toLowerCase();
		
		// Play against computer
		if (s.equals("play") || s.equals("p")) {
			playPC();
			return;
		}
		
		// Label
		System.out.println("Label?");
		String label = sc.nextLine();
		
		AI[] batch = new AI[BATCH_SIZE];
		
		// Generate random batch
		if (s.equals("generate") || s.equals("g")) {
			batch = generate(label);
		}
		
		// Get batch from a text file
		else if (s.equals("file") || s.equals("f")) {
//			System.out.println("Read from which file?");
//			String filename = sc.nextLine();
			String filename = "start.txt";
			try {
				batch = Storer.readFile(filename);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		// Determine how many times to run the tournament
		System.out.println("Run tournament how many times?");
		int runs = sc.nextInt();
		while (runs--> 0) {
			System.out.println("Starting round of tournament " + runs);
			Tournament.runTournament(batch, true);

			AI[] newGen = Breeder.newGen(label, batch);
			Storer.writeFile("record.txt", newGen, "AI");
			batch = newGen;
			
			System.out.println("New batch:");
			System.out.println(batch.length);
			for (AI p : batch) {
				for (double d : p.getDna()) {
					System.out.print(d);
					System.out.print(" ");
				}
				System.out.println();
			}
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
		
		double[] rein = new double[] {1, 9, 5, 3, 3}; 
		
		if (s.equals("white") || s.equals("w")) {
			p1 = Breeder.createAI(label, rein);
			p2 = new PC();
		} else {
			p1 = new PC();
			p2 = Breeder.createAI(label, rein);
		}
		
		// Determine outcome of game
		State result = Tournament.play(p1, p2, true);
		if (result == State.W) {
			System.out.println("White wins!");
		} else if (result == State.B) {
			System.out.println("Black wins!");
		} else {
			System.out.println("Draw!");
		}
	}
	
	// Returns a randomly generated batch of AI's
	private static AI[] generate(String label) {
		AI[] batch = new AI[BATCH_SIZE];
		for (int i = 0; i < BATCH_SIZE; i++) {
			batch[i] = Breeder.createAI(label);
		}
		return batch;
	}

}
