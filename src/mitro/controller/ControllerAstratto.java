package mitro.controller;

import mitro.controller.log.LoggerOperazioni;

public abstract class ControllerAstratto {

	private LoggerOperazioni logger;
	
	public ControllerAstratto(LoggerOperazioni logger) {
		this.logger = logger;
	}
	
	protected void eseguiLogOperazione(String operazione) {
		logger.scrivi(operazione);
	}
	
}
