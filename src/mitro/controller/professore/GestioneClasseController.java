package mitro.controller.professore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import mitro.controller.ControllerAstratto;
import mitro.controller.log.LoggerOperazioni;
import mitro.exceptions.OperazioneException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Archiviazione;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Presenza;
import mitro.model.Studente;
import mitro.model.Voto;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOUtente;

public class GestioneClasseController extends ControllerAstratto implements GestioneClasse {

	private Classe classe;
	private DAOArchiviazione daoArchiviazione;
	private DAOUtente daoUtente;
	private DAOAttivita daoAttivita;
	private LoggerOperazioni logger;
	
	public GestioneClasseController(DAOArchiviazione daoArchiviazione,
			DAOUtente daoUtente, DAOAttivita daoAttivita, LoggerOperazioni logger) {
		super(logger);
		this.daoArchiviazione = daoArchiviazione;
		this.daoUtente = daoUtente;
		this.daoAttivita = daoAttivita;
	}

	@Override
	public Classe getClasse() throws OperazioneException {
		return classe;
	}

	@Override
	public void inserisciAnnotazione(Attivita attivita, String annotazione) throws OperazioneException {
		attivita.setAnnotazione(annotazione);
	}

	@Override
	public void inserisciPresenza(Presenza presenza) throws OperazioneException,
			PersistenzaException {
		daoArchiviazione.registraArchiviazione(presenza);
	}

	@Override
	public void registraVoto(Voto voto) throws OperazioneException,
			PersistenzaException {
		daoArchiviazione.registraArchiviazione(voto);
	}

	@Override
	public List<Studente> getListaStudenti() throws OperazioneException,
			PersistenzaException {
		List<Studente> listaStudenti = new ArrayList<Studente>();
		for (Archiviazione archiviazione : daoArchiviazione.ottieniArchiviazioniPerClasse(classe)) {
			listaStudenti.add(archiviazione.getStudente());
		}
		return listaStudenti;
	}

	@Override
	public List<Attivita> getListaAttivita(LocalDate from, LocalDate to)
			throws OperazioneException, PersistenzaException {
		List<Attivita> listaAttivita = new ArrayList<Attivita>();
		for (Attivita attivita : daoAttivita.ottieniAttivitaPerClasse(classe)) {
			if (attivita.getData().isAfter(from) && attivita.getData().isBefore(to))
				listaAttivita.add(attivita);
		}
		return listaAttivita;
	}

	@Override
	public List<Presenza> getListaPresenze(LocalDate from, LocalDate to)
			throws OperazioneException, PersistenzaException {
		List<Presenza> listaPresenze = new ArrayList<Presenza>();
		for (Archiviazione archiviazione : daoArchiviazione.ottieniArchiviazioniPerClasse(classe)) {
			if (archiviazione instanceof Presenza)
				listaPresenze.add((Presenza) archiviazione);
		}
		return listaPresenze;
	}

	@Override
	public List<Voto> getListaVoti(LocalDate from, LocalDate to) throws OperazioneException,
			PersistenzaException {
		List<Voto> listaVoti = new ArrayList<Voto>();
		for (Archiviazione archiviazione : daoArchiviazione.ottieniArchiviazioniPerClasse(classe)) {
			if (archiviazione instanceof Presenza)
				listaVoti.add((Voto) archiviazione);
		}
		return listaVoti;
	}
	
}
