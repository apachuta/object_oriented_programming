package game;

import exceptions.GameBrokenException;

import java.util.List;

import moves.Move;
import players.Player;
import results.*;

public class Game {
	
	private GameSpectator server;
	private Player player1;
	private Player player2;
	private GameConfiguration config;
	private int points1;
	private int points2;
	
	public Game(GameSpectator server, Player player1, Player player2, GameConfiguration config) {
		this.server = server;
		this.player1 = player1;
		this.player2 = player2;
		this.config = config;
	}
	
	public void start() throws GameBrokenException {
		points1 = 0;
		points2 = 0;
		List<Move> possibleMoves = config.getPossibleMoves();
		
		while(true) {
			Move move1 = player1.chooseMove(possibleMoves);
			server.playerHasMoved();
			Move move2 = player2.chooseMove(possibleMoves);
			server.playerHasMoved();
			Result result1 = move1.compare(move2);
			Result result2 = result1.opposite();
			points1 += result1.getPoints();
			points2 += result2.getPoints();
			player1.giveRoundResults(move1, move2, result1, points1);
			player2.giveRoundResults(move2, move1, result2, points2);
			server.giveRoundResults(move1, move2, result1, result2, points1, points2);
			if(points1 == config.getNumberOfRounds()) {
				endOfGame(player1, player2);
				break;
			}
			if(points2 == config.getNumberOfRounds()) {
				endOfGame(player2, player1);
				break;
			}
			
		}
		
	}
	
	private void endOfGame(Player winner, Player loser) {
		winner.giveGameResults(Win.getInstance());
		loser.giveGameResults(Lose.getInstance());
		server.giveGameWinner(winner);
	}
}
