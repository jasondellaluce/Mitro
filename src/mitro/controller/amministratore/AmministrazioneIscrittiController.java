package mitro.controller.amministratore;

import java.util.List;

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
		try {
			daoUtente.registraUtente(iscritto);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException();
		}
	}

	@Override
	public void modificaIscritto(Iscritto iscritto) throws OperazioneException {
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
		try {
			daoUtente.inserisciCredenziali(iscritto, username, password);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException();
		}
	}

	@Override
	public void registraComunicazione(Comunicazione comunicazione) throws OperazioneException {
		try {
			daoComunicazione.registraComunicazione(comunicazione);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException();
		}
	}

	@Override
	public List<Iscritto> cercaIscritti(String filtro) throws OperazioneException {
		try {
			return daoUtente.ottieniIscrittiPerNomeOCognome(filtro);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException();
		}
	}

}