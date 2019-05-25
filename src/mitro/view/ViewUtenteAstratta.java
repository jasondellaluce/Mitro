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
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {		
		Utente utenteAutenticato;
		try {
			utenteAutenticato = ottieniUtenteAutenticato(req, resp);
			gestisciRichiestaGet(utenteAutenticato, req, resp);
		}
		catch (OperazioneException e) {
			apriErroreSconosciuto(req, resp);
		}
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		Utente utenteAutenticato;
		try {
			utenteAutenticato = ottieniUtenteAutenticato(req, resp);
			gestisciRichiestaPost(utenteAutenticato, req, resp);
		}
		catch (OperazioneException e) {
			apriErroreSconosciuto(req, resp);
		}
	}
	
	protected void eseguiDisconnessione(HttpServletRequest req, HttpServletResponse resp)
			throws OperazioneException, IOException, ServletException {
		HttpSession sessioneUtente = req.getSession();
		
		/* Controlla Login non eseguito */
		if(sessioneUtente.getAttribute(nomeAttributoLogin) == null) {
			return;
		}
		
		/* Controlla se l'attributo Login è compromesso */
		if(!(sessioneUtente.getAttribute(nomeAttributoLogin) instanceof Login)) {
			sessioneUtente.removeAttribute(nomeAttributoLogin);
			return;
		}
		
		/* Controlla se il cliente è stato autenticato */
		Login loginUtente = (Login) sessioneUtente.getAttribute(nomeAttributoLogin);
		if(!loginUtente.getUtenteAutenticato().isPresent()) {
			return;
		}
		
		/* Esegui disconnessione */
		if(!loginUtente.disconnetti()) {
			apriErrorePrivilegio(req, resp);
			return;
		}
		apriLogin(req, resp);
	}
	
	private Utente ottieniUtenteAutenticato(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, OperazioneException, ServletException {
		HttpSession sessioneUtente = req.getSession();
		
		/* Controlla Login non eseguito */
		if(sessioneUtente.getAttribute(nomeAttributoLogin) == null) {
			apriLogin(req, resp);
			return null;
		}
		
		/* Controlla se l'attributo Login è compromesso */
		if(!(sessioneUtente.getAttribute(nomeAttributoLogin) instanceof Login)) {
			sessioneUtente.removeAttribute(nomeAttributoLogin);
			apriLogin(req, resp);
			return null;
		}
		
		/* Controlla se il cliente è stato autenticato */
		Login loginUtente = (Login) sessioneUtente.getAttribute(nomeAttributoLogin);
		if(!loginUtente.getUtenteAutenticato().isPresent()) {
			apriLogin(req, resp);
			return null;
		}
		
		/* Controlla se il cliente possiede i privilegi adeguati */
		Utente utente = loginUtente.getUtenteAutenticato().get();
		if(!utente.getRuolo().equals(ruoloPrivilegio)) {
			apriErrorePrivilegio(req, resp);
			return null;
		}
		
		/* L'autenticazione è considerata corretta */
		return utente;
	}
	
	/* Ridirige alla pagina di login */
	private void apriLogin(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {
		req.setAttribute("error", "Non sei ancora stato autenticato");
		req.getRequestDispatcher("/login").forward(req, resp);
	}
	
	/* Ridirige alla pagina di errore per mancato privilegio */
	private void apriErrorePrivilegio(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {
		req.setAttribute("error", "Non possiedi i privilegi necessari");
		req.getRequestDispatcher("/login").forward(req, resp);
	}
	
	/* Ridirige alla pagina di errore per mancato privilegio */
	private void apriErroreSconosciuto(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {
		req.setAttribute("error", "Abbiamo riscontrato un errore sconosciuto");
		req.getRequestDispatcher("/login").forward(req, resp);
	}
	
	protected abstract void gestisciRichiestaGet(Utente utente, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException;
	
	protected abstract void gestisciRichiestaPost(Utente utente, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException;
	
}
