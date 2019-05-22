package mitro.exceptions;

public class ElementoNonModificabileException extends PersistenzaException {

	private static final long serialVersionUID = -8762139008668569854L;

	public ElementoNonModificabileException() {
		
	}

	public ElementoNonModificabileException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ElementoNonModificabileException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElementoNonModificabileException(String message) {
		super(message);
	}

	public ElementoNonModificabileException(Throwable cause) {
		super(cause);
	}

}
