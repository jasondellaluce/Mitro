package mitro.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.model.Ruolo;
import mitro.model.Utente;

public class HomeProfessore extends ViewUtenteAstratta {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HomeProfessore(Ruolo ruoloPrivilegio) {
		super(ruoloPrivilegio);
		// TODO Auto-generated constructor stub
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
