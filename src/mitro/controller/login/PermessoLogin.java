package mitro.controller.login;

import mitro.exceptions.OperazioneException;
import mitro.model.Utente;

public interface PermessoLogin {

	boolean ottieniPermesso(Utente utente, Login richiedente) throws OperazioneException;
	boolean rilasciaPermesso(Utente utente, Login richiedente) throws OperazioneException;

}