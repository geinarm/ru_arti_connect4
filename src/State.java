
public class State {
	final static int WIDTH = 7;		//Width of the game board
	final static int HEIGHT = 6;	//Height of the game board
	
	long white;			//Bitmask holding whites positions
	long red;			//Bitmask holding red positions
	boolean whiteTurn;
	int lastDrop;
	
	State()
	{
		white = 0;
		red = 0;
		whiteTurn = true;
	}
	
	//Copy constructor
	State(State s)
	{
		white = s.white;
		red = s.red;
		whiteTurn = s.whiteTurn;
	}
	
	//Copies the state and changes the current player 
	public State nextState()
	{
		State s = new State(this);
		s.whiteTurn = !whiteTurn;
		
		return s;
	}
	
	//Add a disk to the game board for the given player in the given column
	public void dropAt(int col)
	{
		lastDrop = col;
		
		//Shift the bitmask to the column
		long mask = 1 << (col-1);
		
		//Shift the bitmask to the row
		while((mask & (white | red)) > 0)
			mask = mask << WIDTH;
		
		//Add to the given players bitmask
		if(whiteTurn)
			white |= mask;
		else
			red |= mask;
	}

	//Check if the cell is empty
	public boolean isEmpty(int col, int row)
	{
		if (col <= 0 || row <= 0 ||
			col > WIDTH || row > HEIGHT) return false;
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
		if (col <= 0 || row <= 0 ||
			col > WIDTH || row > HEIGHT) return null;
		long mask = 1;
		mask = mask << (((row-1) * WIDTH) + (col-1));
		
		if((white & mask) > 0)
			return Player.WHITE;
		else if((red & mask) > 0)
			return Player.RED;
		else
			return null;
	}

	public boolean isTerminal()
	{
		long location = 1 << (lastDrop - 1);
		long fulltable = -1 >>> (64 - HEIGHT * WIDTH);
		int row = 0; // Height of the column where lastMove was droped
		int i, j, c;
		Player player = null;
		if ((white | red) == fulltable)
			return true;
		
		// Find where last disc was placed (row) 
		while ((location & (white | red)) > 0) {
			location = location << WIDTH;
			row++;
		}
		location = location >> WIDTH;
		row--;
		
		// Get the player of the previous move
		player = getPlayerAt(lastDrop, row);
		
		// HORIZONTAL
		c = 0;
		i = lastDrop;
		while (getPlayerAt(--i, row) == player)
			c++;
		i = lastDrop;
		while (getPlayerAt(++i, row) == player)
			c++;
		
		if (c >= 4) return true;
		
		// VERTICAL
		c = 0;
		i = row;
		while (getPlayerAt(lastDrop, --i) == player)
			c++;
		i = row;
		while (getPlayerAt(lastDrop, ++i) == player)
			c++;
		
		if (c >= 4) return true;
		
		// DIAGONAL /
		c = 0;
		i = row;
		j = lastDrop;
		while (getPlayerAt(++j, ++i) == player)
			c++;
		i = row;
		while (getPlayerAt(--j, --i) == player)
			c++;
		
		if (c >= 4) return true;
		
		// DIAGONAL \
		c = 0;
		i = row;
		j = lastDrop;
		while (getPlayerAt(++j, --i) == player)
			c++;
		i = row;
		while (getPlayerAt(--j, ++i) == player)
			c++;
		
		if (c >= 4) return true;
		return false;
	}
}
