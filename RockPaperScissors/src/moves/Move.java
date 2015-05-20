package moves;

import results.Result;

public abstract class Move {
	
	public Result compare(Rock rock) {
		return compare((Move) rock);
	}
	
	public Result compare(Paper paper) {
		return compare((Move) paper);
	}
	
	public Result compare(Scissors scissors) {
		return compare((Move) scissors);
	}
	
	public abstract Result compare(Move move);
	@Override
	public abstract String toString();
	public abstract String getSymbol();

}