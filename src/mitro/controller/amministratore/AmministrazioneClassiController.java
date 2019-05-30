package mitro.controller.amministratore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import mitro.controller.ControllerAstratto;
import mitro.controller.log.LoggerOperazioni;
import mitro.deployment.Configurazione;
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
		this.eseguiLogOperazione("registraClasse, " + classe);
		try {
			daoClasse.registraClasse(classe);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}

	}

	@Override
	public void modificaClasse(Classe classe) throws OperazioneException {
		this.eseguiLogOperazione("modificaClasse, " + classe);
		try {
			daoClasse.modificaClasse(classe);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}

	}

	@Override
	public void registraAttivita(Attivita attivita) throws OperazioneException {
		this.eseguiLogOperazione("registraAttivita, " + attivita);
		if(attivita.getData().atTime(attivita.getOraInizio(), 0)
				.isBefore(LocalDateTime.now(Configurazione.getInstance().getZoneId())))
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
		this.eseguiLogOperazione("eliminaAttivita, " + attivita);
		if(attivita.getData().atTime(attivita.getOraInizio(), 0)
				.isBefore(LocalDateTime.now(Configurazione.getInstance().getZoneId())))
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
		this.eseguiLogOperazione("modificaVoto, " + voto);
		if(voto.getValore() < 0 || voto.getValore() > 10)
			throw new IllegalArgumentException("voto con valore invalido");
		
		try {
			daoArchiviazione.modificaArchiviazione(voto);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Attivita> getListaAttivita(Classe classe) throws OperazioneException {
		this.eseguiLogOperazione("getListaAttivita, " + classe);
		try {
			return daoAttivita.ottieniAttivitaPerClasse(classe);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

	@Override
	public List<Voto> getListaVoti(Studente studente) throws OperazioneException {
		this.eseguiLogOperazione("getListaVoti, " + studente);
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
		this.eseguiLogOperazione("cercaClassi, " + filtro);
		try {
			return daoClasse.ottieniClassiPerNome(filtro.substring(6));
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
	}

}
