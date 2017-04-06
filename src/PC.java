import java.util.Scanner;

public class PC implements Player {
	
	public PC() {
		
	}

	/* Prompts user for a move and returns a valid move */
	public String pickMove(Othello o){
		
//		System.out.println("All valid moves: ");
//		for (String[] m :Chess.validMoves(c)) {
//			System.out.print(Arrays.asList(m).toString() + ", ");
//		}
		
		// Get user input for next move
		Scanner sc = new Scanner(System.in);
		System.out.println("Your move:");
		String move = sc.nextLine().toLowerCase();
		
		// Return if move is valid
		if (isValidMove(o, move)) {
			return move;
		}
		
		// If move is not valid, re-prompt for next move
		System.out.println("Move is not valid. Please pick again.");
		return pickMove(o);
	}
	
	/* Returns whether given move is in the list of possible moves for a chess c */
	private boolean isValidMove(Othello o, String move) {
		for (String m : o.getValidMoves()) {
			if (m.equals(move)) {
				return true;
			}
		}
		return false;
	}
}
