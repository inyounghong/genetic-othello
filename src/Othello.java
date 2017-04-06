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
		board = b;
		turn = t;
		whiteTurn = w;
	}
	
	private static Piece[][] boardCopy(Piece[][] b) {
		Piece[][] out = new Piece[8][];
		for (int i = 0; i < 8; i++) {
			out[i] = Arrays.copyOf(b[i], 8);
		}
		return out;
	}
	
	private boolean isWhiteTurn() {
		return whiteTurn;
	}
	
	public String[] getMoves() {
		return getMovesOfSide(whiteTurn);
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
		
		int col = unnote(m)[0];
		int row = unnote(m)[1];
		
		Piece currentPiece = getCurrentPiece();
		
		// Horizontal
		if (checkDirection(col, row, 1, 0, currentPiece)) {
			flip(col, row, 1, 0);
		}
		if (checkDirection(col, row, -1, 0, currentPiece)) {
			flip(col, row, -1, 0);
		}
		// Vertical
		if (checkDirection(col, row, 0, 1, currentPiece)) {
			flip(col, row, 0, 1);
		}
		if (checkDirection(col, row, 0, -1, currentPiece)) {
			flip(col, row, 0, -1);
		}
		// Diagonal
		if (checkDirection(col, row, 1, 1, currentPiece)) {
			flip(col, row, 1, 1);
		}
		if (checkDirection(col, row, -1, -1, currentPiece)) {
			flip(col, row, -1, -1);
		}
		if (checkDirection(col, row, 1, -1, currentPiece)) {
			flip(col, row, 1, -1);
		}
		if (checkDirection(col, row, -1, 1, currentPiece)) {
			flip(col, row, -1, 1);
		}
		
		return null;
	}
	
	private void flip(int col, int row, int horz, int vert) {
		Piece currentPiece = getCurrentPiece();
		
		while (board[row][col] != currentPiece) {
			board[row][col] = currentPiece;
			col += horz;
			row += vert;
		}
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
	
	private boolean checkDirection(int col, int row, int horz, int vert, Piece p) {
		col += horz;
		row += vert;
		if (isOpposed(p, board[col][row])) {
			while (col < 7 && row < 7 && col > 0 && row > 0) {
				col += horz;
				row += vert;
				if (board[col][row] == p) {
					return true;
				}
				else if (board[col][row] == Piece.NONE) {
					return false;
				}
			}
		}
		return false;
	}
	
	private boolean isValidMove(int col, int row, boolean w) {
		if (board[col][row] != Piece.NONE){ 
			return false;
		}
		Piece p;
		if (w) {
			p = Piece.W;
		}
		else {
			p = Piece.B;
		}
		if (col > 1) {
			if (row > 1 && checkDirection(col,row,-1,-1,p)) {
				return true;
			}
			if (row < 6 && checkDirection(col,row,-1,1,p)) {
				return true;
			}
			if (checkDirection(col,row,-1,0,p)) {
				return true;
			}
		}
		if (col < 6) {
			if (row > 1 && checkDirection(col,row,1,-1,p)) {
				return true;
			}
			if (row < 6 && checkDirection(col,row,1,1,p)) {
				return true;
			}
			if (checkDirection(col,row,1,0,p)) {
				return true;
			}
		}
		if (row < 6 && checkDirection(col,row,0,1,p)) {
			return true;
		}
		if (row > 1 && checkDirection(col,row,0,-1,p)) {
			return true;
		}
		return false;
	}
	
	private String[] getMovesOfSide(boolean w) {
		ArrayList<String> out = new ArrayList<String>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (isValidMove(i,j,w)) {
					out.add(note(i,j));
				}
			}
		}
		return out.toArray(new String[] {});	
	}
	
	public int count(Piece p) {
		int out = 0;
		for (Piece[] i : board) {
			for (Piece j : i) {
				if (j == p) {
					out++;
				}
			}
		}
		return out;
	}
	
	public State getState() {
		if (getMovesOfSide(true).length == 0 && getMovesOfSide(false).length == 0) {
			int wc = count(Piece.W);
			int bc = count(Piece.B);
			if (wc > bc) {
				return State.W;
			}
			else if (bc > wc) {
				return State.B;
			}
			else {
				return State.DRAW;
			}
		}
		else {
			return State.NONE;
		}
	}
	
	
	private static String note(int col, int row) {
		return String.valueOf((char)(col+97))+String.valueOf(row+1);
	}
	
	private static int[] unnote(String s) {
		return new int[] {((int)s.charAt(0))-97, Integer.parseInt(s.substring(1))-1};
	}

}
