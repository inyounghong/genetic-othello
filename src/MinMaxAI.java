//label is mm
public class MinMaxAI extends AI {

	private int DEPTH = 7; 		// Depth for alpha-beta search

	public MinMaxAI(double[] d) {
		super(d);
	}
	
	public MinMaxAI(int n) {
		super(n);
	}
	
	public void setDepth(int d) {
		DEPTH = d;
	}

	public String pickMove(Othello o){	
		String bestMove = alphaBeta(o, -Double.MAX_VALUE, Double.MAX_VALUE, DEPTH, !o.isWhiteTurn()).second;
		return bestMove;
	}

	// Evaluates a othello according to evaluation function
	public double eval(Othello o) {
		State s = o.getState();
		switch(s) {
			case W:
				return -Double.MAX_VALUE;
			case B:
				return Double.MAX_VALUE;
			case DRAW:
				return 0;
			default:
				return innerEval(o);
		}
	}
	
	public double innerEval(Othello o) {
		return 0.0;
	}
	
	private Pair<String[], Othello[]> getChildren(Othello o) {
		String[] moves;
    	Othello[] os;
		moves = o.getMoves();
		os = new Othello[moves.length];
		for (int i = 0; i < moves.length; i++) {
			os[i] = o.makeMove(moves[i]);
    	}
    	return new Pair<String[], Othello[]>(moves, os);
	}

	/* 
	 * Returns a Pair of the best value and the best move. 
	 * If no best move (there are no valid moves), then the best move is null.
	 */
	private Pair<Double, String> alphaBeta(Othello o, double alpha, double beta, int depth, 
			boolean maximisingPlayer) {
	    
	    // End if depth is 0 or no more moves
	    if (depth == 0 || o.getState() != State.NONE) {
	        return new Pair<Double, String>(eval(o), null);
	    }
	    
	    String bestMove = null;
	    Pair<String[], Othello[]> children = getChildren(o);
	    
	    // Max player
	    if (maximisingPlayer) {
	    	double v = -Double.MAX_VALUE;
	        for (int i = 0; i < children.first.length; i++) {
	            double childValue = alphaBeta(children.second[i], alpha, beta, depth-1, false).first;
	            if (v < childValue || (v == childValue && bestMove == null)) {
	            	v = childValue;
	            	bestMove = children.first[i];
	            }
	            alpha = Math.max(alpha, v);
	            if (beta <= alpha) {
	                break;
	            }
	        }
	        return new Pair<Double, String>(v, bestMove);
	    }
	    
	    // Min player
	    else {
	    	double v = Double.MAX_VALUE;
	        for (int i = 0; i < children.first.length; i++) {
	            double childValue = alphaBeta(children.second[i], alpha, beta, depth-1, true).first;
	            if (v > childValue || (v == childValue && bestMove == null)) {
	            	v = childValue;
	            	bestMove = children.first[i];
	            }
	            beta = Math.min(beta, v);
	            if (beta <= alpha) {
	                break;
	            }
	        }
	        return new Pair<Double, String>(v, bestMove);
	    }
	}
}
