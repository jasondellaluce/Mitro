package mitro.controller.amministratore;

import java.util.List;
import java.util.stream.Collectors;

import mitro.controller.ControllerAstratto;
import mitro.controller.log.LoggerOperazioni;
import mitro.exceptions.OperazioneException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Comunicazione;
import mitro.model.Iscritto;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.DAOUtente;

public class AmministrazioneIscrittiController extends ControllerAstratto 
			implements AmministrazioneIscritti {
	
	private DAOUtente daoUtente;
	private DAOComunicazione daoComunicazione;

	public AmministrazioneIscrittiController(LoggerOperazioni logger, DAOUtente daoUtente,
			DAOComunicazione daoComunicazione) {
		super(logger);
		this.daoUtente = daoUtente;
		this.daoComunicazione = daoComunicazione;
	}

	@Override
	public void registraIscritto(Iscritto iscritto) throws OperazioneException {
		this.eseguiLogOperazione("registraIscritto, " + iscritto);
		try {
			daoUtente.registraUtente(iscritto);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException();
		}
	}

	@Override
	public void modificaIscritto(Iscritto iscritto) throws OperazioneException {
		this.eseguiLogOperazione("modificaIscritto, " + iscritto);
		try {
			daoUtente.modificaUtente(iscritto);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException();
		}
	}

	@Override
	public void inserisciCredenzialiIscritto(Iscritto iscritto, String username, String password)
			throws OperazioneException {
		this.eseguiLogOperazione("inserisciCredenzialiIscritto, " + iscritto + ", " + username
				+ ", " + password);
		try {
			daoUtente.inserisciCredenziali(iscritto, username, password);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException();
		}
	}

	@Override
	public void registraComunicazione(Comunicazione comunicazione) throws OperazioneException {
		this.eseguiLogOperazione("registraComunicazione, " + comunicazione);
		try {
			daoComunicazione.registraComunicazione(comunicazione);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException();
		}
	}

	@Override
	public List<Iscritto> cercaIscritti(String filtro) throws OperazioneException {
		this.eseguiLogOperazione("cercaIscritti, " + filtro);
		try {
			if(filtro!=null) {
				return daoUtente.ottieniIscrittiPerNomeOCognome(filtro);
			}
			else {
				List<Iscritto> iscritti= daoUtente.ottieniUtenti().stream().filter(o -> o instanceof Iscritto)
						.map(o -> (Iscritto)o)
						.collect(Collectors.toList());
				return iscritti;
			}
		}
		catch (PersistenzaException e) {
			throw new OperazioneException();
		}
	}

}
