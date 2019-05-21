package mitro.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mitro.controller.login.Login;
import mitro.exceptions.OperazioneException;
import mitro.model.Ruolo;
import mitro.model.Utente;

public abstract class ViewUtenteAstratta extends ViewAstratta {

	private static final long serialVersionUID = -2194665168250919145L;
	private Ruolo ruoloPrivilegio;
	
	public ViewUtenteAstratta(Ruolo ruoloPrivilegio) {
		this.ruoloPrivilegio = ruoloPrivilegio;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		Utente utenteAutenticato;
		try {
			utenteAutenticato = ottieniUtenteAutenticato(req, resp);
			gestisciRichiestaGet(utenteAutenticato, req, resp);
		}
		catch (OperazioneException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		Utente utenteAutenticato;
		try {
			utenteAutenticato = ottieniUtenteAutenticato(req, resp);
			gestisciRichiestaPost(utenteAutenticato, req, resp);
		}
		catch (OperazioneException e) {
			e.printStackTrace();
		}
	}
	
	private Utente ottieniUtenteAutenticato(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, OperazioneException {
		HttpSession sessioneUtente = req.getSession();
		
		/* Controlla Login non eseguito */
		if(sessioneUtente.getAttribute(nomeAttributoLogin) == null) {
			apriLogin(req, resp);
		}
		
		/* Controlla se l'attributo Login è compromesso */
		if(!(sessioneUtente.getAttribute(nomeAttributoLogin) instanceof Login)) {
			sessioneUtente.removeAttribute(nomeAttributoLogin);
			apriLogin(req, resp);
		}
		
		/* Controlla se il cliente è stato autenticato */
		Login loginUtente = (Login) sessioneUtente.getAttribute(nomeAttributoLogin);
		if(!loginUtente.getUtenteAutenticato().isPresent()) {
			apriLogin(req, resp);
		}
		
		/* Controlla se il cliente possiede i privilegi adeguati */
		Utente utente = loginUtente.getUtenteAutenticato().get();
		if(!utente.getRuolo().equals(ruoloPrivilegio)) {
			apriErrorePrivilegio(req, resp);
		}
		
		/* L'autenticazione è considerata corretta */
		return utente;
	}
	
	/* Ridirige alla pagina di login */
	private void apriLogin(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		resp.sendRedirect("/");
	}
	
	/* Ridirige alla pagina di errore per mancato privilegio */
	private void apriErrorePrivilegio(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		resp.sendRedirect("/");
	}
	
	protected abstract void gestisciRichiestaGet(Utente utente, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException;
	
	protected abstract void gestisciRichiestaPost(Utente utente, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException;
	
}
