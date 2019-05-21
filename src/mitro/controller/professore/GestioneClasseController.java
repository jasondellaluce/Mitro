package mitro.controller.professore;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import mitro.controller.ControllerAstratto;
import mitro.controller.log.LoggerOperazioni;
import mitro.exceptions.OperazioneException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Presenza;
import mitro.model.Studente;
import mitro.model.Voto;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;

public class GestioneClasseController extends ControllerAstratto implements GestioneClasse {

	private Classe classe;
	private DAOArchiviazione daoArchiviazione;
	private DAOAttivita daoAttivita;
	private LoggerOperazioni logger;
	
	public GestioneClasseController(DAOArchiviazione daoArchiviazione,
			DAOAttivita daoAttivita, LoggerOperazioni logger) {
		super(logger);
		this.daoArchiviazione = daoArchiviazione;
		this.daoAttivita = daoAttivita;
	}

	@Override
	public Classe getClasse() throws OperazioneException {
		return classe;
	}

	@Override
	public void inserisciAnnotazione(Attivita attivita, String annotazione)
			throws OperazioneException {
		attivita.setAnnotazione(annotazione);
	}

	@Override
	public void inserisciPresenza(Presenza presenza) throws OperazioneException {
		try {
			daoArchiviazione.registraArchiviazione(presenza);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public void registraVoto(Voto voto) throws OperazioneException {
		try {
			daoArchiviazione.registraArchiviazione(voto);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Studente> getListaStudenti() throws OperazioneException {
		try {
			return daoArchiviazione.ottieniArchiviazioniPerClasse(classe).stream()
					.map(a -> a.getStudente())
					.distinct()
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Attivita> getListaAttivita(LocalDate from, LocalDate to)
			throws OperazioneException {
		try {
			return daoAttivita.ottieniAttivitaPerClasse(classe).stream()
					.filter(a -> a.getData().isAfter(from))
					.filter(a -> a.getData().isBefore(to))
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Presenza> getListaPresenze(LocalDate from, LocalDate to)
			throws OperazioneException {
		try {
			return daoArchiviazione.ottieniArchiviazioniPerClasse(classe).stream()
					.filter(a -> a instanceof Presenza)
					.map(a -> (Presenza) a)
					.filter(a -> a.getAttivita().getData().isAfter(from))
					.filter(a -> a.getAttivita().getData().isBefore(to))
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Voto> getListaVoti(LocalDate from, LocalDate to)
			throws OperazioneException {
		try {
			return daoArchiviazione.ottieniArchiviazioniPerClasse(classe).stream()
					.filter(a -> a instanceof Voto)
					.map(a -> (Voto) a)
					.filter(a -> a.getAttivita().getData().isAfter(from))
					.filter(a -> a.getAttivita().getData().isBefore(to))
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}
	
}
