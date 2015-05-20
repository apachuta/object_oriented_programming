package players;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import moves.Move;
import moves.Paper;
import moves.Rock;
import moves.Scissors;

import org.junit.Test;

import exceptions.GameBrokenException;

public class HumanTest {

	@Test
	public void humanChoosesRock() throws GameBrokenException {
		String input = "R\n";
		InputStream mockInputStream = new ByteArrayInputStream(input.getBytes());
		Human human = new Human(mockInputStream);
		List<Move> possibleMoves = Arrays.asList(new Rock(), new Paper(), new Scissors());
		assertTrue(human.chooseMove(possibleMoves) instanceof Rock);
	}
	
	@Test
	public void humanChoosesPaper() throws GameBrokenException {
		String input = "P\n";
		InputStream mockInputStream = new ByteArrayInputStream(input.getBytes());
		Human human = new Human(mockInputStream);
		List<Move> possibleMoves = Arrays.asList(new Rock(), new Paper(), new Scissors());
		assertTrue(human.chooseMove(possibleMoves) instanceof Paper);
	}

	@Test
	public void humanChoosesScissors() throws GameBrokenException {
		String input = "S\n";
		InputStream mockInputStream = new ByteArrayInputStream(input.getBytes());
		Human human = new Human(mockInputStream);
		List<Move> possibleMoves = Arrays.asList(new Rock(), new Paper(), new Scissors());
		assertTrue(human.chooseMove(possibleMoves) instanceof Scissors);
	}
	
	@Test
	public void humanChoosesRockPaperRockScissors() throws GameBrokenException {
		String input = "R\nP\nR\nS\n";
		InputStream mockInputStream = new ByteArrayInputStream(input.getBytes());
		Human human = new Human(mockInputStream);
		List<Move> possibleMoves = Arrays.asList(new Rock(), new Paper(), new Scissors());
		assertTrue(human.chooseMove(possibleMoves) instanceof Rock);
		assertTrue(human.chooseMove(possibleMoves) instanceof Paper);
		assertTrue(human.chooseMove(possibleMoves) instanceof Rock);
		assertTrue(human.chooseMove(possibleMoves) instanceof Scissors);
	}
	
	@Test
	public void humanChoosesThreeTimesIncorectlyAndScissors() throws GameBrokenException {
		String input = "X\n  xxx\n\nS\n";
		InputStream mockInputStream = new ByteArrayInputStream(input.getBytes());
		Human human = new Human(mockInputStream);
		List<Move> possibleMoves = Arrays.asList(new Rock(), new Paper(), new Scissors());
		assertTrue(human.chooseMove(possibleMoves) instanceof Scissors);
	}
}
