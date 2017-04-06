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
	
	public Piece[][] boardCopy() {
		Piece[][] out = new Piece[8][];
		for (int i = 0; i < 8; i++) {
			out[i] = Arrays.copyOf(board[i], 8);
		}
		return out;
	}
	
	public boolean isWhiteTurn() {
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
		
		Piece[][] newBoard = boardCopy();
		
		if (m.equals("skip")) {
			return new Othello(newBoard, turn + 1, !whiteTurn); 
		}
		
		int col = unnote(m)[0];
		int row = unnote(m)[1];
		
		Piece currentPiece = getCurrentPiece();
		
		// Horizontal
		if (checkDirection(col, row, 1, 0, currentPiece)) {
			flip(col, row, 1, 0, newBoard);
		}
		if (checkDirection(col, row, -1, 0, currentPiece)) {
			flip(col, row, -1, 0, newBoard);
		}
		// Vertical
		if (checkDirection(col, row, 0, 1, currentPiece)) {
			flip(col, row, 0, 1, newBoard);
		}
		if (checkDirection(col, row, 0, -1, currentPiece)) {
			flip(col, row, 0, -1, newBoard);
		}
		// Diagonal
		if (checkDirection(col, row, 1, 1, currentPiece)) {
			flip(col, row, 1, 1, newBoard);
		}
		if (checkDirection(col, row, -1, -1, currentPiece)) {
			flip(col, row, -1, -1, newBoard);
		}
		if (checkDirection(col, row, 1, -1, currentPiece)) {
			flip(col, row, 1, -1, newBoard);
		}
		if (checkDirection(col, row, -1, 1, currentPiece)) {
			flip(col, row, -1, 1, newBoard);
		}
		
		return new Othello(newBoard, turn + 1, !whiteTurn);
	}
	
	// Flips pieces in the horz and vert direction
	private void flip(int col, int row, int horz, int vert, Piece[][] board) {
		Piece currentPiece = getCurrentPiece();
		
		while (board[col][row] != currentPiece) {
			board[col][row] = currentPiece;
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
		if (col < 7 && row < 7 && col > 0 && row > 0 && isOpposed(p, board[col][row])) {
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
		int[] dirs = new int[] {-1, 0 ,1};
		for (int i : dirs) {
			for (int j : dirs) {
				if ((i != 0 || j != 0) && checkDirection(col,row,i,j,p)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void printBoard() {
		for (Piece[] i : board) {
			for (Piece j : i) {
				switch(j) {
				case B:
					System.out.print("X");
					break;
				case W:
					System.out.print("O");
					break;
				default:
					System.out.print(".");
					break;
				}
			}
			System.out.println();
		}
		System.out.println();
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
		if (out.size() == 0) {
			out.add("skip");
		}
		return out.toArray(new String[] {});	
	}
	
	// Returns the count of the given piece p
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
		if (getMovesOfSide(true)[0].equals("skip") && getMovesOfSide(false)[0].equals("skip")) {
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
