package mitro.controller.login;

import java.util.Optional;

import mitro.controller.ControllerAstratto;
import mitro.controller.log.LoggerOperazioni;
import mitro.exceptions.UtenteGiaAutenticatoException;
import mitro.exceptions.UtenteNonRegistratoException;
import mitro.exceptions.OperazioneException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Utente;
import mitro.persistenza.DAOUtente;

public class LoginController extends ControllerAstratto implements Login{

	private DAOUtente daoUtente;
	private Utente utenteAutenticato;
	private PermessoLogin permessoLogin;
	
	public LoginController(LoggerOperazioni logger, PermessoLogin permessoLogin, 
			DAOUtente daoUtente) {
		super(logger);
		this.permessoLogin = permessoLogin;
		this.daoUtente = daoUtente;
	}

	@Override
	public boolean autentica(String username, String password) throws OperazioneException {
		if(username == null || username.length() == 0)
			throw new IllegalArgumentException("username");
		if(password == null || password.length() == 0)
			throw new IllegalArgumentException("password");
		
		/* Controlla autenticazione ripetuta */
		if(getUtenteAutenticato().isPresent())
			return false;
		
		/* Ricerca utente nella persistenza */
		Utente utenteRichiesto = null;
		try {
			utenteRichiesto = daoUtente.ottieniUtentePerCredenziali(username, password);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
		
		/* Controlla che l'utente richiesto sia stato trovato */
		if(utenteRichiesto == null)
			throw new UtenteNonRegistratoException();
		
		/* Controlla sessioni simultanee */
		if(!permessoLogin.ottieniPermesso(utenteRichiesto, this))
			throw new UtenteGiaAutenticatoException();
		
		/* Autenticazione effettuata con successo */
		utenteAutenticato = utenteRichiesto;
		return true;
	}

	@Override
	public boolean disconnetti() throws OperazioneException {
		/* Controlla autenticazione mancata */
		if(!getUtenteAutenticato().isPresent())
			return false;
		
		/* Tenta il rilascio della sessione unica */
		if(!permessoLogin.rilasciaPermesso(utenteAutenticato, this))
			return false;
		
		/* Disconenssione effettuata con successo */
		utenteAutenticato = null;
		return true;
	}

	@Override
	public Optional<Utente> getUtenteAutenticato() {
		return Optional.ofNullable(utenteAutenticato);
	}

}
