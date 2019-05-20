package mitro.controller;

import java.time.LocalDateTime;

import mitro.controller.log.LoggerOperazioni;
import mitro.model.Utente;

public abstract class ControllerAstratto {

	private LoggerOperazioni logger;
	
	public ControllerAstratto(LoggerOperazioni logger) {
		this.logger = logger;
	}
	
	/**
	 * Scrive un'operazione richiesta da un utente autenticato
	 * */
	protected void eseguiLogOperazione(Utente utente, String operazione) {
		String voce = LocalDateTime.now() + ", "
				+ utente.getId() + ", "
				+ this.getClass().getSimpleName() + " - "
				+ operazione;
		logger.scrivi(voce);
	}
	
	/**
	 * Scrive un'operazione richiesta da un utente non autenticato
	 * */
	protected void eseguiLogOperazione(String operazione) {
		String voce = LocalDateTime.now() + ", "
				+ "NOID, "
				+ this.getClass().getSimpleName() + " - "
				+ operazione;
		logger.scrivi(voce);
	}
	
}
