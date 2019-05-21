package mitro.controller.login;

import java.util.Optional;

import mitro.exceptions.OperazioneException;
import mitro.model.Utente;

public interface Login {

	public boolean autentica(String username, String password) throws OperazioneException;
	public boolean disconnetti() throws OperazioneException;
	public Optional<Utente> getUtenteAutenticato() throws OperazioneException;
	
}
