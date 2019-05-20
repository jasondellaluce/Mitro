package mitro.controller.log;

import java.time.LocalDateTime;
import java.util.List;

import mitro.exceptions.OperazioneException;
import mitro.model.Log;

public interface GestioneLog {

	public Log getLog(LocalDateTime from, LocalDateTime to) throws OperazioneException;
	public List<String> getAnomalieMessaggi(LocalDateTime from, LocalDateTime to) throws OperazioneException;
	public List<String> getAnomalieOperazioni(LocalDateTime from, LocalDateTime to) throws OperazioneException;
	
}
