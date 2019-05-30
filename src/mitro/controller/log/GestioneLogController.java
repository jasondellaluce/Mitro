package mitro.controller.log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import mitro.controller.ControllerAstratto;
import mitro.exceptions.OperazioneException;
import mitro.model.Log;
import mitro.model.VoceLog;
import mitro.model.VoceMessaggioLog;
import mitro.model.VoceOperazioneLog;

public class GestioneLogController extends ControllerAstratto implements GestioneLog {

	private AnalisiMessaggi analisiMessaggi;
	private AnalisiOperazioni analisiOperazioni;
	private String nomeFileLogOperazioni;
	private String nomeFileLogMessaggi;
	
	public GestioneLogController(LoggerOperazioni logger, String nomeFileLogOperazioni, 
			String nomeFileLogMessaggi, AnalisiOperazioni analisiOperazioni,
			AnalisiMessaggi analisiMessaggi) {
		super(logger);
		this.analisiMessaggi = analisiMessaggi;
		this.analisiOperazioni = analisiOperazioni;
		this.nomeFileLogOperazioni = nomeFileLogOperazioni;
		this.nomeFileLogMessaggi = nomeFileLogMessaggi;
	}

	@Override
	public Log getLog(LocalDateTime from, LocalDateTime to) throws OperazioneException {
		this.eseguiLogOperazione("getLog, " + from + ", " + to);
		List<VoceLog> listaVoci = new ArrayList<>(1000);
		String line;
		
		/* Leggi operazioni */
		try (BufferedReader reader = new BufferedReader(new FileReader(nomeFileLogOperazioni))) {
			while((line = reader.readLine()) != null) {
				VoceOperazioneLog voce = parseVoceOperazione(line);
				if(voce.getDataOra().isAfter(from) && voce.getDataOra().isBefore(to))
					listaVoci.add(voce);
			}
		}
		catch (Exception e) {
			throw new OperazioneException(e);
		}
		
		/* Leggi messaggi */
		try (BufferedReader reader = new BufferedReader(new FileReader(nomeFileLogMessaggi))) {
			while((line = reader.readLine()) != null) {
				VoceMessaggioLog voce = parseVoceMessaggio(line);
				if(voce.getDataOra().isAfter(from) && voce.getDataOra().isBefore(to))
					listaVoci.add(voce);
			}
		}
		catch (Exception e) {
			throw new OperazioneException(e);
		}
		
		/* Ordina le voci */
		listaVoci.sort(Comparator.comparing(VoceLog::getDataOra));
		return new Log(listaVoci);
	}

	@Override
	public List<String> getAnomalieMessaggi(LocalDateTime from, LocalDateTime to) throws OperazioneException {
		this.eseguiLogOperazione("getAnomalieMessaggi, " + from + ", " + to);
		List<VoceMessaggioLog> elencoVoci = getLog(from, to).getListaVoci(from, to).stream()
				.filter(v -> v instanceof VoceMessaggioLog)
				.map(v -> (VoceMessaggioLog) v)
				.collect(Collectors.toList());
		return analisiMessaggi.analizza(elencoVoci);
	}

	@Override
	public List<String> getAnomalieOperazioni(LocalDateTime from, LocalDateTime to) throws OperazioneException {
		this.eseguiLogOperazione("getAnomalieOperazioni, " + from + ", " + to);
		List<VoceOperazioneLog> elencoVoci = getLog(from, to).getListaVoci(from, to).stream()
				.filter(v -> v instanceof VoceOperazioneLog)
				.map(v -> (VoceOperazioneLog) v)
				.collect(Collectors.toList());
		return analisiOperazioni.analizza(elencoVoci);
	}
	
	private VoceOperazioneLog parseVoceOperazione(String line) {
		StringTokenizer stk = new StringTokenizer(line, ",");
		LocalDateTime dataOra = LocalDateTime.parse(stk.nextToken().trim());
		String resto = stk.nextToken("\n").substring(1).trim();
		return new VoceOperazioneLog(dataOra, resto);
	}
	
	private VoceMessaggioLog parseVoceMessaggio(String line) {
		StringTokenizer stk = new StringTokenizer(line, ",");
		LocalDateTime dataOra = LocalDateTime.parse(stk.nextToken().trim());
		String resto = stk.nextToken("\n").substring(1).trim();
		return new VoceMessaggioLog(dataOra, resto);
	}
}
