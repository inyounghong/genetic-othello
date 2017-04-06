
public class MinMaxPieceCounter extends MinMaxAI {

	//d must be of length 5
	public MinMaxPieceCounter(double[] d) {
		super(d);
	}
	
	public MinMaxPieceCounter(int n) {
		super(n);
	}
	
	// Evaluates a chess according to evaluation function
	public double innerEval(Othello o) {
		double[] dna = getDna();
		double out = 0.0;
//		for (Piece[] i : c.getBoard()) {
//			for (Piece j : i) {
//				switch (j) {
//					case WPAWN: case WPAWNM:
//						out += dna[0];
//						break;
//					case WQUEEN:
//						out += dna[1];
//						break;
//					case WROOK: case WROOKM:
//						out += dna[2];
//						break;
//					case WBISHOP:
//						out += dna[3];
//						break;
//					case WKNIGHT:
//						out += dna[4];
//						break;
//					case BPAWN: case BPAWNM:
//						out -= dna[0];
//						break;
//					case BQUEEN:
//						out -= dna[1];
//						break;
//					case BROOK: case BROOKM:
//						out -= dna[2];
//						break;
//					case BBISHOP:
//						out -= dna[3];
//						break;
//					case BKNIGHT:
//						out -= dna[4];
//						break;
//					default:
//						break;
//				}
//			}
//		}
		return out;
	}

}
