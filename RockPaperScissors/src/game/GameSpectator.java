package game;

import moves.Move;
import players.Player;
import results.Result;

public interface GameSpectator {
	public void playerHasMoved();
	
	public void giveRoundResults(Move move1, Move move2, Result result1, Result result2, 
			int points1, int points2);
	
	public void giveGameWinner(Player winner);
}
