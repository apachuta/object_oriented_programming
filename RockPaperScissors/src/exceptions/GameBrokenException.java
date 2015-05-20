package exceptions;

public class GameBrokenException extends Exception {

	private static final long serialVersionUID = 1L;

		public GameBrokenException() {
			super();
		}
		
		public GameBrokenException(String msg) {
			super(msg);
		}
		
		public GameBrokenException(String msg, Throwable e) {
			super(msg, e);
		}
		
		public GameBrokenException(Throwable e) {
			super(e);
		}
}
