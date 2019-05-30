package mitro.controller.professore;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import mitro.controller.ControllerAstratto;
import mitro.controller.log.LoggerOperazioni;
import mitro.exceptions.OperazioneException;
import mitro.exceptions.PersistenzaException;
import mitro.exceptions.PrecondizioneNonSoddisfattaException;
import mitro.model.Archiviazione;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Presenza;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Voto;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOUtente;

public class GestioneClasseController extends ControllerAstratto implements GestioneClasse {

	private Classe classe;
	private DAOArchiviazione daoArchiviazione;
	private DAOAttivita daoAttivita;
	private DAOUtente daoUtente;
	
	public GestioneClasseController(LoggerOperazioni logger, DAOArchiviazione daoArchiviazione,
			DAOAttivita daoAttivita, DAOUtente daoUtente, Classe classe) {
		super(logger);
		this.daoArchiviazione = daoArchiviazione;
		this.daoAttivita = daoAttivita;
		this.daoUtente = daoUtente;
		this.classe= classe;
	}

	@Override
	public Classe getClasse() throws OperazioneException {
		this.eseguiLogOperazione("getClasse");
		return classe;
	}

	@Override
	public void inserisciAnnotazione(Attivita attivita, String annotazione)
			throws OperazioneException {
		this.eseguiLogOperazione("inserisciAnnotazione, " + attivita);
		try {
			attivita.setAnnotazione(annotazione);
			daoAttivita.modificaAttivita(attivita);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public void inserisciPresenza(Presenza presenza) throws OperazioneException {
		this.eseguiLogOperazione("inserisciPresenza, " + presenza);
		if(!presenza.getAttivita().getClasse().equals(presenza.getStudente().getClasse()))
			throw new PrecondizioneNonSoddisfattaException(
					"studente non partecipante all'attività");
		
		try {
			List<Archiviazione> archiviazioni = daoArchiviazione.ottieniArchiviazioni();
			for(Archiviazione a: archiviazioni)
				if(a instanceof Presenza && a.getAttivita().equals(presenza.getAttivita()) && a.getStudente().equals(presenza.getStudente()))
					daoArchiviazione.eliminaArchiviazione(presenza);
				
			daoArchiviazione.registraArchiviazione(presenza);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public void registraVoto(Voto voto) throws OperazioneException {
		this.eseguiLogOperazione("registraVoto, " + voto);
		if(!voto.getAttivita().getClasse().equals(voto.getStudente().getClasse()))
			throw new IllegalArgumentException("Precondizione insoddisfatta");
		if(voto.getValore() < 0 || voto.getValore() > 10)
			throw new IllegalArgumentException("voto con valore invalido");
		
		try {
			daoArchiviazione.registraArchiviazione(voto);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Studente> getListaStudenti() throws OperazioneException {
		this.eseguiLogOperazione("getListaStudenti");
		try {
			return daoUtente.ottieniUtentiPerRuolo(Ruolo.STUDENTE).stream()
					.filter(u -> u instanceof Studente)
					.map(u -> (Studente) u)
					.filter(s -> Objects.equals(s.getClasse(), classe))
					.sorted(Comparator.comparing(Studente::getNome).thenComparing(Studente::getCognome))
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Attivita> getListaAttivita(LocalDate from, LocalDate to)
			throws OperazioneException {
		this.eseguiLogOperazione("getListaAttivita, " + from + ", " + to);
		try {
			return daoAttivita.ottieniAttivitaPerClasse(classe).stream()
					.filter(a -> a.getData().isAfter(from.minusDays(1))
							&& a.getData().isBefore(to.plusDays(1)))
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Presenza> getListaPresenze(LocalDate from, LocalDate to)
			throws OperazioneException {
		this.eseguiLogOperazione("getListaPresenza, " + from + ", " + to);
		try {
			return daoArchiviazione.ottieniArchiviazioniPerClasse(classe).stream()
					.filter(a -> a instanceof Presenza)
					.map(a -> (Presenza) a)
					.filter(a -> a.getAttivita().getData().isAfter(from.minusDays(1))
							&& a.getAttivita().getData().isBefore(to.plusDays(1)))
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Voto> getListaVoti(LocalDate from, LocalDate to)
			throws OperazioneException {
		this.eseguiLogOperazione("getListaVoti, " + from + ", " + to);
		try {
			return daoArchiviazione.ottieniArchiviazioniPerClasse(classe).stream()
					.filter(a -> a instanceof Voto)
					.map(a -> (Voto) a)
					.filter(a -> a.getAttivita().getData().isAfter(from.minusDays(1))
							&& a.getAttivita().getData().isBefore(to.plusDays(1)))
					.collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}
	
}
