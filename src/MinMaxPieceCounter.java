
public class MinMaxPieceCounter extends MinMaxAI {

	//d must be of length 5
	public MinMaxPieceCounter(double[] d) {
		super(d);
	}
	
	public MinMaxPieceCounter(int n) {
		super(n);
	}
	
	// Returns the difference of player's pieces and opponent's pieces
	public double innerEval(Othello o) {
		return o.count(Piece.B) - o.count(Piece.W);
	}

}
