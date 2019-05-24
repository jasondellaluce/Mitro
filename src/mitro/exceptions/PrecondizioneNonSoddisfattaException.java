package mitro.exceptions;

public class PrecondizioneNonSoddisfattaException extends OperazioneException {

	private static final long serialVersionUID = 1755020803902652706L;

	public PrecondizioneNonSoddisfattaException() {
	
	}

	public PrecondizioneNonSoddisfattaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PrecondizioneNonSoddisfattaException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrecondizioneNonSoddisfattaException(String message) {
		super(message);
	}

	public PrecondizioneNonSoddisfattaException(Throwable cause) {
		super(cause);
	}

}
