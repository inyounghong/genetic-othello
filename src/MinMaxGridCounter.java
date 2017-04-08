//mmgc
public class MinMaxGridCounter extends MinMaxAI {

	public MinMaxGridCounter(double[] d) {
		super(d);
	}

	public MinMaxGridCounter(int n) {
		super(n);
	}
	
	public double innerEval(Othello o) {
		Piece[][] board = o.boardCopy();
		double[] dna = getDna();
		double out = 0.0;
		double coeff;
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				switch(board[a][b]) {
				case B:
					coeff = 1.0;
					break;
				case W:
					coeff = -1.0;
					break;
				default:
					coeff = 0.0;
					break;
				}
				out += coeff * dna[a*8+b];
			}
		}
		return out;
	}

}
