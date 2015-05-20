package players;

import java.util.List;
import java.util.Random;

import exceptions.GameBrokenException;
import moves.Move;
import results.Result;

public class Computer implements Player {

	private String name;
	
	public Computer(String name) {
		this.name = name;
	}
	
	public Computer() {
		this("Computer");
	}
	
	@Override
	public Move chooseMove(List<Move> possibleMoves) throws GameBrokenException {
		Random random = new Random();
        int randomIndex = random.nextInt(possibleMoves.size());
        return possibleMoves.get(randomIndex);
	}

	@Override
	public void giveRoundResults(Move yourMove, Move opponentMove, Result result,
			int points) {
	}

	@Override
	public void giveGameResults(Result result) {
	}
	
	@Override
	public String toString(){
		return name;
	}

}
