package players;

import java.util.List;

import moves.Move;
import results.Result;
import exceptions.GameBrokenException;

public interface Player {
	Move chooseMove(List<Move> possibleMoves) throws GameBrokenException;
	void giveRoundResults(Move yourMove, Move opponentMove, Result result, int points);
	void giveGameResults(Result result);
	@Override
	String toString();
}
