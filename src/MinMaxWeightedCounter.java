//label is mmwc
public class MinMaxWeightedCounter extends MinMaxAI {

	public MinMaxWeightedCounter(double[] d) {
		super(d);
	}

	public MinMaxWeightedCounter(int n) {
		super(n);
	}
	
	// Wedge of board
	public double innerEval(Othello o) {
		Piece[][] board = o.boardCopy();
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
				int i = a;
				int j = b;
				if (i >= 4) {
					i = 7-i;
				}
				if (j >= 4) {
					j = 7-j;
				}
				if (i < j) {
					int temp = i;
					i = j;
					j = temp;
				}
				double[] dna = getDna();
				if (i == 0) {
					out += coeff * dna[0];
				}
				else if (i == 1) {
					if (j == 0) {
						out += coeff * dna[1];
					}
					else {
						out += coeff * dna[2];
					}
				}
				else if (i == 2) {
					if (j == 0) {
						out += coeff * dna[3];
					}
					else if (j == 1){
						out += coeff * dna[4];
					}
					else {
						out += coeff * dna[5];
					}
				}
				else if (i == 3) {
					if (j == 0) {
						out += coeff * dna[6];
					}
					else if (j == 1){
						out += coeff * dna[7];
					}
					else if (j == 2){
						out += coeff * dna[8];
					}
					else {
						out += coeff * dna[9];
					}
				}
			}
		}
		return out;
	}
}
