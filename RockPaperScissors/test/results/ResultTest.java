package results;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResultTest {

	@Test
	public void winOppositeEqualsLose() {
		assertEquals(Win.getInstance().opposite(), Lose.getInstance());
	}

	@Test
	public void loseOppositeEqualsWin() {
		assertEquals(Lose.getInstance().opposite(), Win.getInstance());
	}
	
	@Test
	public void drawOppositeEqualsDraw() {
		assertEquals(Draw.getInstance().opposite(), Draw.getInstance());
	}
		
}