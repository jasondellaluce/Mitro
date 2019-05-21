package mitro.exceptions;

public class ElementoGiaPersistenteException extends PersistenzaException {

	private static final long serialVersionUID = -6560678572465620253L;

	public ElementoGiaPersistenteException() {
		
	}

	public ElementoGiaPersistenteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ElementoGiaPersistenteException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElementoGiaPersistenteException(String message) {
		super(message);
	}

	public ElementoGiaPersistenteException(Throwable cause) {
		super(cause);
	}

}
