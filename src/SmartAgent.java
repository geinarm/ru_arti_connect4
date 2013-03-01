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
		int col = alphaBetaSearch();
		
		return "(DROP " + col + ")";
	}
	
	private int alphaBetaSearch()
	{
		return 1;
	}
	
	private int evalueate(State state)
	{
		
		return 0;
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
