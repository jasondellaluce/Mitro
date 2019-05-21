package mitro.controller.professore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
	public List<Classe> getListaClassi() throws OperazioneException, PersistenzaException {
		List<Classe> listaClassi = new ArrayList<Classe>();
		for (Attivita attivita : daoAttivita.ottieniAttivitaPerProfessore(professore)) {
			if (!listaClassi.contains(attivita.getClasse()))
				listaClassi.add(attivita.getClasse());
		}
		return listaClassi;
	}

	@Override
	public List<Attivita> getListaAttivita(LocalDate from, LocalDate to) throws OperazioneException,
			PersistenzaException {
		List<Attivita> listaAttivita = new ArrayList<Attivita>();
		for (Attivita attivita : daoAttivita.ottieniAttivitaPerProfessore(professore)) {
			if (attivita.getData().isAfter(from) && attivita.getData().isBefore(to))
				listaAttivita.add(attivita);
		}
		return listaAttivita;
	}

	@Override
	public List<Comunicazione> getListaComunicazioni(LocalDate from, LocalDate to) throws OperazioneException,
			PersistenzaException {
		List<Comunicazione> listaComunicazioni = new ArrayList<Comunicazione>();
		for (Comunicazione comunicazione : daoComunicazione.ottieniComunicazioniPerDestinatario(professore)) {
			if (comunicazione.getDataOra().isAfter(from.atStartOfDay())
					&& comunicazione.getDataOra().isBefore(to.atStartOfDay()))
				listaComunicazioni.add(comunicazione);
		}
		return listaComunicazioni;
	}
	
}
