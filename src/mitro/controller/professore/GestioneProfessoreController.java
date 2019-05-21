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
import mitro.model.Comunicazione;
import mitro.model.Professore;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOComunicazione;

public class GestioneProfessoreController extends ControllerAstratto implements GestioneProfessore {
	
	private Professore professore;
	private DAOComunicazione daoComunicazione;
	private DAOAttivita daoAttivita;
	private LoggerOperazioni logger;
	
	public GestioneProfessoreController(Professore professore,
			DAOComunicazione daoComunicazione, DAOAttivita daoAttivita,
			LoggerOperazioni logger) {
		super(logger);
		this.professore = professore;
		this.daoComunicazione = daoComunicazione;
		this.daoAttivita = daoAttivita;
	}

	@Override
	public Professore getProfessore() throws OperazioneException {
		return this.professore;
	}

	@Override
	public List<Classe> getListaClassi() throws OperazioneException {
		try {
			return daoAttivita.ottieniAttivitaPerProfessore(professore).stream()
					.map(a -> a.getClasse())
					.distinct()
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Attivita> getListaAttivita(LocalDate from, LocalDate to) throws OperazioneException {
		try {
			return daoAttivita.ottieniAttivitaPerProfessore(professore).stream()
					.filter(a -> a.getData().isAfter(from))
					.filter(a -> a.getData().isBefore(to))
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Comunicazione> getListaComunicazioni(LocalDate from, LocalDate to) throws OperazioneException {
		try {
			return daoComunicazione.ottieniComunicazioniPerDestinatario(professore).stream()
					.filter(c -> c.getDataOra().isAfter(from.atStartOfDay()))
					.filter(c -> c.getDataOra().isBefore(to.atStartOfDay()))
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}
	
}
