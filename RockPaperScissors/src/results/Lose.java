package results;

public class Lose extends Result {

	private static Lose instance;
	
	private Lose() {}
	
	@Override
	public String toString() {
		return "lost";
	}

	@Override
	public Result opposite() {
		return Win.getInstance();
	}

	@Override
	public int getPoints() {
		return 0;
	}
	
	public static Lose getInstance() {
		if (instance == null) {
			instance = new Lose();
		}
		return instance;
	}

}
