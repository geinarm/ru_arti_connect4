import java.util.ArrayList;

public class SmartAgent implements Agent
{
	private State currentState;
	private Player role;
	private int playclock;
	private boolean myTurn;
	
	public void init(String role, int playclock) {
		// TODO Auto-generated method stub
		
		this.role = Player.valueOf(role);
		myTurn = !role.equals("WHITE");
		this.playclock = playclock;
		currentState = new State();
	}

	public String nextAction(int lastDrop) {
		myTurn = !myTurn;
		
		if(myTurn){
			currentState.dropAt(role, 4);
			return getNextMove();
		}
		else{
			currentState.dropAt(Player.RED, lastDrop);
			return "NPOOP";
		}
	}

	private String getNextMove()
	{
		int col = alphaBetaSearch(3, currentState, -1000, 1000);
		
		return "(DROP " + col + ")";
	}
	
	private int alphaBetaSearch(int depth, State state, int alpha, int beta)
	{
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
		return score;
	}
	
	private ArrayList<String> generateLegalMoves(State state)
	{
		ArrayList<String> moves = new ArrayList<String>();
		
		for(int i = 1; i <= State.WIDTH; i++){
			if(state.isEmpty(i, State.HEIGHT))
				moves.add("(DROP "+ i +")");
		}
		
		return moves;
	}
	
	private boolean isGoal(State state)
	{
		return false;
	}
}
