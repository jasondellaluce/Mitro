package mitro.controller.log;

import java.time.LocalDateTime;
import java.util.List;

import mitro.controller.OperationException;
import mitro.model.Log;

public interface GestioneLog {

	public Log getLog(LocalDateTime from, LocalDateTime to) throws OperationException;
	public List<String> getAnomalieMessaggi(LocalDateTime from, LocalDateTime to) throws OperationException;
	public List<String> getAnomalieOperazioni(LocalDateTime from, LocalDateTime to) throws OperationException;
	
}
