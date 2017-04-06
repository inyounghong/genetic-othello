public class MinMaxAI extends AI {

	private int DEPTH = 5; 		// Depth for alpha-beta search

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
		String bestMove = alphaBeta(o, -Double.MAX_VALUE, Double.MAX_VALUE, DEPTH, o.isWhiteTurn()).second;
		return bestMove;
	}

	// Evaluates a chess according to evaluation function
	public double eval(Othello o) {
		State s = o.getState();
		switch(s) {
			case W:
				return Double.MAX_VALUE;
			case B:
				return Double.MIN_VALUE;
			case DRAW:
				return 0;
			default:
				return innerEval(o);
		}
	}
	
	public double innerEval(Othello o) {
		return 0.0;
	}
	

	/* 
	 * Returns a Pair of the best value and the best move. 
	 * If no best move (there are no valid moves), then the best move is null.
	 */
	private Pair<Double, String> alphaBeta(Othello o, double alpha, double beta, int depth, 
			boolean maximisingPlayer) {
	    
	    // End if depth is 0 or no more moves
	    if (depth == 0 || o.getMoves().length == 0) {
	        return new Pair<Double, String>(eval(o), null);
	    }
	    
	    String bestMove = null;
	    
	    // Max player
	    if (maximisingPlayer) {
	    	double v = -Double.MAX_VALUE;
	        for (String m : o.getMoves()) {
	            double childValue = alphaBeta(o.makeMove(m), alpha, beta, depth-1, false).first;
	            if (v < childValue) {
	            	v = childValue;
	            	bestMove = m;
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
	        for (String m : o.getMoves()) {
	            double childValue = alphaBeta(o.makeMove(m), alpha, beta, depth-1, true).first;
	            if (v > childValue) {
	            	v = childValue;
	            	bestMove = m;
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
