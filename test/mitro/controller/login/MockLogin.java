package mitro.controller.login;

import java.util.Optional;

import mitro.exceptions.OperazioneException;
import mitro.model.Utente;

public class MockLogin implements Login {

	@Override
	public boolean autentica(String username, String password) throws OperazioneException {
		return false;
	}

	@Override
	public boolean disconnetti() throws OperazioneException {
		return false;
	}

	@Override
	public Optional<Utente> getUtenteAutenticato() {
		return Optional.ofNullable(null);
	}

}
