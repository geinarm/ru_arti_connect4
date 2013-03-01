
public class State {
	final static int WIDTH = 7;		//Width of the game board
	final static int HEIGHT = 6;	//Height of the game board
	
	long white;			//Bitmask holding whites positions
	long red;			//Bitmask holding red positions
	
	State()
	{
		white = 0;
		red = 0;
	}
	
	//Copy constructor
	State(State s)
	{
		white = s.white;
		red = s.red;
	}
	
	//Add a disk to the game board for the given player in the given column
	public void dropAt(Player p, int col)
	{
		//Shift the bitmask to the column
		long mask = 1 << (col-1);
		
		//Shift the bitmask to the row
		while((mask & (white | red)) > 0)
			mask = mask << WIDTH;
		
		//Add to the given players bitmask
		if(p == Player.WHITE)
			white |= mask;
		else
			red |= mask;
	}

	//Check if the cell is empty
	public boolean isEmpty(int col, int row)
	{
		long mask = 1;
		mask = mask << (((row-1) * WIDTH) + (col-1));

		//Debug stuff
		/*System.out.println("Mask: " + mask);
		System.out.println("White: " + white);
		System.out.println("Red: " + red);
		System.out.println("White or Red: " + (white | red));*/
		
		if((mask & (white | red)) != 0)
			return false;
		
		return true;
	}
	
	
	//Check what player holds this position. Returns null if empty
	public Player getPlayerAt(int col, int row)
	{
		long mask = 1;
		mask = mask << (((row-1) * WIDTH) + (col-1));
		
		if((white & mask) > 0)
			return Player.WHITE;
		else if((red & mask) > 0)
			return Player.RED;
		else
			return null;
	}
}
