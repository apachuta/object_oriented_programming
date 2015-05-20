package results;

public class Win extends Result {

	private static Win instance;
	
	private Win() {}
	
	@Override
	public String toString() {
		return "won";
	}

	@Override
	public Result opposite() {
		return Lose.getInstance();
	}

	@Override
	public int getPoints() {
		return 1;
	}
	
	public static Win getInstance() {
		if (instance == null) {
			instance = new Win();
		}
		return instance;
	}

}
