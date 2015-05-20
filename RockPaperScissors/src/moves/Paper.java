package moves;

import results.Draw;
import results.Lose;
import results.Result;
import results.Win;

public class Paper extends Move {

	@Override
	public Result compare(Rock rock) {
		return Win.getInstance();
	}

	@Override
	public Result compare(Paper paper) {
		return Draw.getInstance();
	}
	
	@Override
	public Result compare(Scissors scissors) {
		return Lose.getInstance();
	}

	@Override
	public Result compare(Move move) {
		return move.compare(this).opposite();
	}
	
	@Override
	public String toString() {
		return "paper";
	}

	@Override
	public String getSymbol() {
		return "P";
	}
}
