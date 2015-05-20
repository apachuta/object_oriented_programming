package moves;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import results.Draw;
import results.Lose;
import results.Win;

public class MoveTest {

	@Test
	public void RockCompareToRock() {
		Move move1 = new Rock();
		Move move2 = new Rock();
		assertEquals(move1.compare(move2), Draw.getInstance());
	}

	@Test
	public void PaperCompareToPaper() {
		Move move1 = new Paper();
		Move move2 = new Paper();
		assertEquals(move1.compare(move2), Draw.getInstance());
	}
	
	@Test
	public void ScissorsCompareToScissors() {
		Move move1 = new Scissors();
		Move move2 = new Scissors();
		assertEquals(move1.compare(move2), Draw.getInstance());
	}
	
	@Test
	public void RockCompareToPaper() {
		Move move1 = new Rock();
		Move move2 = new Paper();
		assertEquals(move1.compare(move2), Lose.getInstance());
	}
	
	@Test
	public void PaperCompareToRock() {
		Move move1 = new Paper();
		Move move2 = new Rock();
		assertEquals(move1.compare(move2), Win.getInstance());
	}
	
	@Test
	public void PaperCompareToScissors() {
		Move move1 = new Paper();
		Move move2 = new Scissors();
		assertEquals(move1.compare(move2), Lose.getInstance());
	}
	
	@Test
	public void ScissorsCompareToPaper() {
		Move move1 = new Scissors();
		Move move2 = new Paper();
		assertEquals(move1.compare(move2), Win.getInstance());
	}
	
	@Test
	public void ScissorsCompareToRock() {
		Move move1 = new Scissors();
		Move move2 = new Rock();
		assertEquals(move1.compare(move2), Lose.getInstance());
	}
	
	@Test
	public void RockCompareToScissors() {
		Move move1 = new Rock();
		Move move2 = new Scissors();
		assertEquals(move1.compare(move2), Win.getInstance());
	}
	
}
