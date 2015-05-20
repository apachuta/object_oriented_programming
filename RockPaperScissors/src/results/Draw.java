package results;

public class Draw extends Result {

	private static Draw instance;
	
	private Draw(){}
	
	@Override
	public String toString() {
		return "draw";
	}

	@Override
	public Result opposite() {
		return Draw.getInstance();
	}
	
	@Override
	public int getPoints() {
		return 0;
	}
	
	public static Draw getInstance() {
		if (instance == null) {
			instance = new Draw();
		}
		return instance;
	}

}
