package mitro.view.professore;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.model.Ruolo;
import mitro.model.Utente;
import mitro.view.ViewUtenteAstratta;

public class ViewStoricoProfessore extends ViewUtenteAstratta {

	private static final long serialVersionUID = 8415486690574371075L;

	public ViewStoricoProfessore() {
		super(Ruolo.PROFESSORE);
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
