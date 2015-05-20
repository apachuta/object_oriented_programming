package moves;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoveFactoryTest {

	@Test
	public void RSymbolEqualsRock() {
		assertTrue(MoveFactory.getMove("R") instanceof Rock);
	}
	
	@Test
	public void PSymbolEqualsPaper() {
		assertTrue(MoveFactory.getMove("P") instanceof Paper);
	}
	
	@Test
	public void SSymbolEqualsScissors() {
		assertTrue(MoveFactory.getMove("S") instanceof Scissors);
	}
	
	@Test
	public void emptySymbolEqualsNull() {
		assertEquals(MoveFactory.getMove(""), null);
	}
	
	@Test
	public void randomSymbolEqualsNull() {
		assertEquals(MoveFactory.getMove("x"), null);
	}
}
