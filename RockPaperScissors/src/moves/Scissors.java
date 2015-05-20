package moves;

import results.Draw;
import results.Lose;
import results.Result;
import results.Win;

public class Scissors extends Move {

	@Override
	public Result compare(Rock rock) {
		return Lose.getInstance();
	}

	@Override
	public Result compare(Paper paper) {
		return Win.getInstance();
	}	

	@Override
	public Result compare(Scissors scissors) {
		return Draw.getInstance();
	}

	@Override
	public Result compare(Move move) {
		return move.compare(this).opposite();
	}
	
	@Override
	public String toString() {
		return "scissors";
	}

	@Override
	public String getSymbol() {
		return "S";
	}

}
