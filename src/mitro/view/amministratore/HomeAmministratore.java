package mitro.view.amministratore;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.amministratore.AmministrazioneClassi;
import mitro.controller.amministratore.AmministrazioneIscritti;
import mitro.exceptions.OperazioneException;
import mitro.model.Ruolo;
import mitro.model.Utente;
import mitro.view.ViewUtenteAstratta;

@SuppressWarnings("unused")
public class HomeAmministratore extends ViewUtenteAstratta {

	private static final long serialVersionUID = -2450523432502584339L;

	public HomeAmministratore() {
		super(Ruolo.AMMINISTRATORE);
	}

	@Override
	protected void gestisciRichiestaGet(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		//AmministrazioneClassi amministrazioneClassi = getAmministrazioneClassi();
		try {
			if("disconnetti".equals(req.getParameter("azione"))) {
				this.eseguiDisconnessione(req, resp);
				return;
			}
			req.setAttribute("nomeAmministratore", "Amministratore scolastico");
		
		}
		catch (OperazioneException e) {
			req.setAttribute("error", String.valueOf(e));
		}
		req.getRequestDispatcher("/amministratore-home.jsp").forward(req, resp);
	}

	@Override
	protected void gestisciRichiestaPost(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		gestisciRichiestaGet(utente, req, resp);
	}

	private AmministrazioneIscritti getAmministrazioneIscritti() {
		return ControllerFactory.getInstance().getAmministrazioneIscritti();
	}
	
	private AmministrazioneClassi getAmministrazioneClassi() {
		return ControllerFactory.getInstance().getAmministrazioneClassi();
	}
	
}
