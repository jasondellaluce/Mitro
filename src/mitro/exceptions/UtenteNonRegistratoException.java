package mitro.exceptions;

public class UtenteNonRegistratoException extends OperazioneException {

	private static final long serialVersionUID = -3032765754269099506L;

	public UtenteNonRegistratoException() {
		super();
	}

	public UtenteNonRegistratoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UtenteNonRegistratoException(String message, Throwable cause) {
		super(message, cause);
	}

	public UtenteNonRegistratoException(String message) {
		super(message);
	}

	public UtenteNonRegistratoException(Throwable cause) {
		super(cause);
	}
	
}
