package Cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cache<KeyType, ValueType> {

	public static class Element<ValueType> {
		public ValueType value;
		public int timestamp;
		
		public Element(ValueType value, int timestamp) {
			this.value = value;
			this.timestamp = timestamp;
		}
	}
	
	private Getable<KeyType, ValueType> getator;
	private int size;
	private TreeMap<Integer, KeyType> byTimestamp;
	private Map<KeyType, Element<ValueType>> byKey;
	private int timestamp;
	private Lock lock;
	private Condition cond;
	private Set<KeyType> currentlyComputed;
	
	public Cache(Getable<KeyType, ValueType> getator, int size) {
		this.getator = getator;
		this.size = size;
		this.timestamp = 0;
		byTimestamp = new TreeMap<Integer, KeyType>();
		byKey = new HashMap<KeyType, Element<ValueType>>();
		lock = new ReentrantLock();
		cond = lock.newCondition();
		currentlyComputed = new HashSet<KeyType>();
	}
	
	public ValueType getValue(KeyType key) throws InterruptedException {
		lock.lock();
		while (currentlyComputed.contains(key)) {
			cond.await();
		}
		timestamp++;
		ValueType value;
		if (byKey.containsKey(key)) {
			value = getAndUpdate(key);
		} else {
			currentlyComputed.add(key);
			lock.unlock();
			value = getator.get(key);
			lock.lock();
			store(key, value);
			currentlyComputed.remove(key);
			cond.signalAll();
		}
		lock.unlock();
		return value;
	}
	
	private void store(KeyType key, ValueType value) {
		if(byKey.size() >= size) {
			Entry<Integer, KeyType> toRemove = byTimestamp.firstEntry();
			byKey.remove(toRemove.getValue());
			byTimestamp.remove(toRemove.getKey());
		}
		Element<ValueType> e = new Element<ValueType>(value, timestamp);
		byKey.put(key, e);
		byTimestamp.put(timestamp, key);
	}

	private ValueType getAndUpdate(KeyType key) {
		Element<ValueType> e = byKey.get(key);
		byTimestamp.remove(e.timestamp);
		e.timestamp = timestamp;
		byTimestamp.put(e.timestamp, key);
		return e.value;
	}
}
