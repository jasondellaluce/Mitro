package mitro.exceptions;

public class ElementoNonPersistenteException extends PersistenzaException {

	private static final long serialVersionUID = 2539667237741420859L;

	public ElementoNonPersistenteException() {
		
	}

	public ElementoNonPersistenteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ElementoNonPersistenteException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElementoNonPersistenteException(String message) {
		super(message);
	}

	public ElementoNonPersistenteException(Throwable cause) {
		super(cause);
	}

}
