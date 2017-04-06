import java.util.Arrays;


public class Othello {
	
	private Piece[][] board;
	private int turn;
	private boolean whiteTurn;

	public Othello() {
		board = new Piece[][] {
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.W,Piece.B,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.B,Piece.W,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE},
				{Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE,Piece.NONE}
				};
		turn = 1;
		whiteTurn = false;
	}
	
	public Othello(Piece[][] b, int t, boolean w) {
		board = new Piece[b.length][];
		for (int i = 0; i < b.length; i++) {
			board[i] = Arrays.copyOf(b[i], b[i].length);
		}
		turn = t;
		whiteTurn = w;
	}
	
	private boolean isWhiteTurn() {
		return whiteTurn;
	}
	
	private static String note(int col, int row) {
		return String.valueOf((char)(col+97))+String.valueOf(row+1);
	}
	
	private static int[] unnote(String s) {
		return new int[] {((int)s.charAt(0))-97, Integer.parseInt(s.substring(1))-1};
	}
	
	// Returns the piece that is currently being played
	private Piece getCurrentPiece() {
		if (whiteTurn) {
			return Piece.W;
		} else {
			return Piece.B;
		}
	}
	
	// Returns the resulting Othello after making a valid move m
	public Othello makeMove(String m) {
		
		int i = unnote(m)[0];
		int j = unnote(m)[1];
		
		Piece currentPiece = getCurrentPiece();
		Piece opposedPiece = getOpposed(currentPiece);
		
		

		// Checking right
		if (checkRight(i, j)) {
			
		}
		return null;
	}
	
	private int checkRight(int i, int j) {
		while (j+1 <= 7) {
			if (board[i][j+1] != opposedPiece) {
				return j;
			}
			j++;
		}	
		return j;
	}
	
	public String[] getMoves() {
		//TODO
		return null;
	}
	
	public State getState() {
		if (getMoves().length == 0) {
			
		}
		else {
			return State.NONE;
		}
	}

}
