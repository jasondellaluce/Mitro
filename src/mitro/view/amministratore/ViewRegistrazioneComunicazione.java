package mitro.view.amministratore;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.model.Ruolo;
import mitro.model.Utente;
import mitro.view.ViewUtenteAstratta;

public class ViewRegistrazioneComunicazione extends ViewUtenteAstratta {

	private static final long serialVersionUID = -6757561119428211218L;

	public ViewRegistrazioneComunicazione() {
		super(Ruolo.AMMINISTRATORE);
	}
	
	@Override
	protected void gestisciRichiestaGet(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void gestisciRichiestaPost(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
