package Cache;

public class CacheMain {

	
	public static class CacheThread<KeyType, ValueType> implements Runnable {

		private Cache<KeyType, ValueType> c;
		private KeyType key;
		
		public CacheThread(Cache<KeyType, ValueType> c, KeyType key) {
			this.c = c;
			this.key = key;
		}
	
		@Override
		public void run() {
			System.out.println("Key: " + key);
			try {
				ValueType value = c.getValue(key);
				System.out.println("KeyValue: " + key + " -> " + value);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Cache<Integer, String> c = new Cache<Integer, String>(new Getable<Integer, String>() {
			@Override
			public String get(Integer key) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return ":) " + key;
			}
		}, 3);
		
		Thread t = new Thread(new CacheThread<Integer, String>(c, 1));
		t.start();
		Thread t2 = new Thread(new CacheThread<Integer, String>(c, 2));
		t2.start();
		Thread t3 = new Thread(new CacheThread<Integer, String>(c, 1));
		t3.start();
		
		/*
		System.out.println(c.getValue(1));
		System.out.println(c.getValue(1));
		System.out.println(c.getValue(2));
		System.out.println(c.getValue(3));
		System.out.println(c.getValue(1));
		System.out.println(c.getValue(4));
		System.out.println(c.getValue(5));
		System.out.println(c.getValue(5));
		System.out.println(c.getValue(1));
		*/
	}
}
