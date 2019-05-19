package mitro.persistenza;

public class PersistenzaException extends Exception {

	private static final long serialVersionUID = 7547078945500647912L;

	public PersistenzaException() {
		super();
	}

	public PersistenzaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PersistenzaException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistenzaException(String message) {
		super(message);
	}

	public PersistenzaException(Throwable cause) {
		super(cause);
	}
	
}
