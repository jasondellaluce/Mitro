package mitro.controller.login;

import mitro.model.Utente;

public class MockPermessoLogin implements PermessoLogin {

	private boolean always;
	
	@Override
	public boolean ottieniPermesso(Utente utente, Login richiedente) {
		return always;
	}

	@Override
	public boolean rilasciaPermesso(Utente utente, Login richiedente) {
		return always;
	}

	public boolean isSempre() {
		return always;
	}

	public void setSempre(boolean always) {
		this.always = always;
	}

}
