package Cache;

public interface Getable<KeyType, ValueType> {
	public ValueType get(KeyType key);
}
