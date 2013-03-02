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
		if(lastDrop != 0){
			currentState = currentState.nextState(lastDrop); 
			
			System.out.print(currentState);
		}
		
		if(myTurn){
			System.out.println("My turn"); 
			String move = getNextMove();
			System.out.println("Move: " + move);
			return move;
		}
		else{
			System.out.println("Not my turn");
			return "NOOP";
		}
	}

	private String getNextMove()
	{
		int bestMove = 0;
		int bestVal = -1000;
		if(currentState.isEmpty(4, 1))
			return "(DROP 4)";
		for(int i : generateLegalMoves(currentState)){
			//Create the next state
			State nextState = currentState.nextState(i);
			
			//Evaluate the state
			int val = -alphaBetaSearch(6, nextState, -1000, 1000);
			
			//Update the best
			if(val > bestVal){
				bestVal = val;
				bestMove = i;
			}
		}
		
		System.out.println("Score: " + bestVal);
		return "(DROP " + bestMove + ")";
	}
	
	private int alphaBetaSearch(int depth, State state, int alpha, int beta)
	{
		if(depth <= 0){
			return evaluate(state, depth);
		}
		if(state.isTerminal())
			return evaluate(state, depth);
		
		int best = -1000;
		for(int i : generateLegalMoves(state)){
			//Generate next state
			State next = state.nextState(i);
			
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
		
		return best;
	}
	
	private int evaluate(State state, int depth)
	{
		int val = 0;
		
		//Punish long paths
		/*if(state.whiteTurn)
			val -= Long.bitCount(state.red) * 2;
		else
			val -= Long.bitCount(state.white) * 2;*/
		
		//Check row length
		int minRowLength = 0;
		int maxRowLength = 0;
		for(int r=1; r <= State.HEIGHT; r++){
			for(int c=1; c <= State.WIDTH; c++){
				Player p = state.getPlayerAt(c, r);
				
				if(p == role){
					maxRowLength += state.countAdjacent(c, r, 1, 0);
					maxRowLength += state.countAdjacent(c, r, 0, 1);
					maxRowLength += state.countAdjacent(c, r, 1, 1);
					maxRowLength += state.countAdjacent(c, r, 1, -1);
					/*if(maxRowLength >= 4){
						val = 100;
					}*/
				}
				else if(p != null){
					minRowLength += state.countAdjacent(c, r, 1, 0);
					minRowLength += state.countAdjacent(c, r, 0, 1);
					minRowLength += state.countAdjacent(c, r, 1, 1);
					minRowLength += state.countAdjacent(c, r, 1, -1);
					/*if(minRowLength >= 4){
						val = -100;
					}*/
				}
			}	
		}
		
		val -= minRowLength;
		val += maxRowLength;
		
		//Check next thing
		
		return (val-(depth*10));
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
