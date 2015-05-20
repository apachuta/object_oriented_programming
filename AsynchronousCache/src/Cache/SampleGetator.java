package Cache;

public class SampleGetator implements Getable<Integer, String> {

	@Override
	public String get(Integer key) {
		return ":) " + key;
	}

}
