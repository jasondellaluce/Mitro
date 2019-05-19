package mitro.controller;

public class OperationException extends Exception {

	private static final long serialVersionUID = 7329572230129311174L;

	public OperationException() {
		super();
	}

	public OperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public OperationException(String message) {
		super(message);
	}

	public OperationException(Throwable cause) {
		super(cause);
	}
	
}
