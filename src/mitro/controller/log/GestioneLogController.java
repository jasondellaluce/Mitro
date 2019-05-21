package mitro.controller.log;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import mitro.controller.ControllerAstratto;
import mitro.exceptions.OperazioneException;
import mitro.model.Log;
import mitro.model.VoceMessaggioLog;
import mitro.model.VoceOperazioneLog;

public class GestioneLogController extends ControllerAstratto implements GestioneLog {

	private AnalisiMessaggi analisiMessaggi;
	private AnalisiOperazioni analisiOperazioni;

	public GestioneLogController(LoggerOperazioni logger, AnalisiMessaggi analisiMessaggi,
			AnalisiOperazioni analisiOperazioni) {
		super(logger);
		this.analisiMessaggi = analisiMessaggi;
		this.analisiOperazioni = analisiOperazioni;
	}

	@Override
	public Log getLog(LocalDateTime from, LocalDateTime to) throws OperazioneException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAnomalieMessaggi(LocalDateTime from, LocalDateTime to) throws OperazioneException {
		List<VoceMessaggioLog> elencoVoci = getLog(from, to).getListaVoci(from, to).stream()
				.filter(v -> v instanceof VoceMessaggioLog)
				.map(v -> (VoceMessaggioLog) v)
				.collect(Collectors.toList());
		return analisiMessaggi.analizza(elencoVoci);
	}

	@Override
	public List<String> getAnomalieOperazioni(LocalDateTime from, LocalDateTime to) throws OperazioneException {
		List<VoceOperazioneLog> elencoVoci = getLog(from, to).getListaVoci(from, to).stream()
				.filter(v -> v instanceof VoceOperazioneLog)
				.map(v -> (VoceOperazioneLog) v)
				.collect(Collectors.toList());
		return analisiOperazioni.analizza(elencoVoci);
	}
}
