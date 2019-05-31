package mitro.view;

import java.util.Optional;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import mitro.controller.login.Login;
import mitro.exceptions.OperazioneException;
import mitro.model.Utente;

public class SessionBindingListenerLogin implements Login, HttpSessionBindingListener {

	private Login delegate;
	
	public SessionBindingListenerLogin(Login delegate) {
		this.delegate = delegate;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		try {
			this.disconnetti();
		}
		catch (OperazioneException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean autentica(String username, String password) throws OperazioneException {
		return delegate.autentica(username, password);
	}

	@Override
	public boolean disconnetti() throws OperazioneException {
		return delegate.disconnetti();
	}

	@Override
	public Optional<Utente> getUtenteAutenticato() throws OperazioneException {
		return delegate.getUtenteAutenticato();
	}

}
