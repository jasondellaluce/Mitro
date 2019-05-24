package mitro.controller.amministratore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import mitro.controller.ControllerAstratto;
import mitro.controller.deployment.Configurazione;
import mitro.controller.log.LoggerOperazioni;
import mitro.exceptions.OperazioneException;
import mitro.exceptions.PersistenzaException;
import mitro.exceptions.PrecondizioneNonSoddisfattaException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Studente;
import mitro.model.Voto;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOClasse;

public class AmministrazioneClassiController extends ControllerAstratto implements AmministrazioneClassi {

	private DAOClasse daoClasse;
	private DAOArchiviazione daoArchiviazione;
	private DAOAttivita daoAttivita;
	
	public AmministrazioneClassiController(LoggerOperazioni logger, DAOClasse daoClasse, DAOArchiviazione daoArchiviazione, DAOAttivita daoAttivita) {
		super(logger);
		this.daoClasse = daoClasse;
		this.daoArchiviazione = daoArchiviazione;
		this.daoAttivita = daoAttivita;
	}
	
	@Override
	public void registraClasse(Classe classe) throws OperazioneException {
		try {
			daoClasse.registraClasse(classe);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}

	}

	@Override
	public void modificaClasse(Classe classe) throws OperazioneException {
		try {
			daoClasse.modificaClasse(classe);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}

	}

	@Override
	public void registraAttivita(Attivita attivita) throws OperazioneException {
		if(attivita.getData().atTime(attivita.getOraInizio(), 0)
				.isBefore(LocalDateTime.now(Configurazione.ZONE_ID)))
			throw new PrecondizioneNonSoddisfattaException("attivita passata");
		try {
			daoAttivita.registraAttivita(attivita);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}

	}

	@Override
	public void eliminaAttivita(Attivita attivita) throws OperazioneException {
		if(attivita.getData().atTime(attivita.getOraInizio(), 0)
				.isBefore(LocalDateTime.now(Configurazione.ZONE_ID)))
			throw new PrecondizioneNonSoddisfattaException("attivita passata");
		try {
			daoAttivita.eliminaAttivita(attivita);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}

	}

	@Override
	public void modificaVoto(Voto voto) throws OperazioneException {
		try {
			daoArchiviazione.modificaArchiviazione(voto);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Attivita> getListaAttivita(Classe classe) throws OperazioneException {
		try {
			return daoAttivita.ottieniAttivitaPerClasse(classe);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Voto> getListaVoti(Studente studente) throws OperazioneException {
		try {
			return daoArchiviazione.ottieniArchiviazioniPerStudente(studente).stream()
				   .filter(o -> o instanceof Voto)
				   .map(o -> (Voto) o)
				   .collect(Collectors.toList());
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Classe> cercaClassi(String filtro) throws OperazioneException {
		try {
			return daoClasse.ottieniClassiPerNome(filtro.substring(6));
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

}
