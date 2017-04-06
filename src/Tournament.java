import java.util.Arrays;

public class Tournament {

	/* takes batch of players and round robins them, returning white win%, black
	 * win %, and total win % for each
	 * 
	 * Returns array of stats array [white win, black win, total win] 
	 * Note: Currently not storing as percent cuz annoying to code
	 */

	public static void runTournament(AI[] batch, boolean print) {
		
		// Play each player against every other player
		for (int i = 0; i < batch.length; i++) {
			for (int j = i+1; j < batch.length; j++) {
				
				// Play twice, once as black and once as white
				State result1 = play(batch[i], batch[j], print);
				State result2 = play(batch[j], batch[i], print);

				// Add results to stats
				if (result1 == State.W) {
					batch[i].addWhiteWin();
				} else if (result1 == State.B){
					batch[j].addBlackWin();
				} else {
					batch[i].addWhiteTie();
					batch[j].addBlackTie();
				}
				
				if (result2 == State.B) {
					batch[i].addBlackWin();
				} else if (result2 == State.W) {
					batch[j].addWhiteWin();
				} else {
					batch[i].addBlackTie();
					batch[j].addWhiteTie();
				}
			}
		}
		
		// Print stats
		for (AI p : batch) {
			for (double d: p.getStats()) {
				System.out.print(d + ", ");
			}
			System.out.println();
		}
	}
	
	
	/* 
	 * Returns game state after playing p1 as black against p2 as white
	 */
	public static State play(Player p1, Player p2, boolean print) {
		
		Othello o = new Othello();
		
		// While game has not ended
		while (o.getState() == State.NONE) { 
		
			// p1 moves
			System.out.println(Arrays.asList(o.getMoves()));
			
			String m1 = p1.pickMove(o);
			o = o.makeMove(m1);
			if (print) {
				System.out.println("Black moves: " + m1);
				o.printBoard();
			}	
			
			// Break if p1 wins/ties
			if (o.getState() != State.NONE) { 
				break;
			}
			
			// p2 moves
			System.out.println(Arrays.asList(o.getMoves()));
			
			String m2 = p2.pickMove(o);
			o = o.makeMove(m2);
			if (print) {
				System.out.println("White moves: " + m2);
				o.printBoard();
			}
		}
		System.out.println("Game State: " + o.getState());
		return o.getState();
 	}
	
}
