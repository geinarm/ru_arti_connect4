
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
	public State nextState(int col)
	{
		State s = new State(this);
		s.dropAt(col);
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
		//if (!inBounds(col, row)) return false;
		long mask = 1;
		mask = mask << (((row-1) * WIDTH) + (col-1));
		
		if((mask & (white | red)) != 0)
			return false;
		
		return true;
	}
	
	
	//Check what player holds this position. Returns null if empty
	public Player getPlayerAt(int col, int row)
	{
		if (!inBounds(col, row)) return null;
		long mask = 1;
		mask = mask << (((row-1) * WIDTH) + (col-1));
		
		if((white & mask) > 0)
			return Player.WHITE;
		else if((red & mask) > 0)
			return Player.RED;
		else
			return null;
	}
	
    public int countAdjacent(int col, int row, int cOffset, int rOffset)
    {
    	if (!inBounds(col, row)) return 0;
    	
        Player p = getPlayerAt(col, row);
        int count = 0;
        
        if(p != null){
            int c = col;
            int r = row;
            
        	while(getPlayerAt(c, r) == p && inBounds(c, r)){
        		count ++;
        		c += cOffset;
        		r += rOffset;
        	}
            c = col-cOffset;
            r = row-rOffset;
        	while(getPlayerAt(c, r) == p && inBounds(c, r)){
        		count ++;
        		c -= cOffset;
        		r -= rOffset;
        	}
        }
        
        return count;
    }
    
    public boolean inBounds(int col, int row)
    {
    	if(col < 1 || col > WIDTH)
    		return false;
    	
    	if(row < 1 || row > HEIGHT)
    		return false;
    	
    	return true;
    }

	public boolean isTerminal()
	{
		if((white & red) != 0){
			System.err.println("Invalid state");
			System.exit(0);
		}
		
		//Check if the board is full
        long fulltable = -1 >>> (64 - HEIGHT * WIDTH);
        if ((white | red) == fulltable)
            return true;		
		
        //Check if the last drop is a part of a winning row
		int col = lastDrop;
		int row = 1;
		
		while(!isEmpty(col, row+1))
			row ++;
		
		//Check right/left
		if(countAdjacent(col, row, 1, 0) >= 4)
			return true;
		
		//Check up/down
		if(countAdjacent(col, row, 0, 1) >= 4)
			return true;
		
		//Check diagonal /
		if(countAdjacent(col, row, 1, 1) >= 4)
			return true;
		
		//Check diagonal \
		if(countAdjacent(col, row, -1, 1) >= 4)
			return true;
		
		return false;
	}
	
	@Override
	public String toString()
	{
		String board = "|";
		
		for(int r=HEIGHT; r >= 1; r--){
			for(int c=1; c <= State.WIDTH; c++){
				Player p = getPlayerAt(c, r);
				if(p == Player.WHITE)
					board += "X";
				else if(p == Player.RED)
					board += "O";
				else
					board += " ";
			}
			board += "|\n|";
		}
		
		return board;
	}
}
