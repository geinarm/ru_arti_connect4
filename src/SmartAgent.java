import java.util.ArrayList;

public class SmartAgent implements Agent
{
	private State currentState;
	private Player role;
	private int playclock;
	private boolean myTurn;
	private int lastDrop;
	
	public void init(String role, int playclock) {
		// TODO Auto-generated method stub
		
		this.role = Player.valueOf(role);
		myTurn = !role.equals("WHITE");
		this.playclock = playclock;
		currentState = new State();
	}

	public String nextAction(int lastDrop) {
		myTurn = !myTurn;
		if(lastDrop != 0)
			currentState.dropAt(lastDrop);
		
		if(myTurn){
			return getNextMove();
		}
		else{
			return "NOOP";
		}
	}

	private String getNextMove()
	{
		System.out.println("yo, i need a move");
		
		int bestMove = 0;
		int bestVal = 0;
		
		for(int i : generateLegalMoves(currentState)){
			//Create the next state
			State nextState = currentState.nextState();
			nextState.dropAt(i);
			
			//Evaluate the state
			int val = alphaBetaSearch(3, currentState, -1000, 1000);
			
			//Update the best
			if(val > bestVal){
				bestVal = val;
				bestMove = i;
			}
		}
		
		return "(DROP " + bestMove + ")";
	}
	
	private int alphaBetaSearch(int depth, State state, int alpha, int beta)
	{
		System.out.println("yo");
		
		if(depth <= 0 || state.isTerminal()){
			System.out.println("yo dawg, returning");
			return evaluate(state);
		}
		
		int best = -1000;
		for(int i : generateLegalMoves(state)){
			//Generate next state
			State next = state.nextState();
			next.dropAt(i);
			
			int value = -alphaBetaSearch(depth-1, next, -beta, -alpha);
			
			//Update best value
			if(value > best)
				best = value;
			
			//Beta cutoff
			if(best > alpha){
				alpha = best;
				if(alpha >= beta) break;
			}
		}
		
		return 1;
	}
	
	private int evaluate(State state)
	{
		int score = 0;
		// mask:
		/*****************/
		/* _ _ _ _ _ _ _ */
		/* _ _ _ _ _ _ _ */
		/* _ _ _ _ _ _ _ */
		/* _ 1 1 _ _ _ _ */
		/* _ x 1 _ _ _ _ */
		/* _ _ 1 _ _ _ _ */
		/*****************/
		// ...0000110000001000000100
		
		// Mask is for x=1 y=1
		long mask = 0;
		mask |= 3 << 15; // u,ur
		mask |= 1 << 9; // r
		mask |= 1 << 2; // dr
		
		long m;
		
		int index;
		for (int col = 0; col < State.WIDTH-1; col++) {
			for (int row = 0; row < State.HEIGHT-1; row++) {
				// check if all edges (or just ul,u,ur,r,dr) are same as player in this point
				index = col + (row * State.WIDTH);
				
				// WHITE
				m = state.white & (mask << (index-8));
				while ((m=m>>>1) != 0)
					score += m & 1;
				
				// RED
				m = state.red & (mask << (index-1));
				// @TODO: fix bits that circle to different row
				while ((m=m>>>1) != 0)
					score += m & 1;
			}
		}
		System.out.println("Score: " + score);
		return score;
	}
	
	private ArrayList<Integer> generateLegalMoves(State state)
	{
		ArrayList<Integer> moves = new ArrayList<Integer>();
		
		for(int i = 1; i <= State.WIDTH; i++){
			if(state.isEmpty(i, State.HEIGHT))
				moves.add(i);
		}
		
		return moves;
	}
	
}
