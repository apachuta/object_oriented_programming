package players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import exceptions.GameBrokenException;
import moves.Move;
import moves.MoveFactory;
import results.Result;

public class Human implements Player {
	
	private BufferedReader bufferedReader;
	private String name;

	public Human(BufferedReader bufferedReader, String name) {
		this.bufferedReader = bufferedReader;
		this.name = name;
	}
	
	public Human(InputStream inputStream, String name) {
		this(new BufferedReader(new InputStreamReader(inputStream)), name);
	}
	
	public Human(InputStream inputSteam) {
		this(inputSteam, "Player");
	}
	
	@Override
	public Move chooseMove(List<Move> possibleMoves) throws GameBrokenException {
		List<String> possibleSymbols = getPossibleSymbols(possibleMoves);
		String possibleMovesString = getPossibleMovesString(possibleMoves);
		
		while(true) {
				System.out.println(name + ", choose move (" + possibleMovesString + "):");
				String inputSymbol;
				try {
					inputSymbol = bufferedReader.readLine();
				} catch (IOException e) {
					throw new GameBrokenException(e);
				}
				if(possibleSymbols.contains(inputSymbol)) {
					return MoveFactory.getMove(inputSymbol);
				}
				System.out.println("Wrong choice. Try again.");
		}
	}

	@Override
	public void giveRoundResults(Move yourMove, Move opponentMove, Result result, int points) {
	}
	
	@Override
	public void giveGameResults(Result result) {
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	private List<String> getPossibleSymbols(List<Move> possibleMoves) {
		List<String> possibleSymbols = new ArrayList<String>(); 
		for(Move move : possibleMoves)
		{
			possibleSymbols.add(move.getSymbol());
		}
		return possibleSymbols;
	}
	
	String getPossibleMovesString(List<Move> possibleMoves) {
		List<String> movesStrings = new ArrayList<String>();
		for (Move move: possibleMoves) {
			movesStrings.add("[" + move.getSymbol() + "] " + move);
		}
		return String.join(", ", movesStrings);
	}
	
}
