//label is nng
public class Neural extends AI {

	public Neural(double[] d) {
		super(d);
		// TODO Auto-generated constructor stub
	}

	public Neural(int n) {
		super(n);
		// TODO Auto-generated constructor stub
	}
	
	private double sig (double d) {
		return 1/(1+Math.pow(2, -d));
	}
	
	public String pickMove(Othello o){
		String[] options = o.getMoves();
		if (options.length == 1) {
			return options[0];
		}
		double coeff = 1.0;
		if (o.isWhiteTurn()) {
			coeff = -1.0;
		}		
		Piece[][] board = o.boardCopy();
		double[] dna = getDna();
		double[] layer1 = new double[64];
		double[] layer2 = new double[64];
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				double spaceVal;
				switch (board[i][j]) {
					case B:
						spaceVal = coeff * 1.0;
						break;
					case W:
						spaceVal = coeff * -1.0;
						break;
					default:
						spaceVal = 0.0;
						break;
				}
				layer1[8*i+j] = spaceVal;
			}
		}
		
		for (int i = 0; i < 64; i++) {
			double sum = dna[i]; // the bias
			for (int j = 0; j < 64; j++) {
				sum += layer1[i] * dna[64+64*i+j];
			}
			layer2[i] = sig(sum);
		}
		
		
		double maxVal = - Double.MAX_VALUE;
		String toPick = "skip";
		
		for (int i = 0; i < options.length; i++) {
			int[] loc = Othello.unnote(options[i]);
			double sum = dna[65*64 + 8*loc[0] + loc[1]]; // the bias
			int offset = 64*(8*loc[0] + loc[1]) + 66*64;
			for (int j = 0; j < 64; j++) {
				sum += layer2[i] * dna[offset+j];
			}
			if (sum > maxVal) {
				maxVal = sum;
				toPick = options[i];
			}
		}
		
		return toPick;
	}

}
