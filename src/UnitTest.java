
public class UnitTest {

	public static void main(String[] args){
		State s = new State();
		
		s.dropAt(Player.WHITE, 2);
		if(s.isEmpty(2, 1)){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		
		s.dropAt(Player.RED, 2);
		if(s.isEmpty(2, 2)){
			System.out.println("LAME");
		}
		else{
			System.out.println("COOL");
		}
		
		s.dropAt(Player.WHITE, 5);
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
		
	}
	
}
