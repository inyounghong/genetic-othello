import java.util.ArrayList;
import java.util.Arrays;


public class Othello {
	
	private Piece[][] board; //must be 8x8
	private int turn;
	private boolean whiteTurn;

	public Othello() {
		board = new Piece[][] {
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.B,Piece.W,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.W,Piece.B,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE}
				};
		turn = 1;
		whiteTurn = false;
	}
	
	public Othello(Piece[][] b, int t, boolean w) {
		board = new Piece[8][];
		for (int i = 0; i < 8; i++) {
			board[i] = Arrays.copyOf(b[i], 8);
		}
		turn = t;
		whiteTurn = w;
	}
	
	private boolean isWhiteTurn() {
		return whiteTurn;
	}
	
	public Othello makeMove(String m) {
		//TODO
		return null;
	}
	
	public String[] getMoves() {
		return getMovesOfSide(whiteTurn);
	}
	
	private static Piece getOpposed(Piece p) {
		switch (p) {
		case B:
			return Piece.W;
		case W:
			return Piece.B;
		default:
			return Piece.NONE;
		}
	}
	
	private static boolean isOpposed(Piece p, Piece q) {
		return p == getOpposed(q);
	}
	
	private boolean isValidMove(int col, int row) {
		
	}
	
	private String[] getMovesOfSide(boolean w) {
		ArrayList<String> out = new ArrayList<String>();
		if (w) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (isValidMove(i,j)) {
						out.add(note(i,j));
					}
				}
			}
		}
		else {
			
		}
	}
	
	public State getState() {
		if () {
			
		}
		else {
			return State.NONE;
		}
	}

}
