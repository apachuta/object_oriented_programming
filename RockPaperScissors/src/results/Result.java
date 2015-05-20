package results;

public abstract class Result {
	@Override
	public abstract String toString();
	public abstract Result opposite();
	public abstract int getPoints();
}
