package mitro.controller.login;

import mitro.model.Utente;

public interface PermessoLogin {

	boolean ottieniPermesso(Utente utente, Login richiedente);
	boolean rilasciaPermesso(Utente utente, Login richiedente);

}