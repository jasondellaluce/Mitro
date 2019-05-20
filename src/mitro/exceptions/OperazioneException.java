package mitro.exceptions;

public class OperazioneException extends Exception {

	private static final long serialVersionUID = 7329572230129311174L;

	public OperazioneException() {
		super();
	}

	public OperazioneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OperazioneException(String message, Throwable cause) {
		super(message, cause);
	}

	public OperazioneException(String message) {
		super(message);
	}

	public OperazioneException(Throwable cause) {
		super(cause);
	}
	
}
