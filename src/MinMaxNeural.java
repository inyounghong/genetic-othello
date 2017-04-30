//label mmnn
public class MinMaxNeural extends MinMaxAI {

	public MinMaxNeural(double[] d) {
		super(d);
		// TODO Auto-generated constructor stub
	}

	public MinMaxNeural(int n) {
		super(n);
		// TODO Auto-generated constructor stub
	}
	
	private double sig (double d) {
		return 1/(1+Math.pow(2, -d));
		
	}
	public double innerEval (Othello o){
		
		Piece[][] board = o.boardCopy();
		double[] dna = getDna();
		double[] layer1 = new double[64];
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				switch (board[i][j]) {
					case B:
						layer1[8*i+j] = 1.0;
						break;
					case W:
						layer1[8*i+j] = -1.0;
						break;
					default:
						layer1[8*i+j] = 0.0;
						break;
				}
			}
		}
		
		double out = 0.0;
		
		for (int i = 0; i < 32; i++) {
			double sum = dna[i]; // the bias
			for (int j = 0; j < 64; j++) {
				sum += layer1[i] * dna[32+64*i+j];
			}
			out += sig(sum) * dna[65*32 + i];
		}
		
		return out;		
		
	}

}
