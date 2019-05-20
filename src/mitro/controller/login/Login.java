package mitro.controller.login;

import java.util.Optional;

import mitro.exceptions.OperationException;
import mitro.model.Utente;

public interface Login {

	public boolean autentica(String username, String password) throws OperationException;
	public boolean disconnetti() throws OperationException;
	public Optional<Utente> getUtenteAutenticato();
	
}
