package mitro.exceptions;

public class UtenteGiaAutenticatoException extends OperazioneException {

	private static final long serialVersionUID = -9058083312376552734L;

	public UtenteGiaAutenticatoException() {
		super();
	}

	public UtenteGiaAutenticatoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UtenteGiaAutenticatoException(String message, Throwable cause) {
		super(message, cause);
	}

	public UtenteGiaAutenticatoException(String message) {
		super(message);
	}

	public UtenteGiaAutenticatoException(Throwable cause) {
		super(cause);
	}

}
