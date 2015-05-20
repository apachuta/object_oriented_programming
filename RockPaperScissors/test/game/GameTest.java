package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import moves.Move;
import moves.Paper;
import moves.Rock;
import moves.Scissors;

import org.junit.Test;

import players.Human;
import players.Player;
import results.Result;
import exceptions.GameBrokenException;

public class GameTest {

	private class MutableInteger {
		public int value;
		
		public MutableInteger() {
			value = 0;
		}
	}
	
	@Test
	public void human2IsWinner() throws GameBrokenException {
		String input1 = "R\nR\nS\nR\nP\n";
		String input2 = "R\nP\nP\nP\nS\n";
		final Human human1 = new Human(new ByteArrayInputStream(input1.getBytes()));
		final Human human2 = new Human(new ByteArrayInputStream(input2.getBytes()));
		List<Move> possibleMoves = Arrays.asList(new Rock(), new Paper(), new Scissors());
		GameConfiguration config = new GameConfiguration(possibleMoves, 3);
		
		GameSpectator gameSpectator = new GameSpectator() {
			@Override
			public void playerHasMoved() {
			}
			
			@Override
			public void giveRoundResults(Move move1, Move move2, Result result1,
					Result result2, int points1, int points2) {
			}
			
			@Override
			public void giveGameWinner(Player winner) {
				assertTrue(winner == human2);
			}
		};
		
		new Game(gameSpectator, human1, human2, config).start();
	}
	
	@Test
	public void winnerHas3Points() throws GameBrokenException {
		// prepare
		String input1 = "R\nR\nS\nR\nP\n";
		String input2 = "R\nP\nP\nP\nS\n";
		final Human human1 = new Human(new ByteArrayInputStream(input1.getBytes()));
		final Human human2 = new Human(new ByteArrayInputStream(input2.getBytes()));
		List<Move> possibleMoves = Arrays.asList(new Rock(), new Paper(), new Scissors());
		GameConfiguration config = new GameConfiguration(possibleMoves, 3);
		final MutableInteger human2points = new MutableInteger();
		
		GameSpectator mockGameSpectator = new GameSpectator() {
			@Override
			public void playerHasMoved() {
			}
			
			@Override
			public void giveRoundResults(Move move1, Move move2, Result result1,
					Result result2, int points1, int points2) {
				human2points.value = points2;
			}
			
			@Override
			public void giveGameWinner(Player winner) {
			}
		};

		// test
		new Game(mockGameSpectator, human1, human2, config).start();
		
		// verify
		assertEquals(3, human2points.value);

	}
	
	@Test
	public void roundResultsAreAsExpected() throws GameBrokenException {
		String input1 = "R\nR\nS\nR\nP\n";
		String input2 = "R\nP\nP\nP\nS\n";
		final Human human1 = new Human(new ByteArrayInputStream(input1.getBytes()));
		final Human human2 = new Human(new ByteArrayInputStream(input2.getBytes()));
		List<Move> possibleMoves = Arrays.asList(new Rock(), new Paper(), new Scissors());
		GameConfiguration config = new GameConfiguration(possibleMoves, 3);
		final MutableInteger roundNumber = new MutableInteger();
		final String[] expectedResults = {"draw", "lost", "won", "lost", "lost"};
		
		GameSpectator gameSpectator = new GameSpectator() {
			@Override
			public void playerHasMoved() {
			}
			
			@Override
			public void giveRoundResults(Move move1, Move move2, Result result1,
					Result result2, int points1, int points2) {
				String expectedResult = expectedResults[roundNumber.value];
				assertEquals("After move " + roundNumber.value + " player1 was expected to " 
						+ expectedResult + ".", expectedResult, result1.toString());
				roundNumber.value++;
			}
			
			@Override
			public void giveGameWinner(Player winner) {

			}
		};
		
		new Game(gameSpectator, human1, human2, config).start();
	}
	
	@Test
	public void counterValuesAreAsExpected() throws GameBrokenException {
		String input1 = "R\nR\nS\nR\nP\n";
		String input2 = "R\nP\nP\nP\nS\n";
		final Human human1 = new Human(new ByteArrayInputStream(input1.getBytes()));
		final Human human2 = new Human(new ByteArrayInputStream(input2.getBytes()));
		List<Move> possibleMoves = Arrays.asList(new Rock(), new Paper(), new Scissors());
		GameConfiguration config = new GameConfiguration(possibleMoves, 3);
		final MutableInteger hasMovedCounter = new MutableInteger();
		final MutableInteger roundNumber = new MutableInteger();
		final MutableInteger winnerCounter = new MutableInteger();
		
		GameSpectator gameSpectator = new GameSpectator() {
			@Override
			public void playerHasMoved() {
				hasMovedCounter.value++;
			}
			
			@Override
			public void giveRoundResults(Move move1, Move move2, Result result1,
					Result result2, int points1, int points2) {
				roundNumber.value++;
			}
			
			@Override
			public void giveGameWinner(Player winner) {
				winnerCounter.value++;
			}
		};
		
		new Game(gameSpectator, human1, human2, config).start();
		
		assertEquals(10, hasMovedCounter.value);
		assertEquals(5, roundNumber.value);
		assertEquals(1, winnerCounter.value);
	}

}
