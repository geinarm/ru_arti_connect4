
public class UnitTest {

	public static void main(String[] args){
		State s = new State();
		
		s.dropAt(2);
		if(s.isEmpty(2, 1)){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		
		s = s.nextState(); 
		s.dropAt(2);
		if(s.isEmpty(2, 2)){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		
		s = s.nextState();
		s.dropAt(5);
		if(s.isEmpty(5, 1)){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		
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
		
		////////////////
		// isTerminal //
		////////////////
		
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
	}
	
}
