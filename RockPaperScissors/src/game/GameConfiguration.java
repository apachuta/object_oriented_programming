package game;

import java.util.List;

import moves.*;

public class GameConfiguration {

	private List<Move> possibleMoves;
	int numberOfRounds;
	
	public GameConfiguration(List<Move> possibleMoves, int numberOfRounds) {
		this.possibleMoves = possibleMoves;
		this.numberOfRounds = numberOfRounds;
	}
	
	public GameConfiguration(List<Move> moves) {
		this(moves, 3);
	}
	
	public List<Move> getPossibleMoves() {
		return possibleMoves;
	}
	
	public int getNumberOfRounds() {
		return numberOfRounds;
	}
}
