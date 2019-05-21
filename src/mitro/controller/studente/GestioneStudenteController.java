package mitro.controller.studente;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import mitro.controller.ControllerAstratto;
import mitro.controller.log.LoggerOperazioni;
import mitro.exceptions.OperazioneException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Attivita;
import mitro.model.Comunicazione;
import mitro.model.Presenza;
import mitro.model.Studente;
import mitro.model.Voto;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOComunicazione;

public class GestioneStudenteController extends ControllerAstratto implements GestioneStudente {

	private Studente studente;
	private DAOComunicazione daoComunicazione;
	private DAOArchiviazione daoArchiviazione;
	private DAOAttivita daoAttivita;

	public GestioneStudenteController(LoggerOperazioni logger, DAOComunicazione daoComunicazione,
			DAOArchiviazione daoArchiviazione, DAOAttivita daoAttivita, Studente studente) {		
		super(logger);
		this.daoComunicazione = daoComunicazione;
		this.daoArchiviazione = daoArchiviazione;
		this.daoAttivita = daoAttivita;
		this.studente=studente;
	}

	@Override
	public Studente getStudente() throws OperazioneException {		
		return studente;		
	}

	@Override
	public List<Attivita> getListaAttivita(LocalDate startDate, LocalDate endDate) throws OperazioneException {
		try {
			return daoAttivita.ottieniAttivitaPerStudente(getStudente()).stream()
					.filter(a -> a.getData().isAfter(startDate))
					.filter(a -> a.getData().isBefore(endDate))
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Comunicazione> getListaComunicazioni(LocalDate startDate, LocalDate endDate)
			throws OperazioneException {
		try {
			return daoComunicazione.ottieniComunicazioniPerDestinatario(studente).stream()
					.filter(a -> a.getDataOra().toLocalDate().isAfter(startDate))
					.filter(a -> a.getDataOra().toLocalDate().isBefore(endDate))
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Voto> getListaVoti(LocalDate startDate, LocalDate endDate) throws OperazioneException {
		try {
			return daoArchiviazione.ottieniArchiviazioniPerStudente(studente).stream()
					.filter(a -> a instanceof Voto)
					.map(a -> (Voto) a)
					.filter(a -> a.getAttivita().getData().isAfter(startDate))
					.filter(a -> a.getAttivita().getData().isBefore(endDate))
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Presenza> getListaPresenze(LocalDate startDate, LocalDate endDate) throws OperazioneException {
		try {
			return daoArchiviazione.ottieniArchiviazioniPerStudente(studente).stream()
					.filter(a -> a instanceof Presenza)
					.map(a -> (Presenza) a)
					.filter(a -> a.getAttivita().getData().isAfter(startDate))
					.filter(a -> a.getAttivita().getData().isBefore(endDate))
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

}
