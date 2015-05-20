package moves;

import java.util.Arrays;
import java.util.List;

public class MoveFactory {
	
	private static final List<Move> moves = Arrays.asList(new Rock(), new Paper(), new Scissors());
	
	public static Move getMove(String symbol) {
		
		for(Move move : moves) {
			if(symbol.equals(move.getSymbol())) {
				return move;
			}
		}
		
		return null;
	}
}
