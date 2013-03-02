
public class UnitTest {

	public static void main(String[] args){
		State s = new State();
		
		System.out.println("Test dropAt and isEmpty");
		s.dropAt(2);
		if(s.isEmpty(2, 1)){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		
		s = s.nextState(2); 
		if(s.isEmpty(2, 2)){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		
		s = s.nextState(5);
		if(s.isEmpty(5, 1)){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		
		System.out.println("Test getPlayerAt");
		if(s.getPlayerAt(2, 2) != Player.RED){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		
		if(s.getPlayerAt(5, 1) != Player.WHITE){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		
		System.out.println("Test countAjacent");
        s.dropAt(7);
        s.dropAt(7);
        s.dropAt(7);
        s.dropAt(7);

        if(s.countAdjacent(7, 1, 0, 1) != 4){
            System.out.println("LAME");
        }
        else{
            System.out.println("COOL");
        }
        
        if(s.countAdjacent(7, 4, -1, 0) != 1){
            System.out.println("LAME");
        }
        else{
            System.out.println("COOL");
        }
		
		////////////////
		// isTerminal //
		////////////////
        System.out.println("Test isTerminal");
		s = new State();
		s.dropAt(1);
		s.dropAt(2);
		s.dropAt(3);

		// Should not be terminal
		if (s.isTerminal()){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		
		s.dropAt(4);
		
		// Should now be termial
		if (!s.isTerminal()){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		
		s = s.nextState(7);
		if (s.isTerminal()){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		s.dropAt(7);
		s.dropAt(7);
		if (s.isTerminal()){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		s.dropAt(7);
		if (!s.isTerminal()){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		s.dropAt(7);
		if (!s.isTerminal()){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		
		
		//In bounds
		System.out.println("Test inBounds");
		if (!s.inBounds(2, 2)){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		if (!s.inBounds(7, 6)){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		if (s.inBounds(8, 2)){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		if (!s.inBounds(1, 1)){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		if (s.inBounds(1, 7)){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
	}
	
}
