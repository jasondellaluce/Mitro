package mitro.view;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.log.LoggerMessaggi;
import mitro.deployment.Configurazione;
import mitro.model.Utente;

public abstract class ViewAstratta extends HttpServlet {

	private static final long serialVersionUID = 3651220875737606801L;
	protected static final String nomeAttributoLogin = "login";
	private LoggerMessaggi logger;
	
	public ViewAstratta() {
		this.logger = ControllerFactory.getInstance().getLoggerMessaggi();
	}
	
	@Override
	protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException;

	@Override
	protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException;
	
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		Configurazione.getInstance().setPercorsoEsecuzione(getServletConfig().getServletContext().getRealPath(""));
		super.service(arg0, arg1);
	}

	/**
	 * Scrive un messaggio ricevuto da un utente autenticato
	 * */
	protected void eseguiLogMessaggioRicevuto(Utente utente, String messaggio) {
		String voce = LocalDateTime.now(Configurazione.getInstance().getZoneId()) + ", "
				+ "R, "
				+ utente.getId() + ", "
				+ messaggio;
		logger.scrivi(voce);
	}
	
	/**
	 * Scrive un messaggio ricevuto da un utente non autenticato
	 * */
	protected void eseguiLogMessaggioRicevuto(String messaggio) {
		String voce = LocalDateTime.now(Configurazione.getInstance().getZoneId()) + ", "
				+ "R, "
				+ "NOID, "
				+ this.getClass().getSimpleName() + ", "
				+ messaggio;
		logger.scrivi(voce);
	}
	
	/**
	 * Scrive un messaggio ricevuto inviato ad un utente non autenticato
	 * */
	protected void eseguiLogMessaggioInviato(Utente utente, String messaggio) {
		String voce = LocalDateTime.now(Configurazione.getInstance().getZoneId()) + ", "
				+ "I, "
				+ this.getClass().getSimpleName() + ", "
				+ messaggio;
		logger.scrivi(voce);
	}
	
	/**
	 * Scrive un messaggio ricevuto inviato ad un utente non autenticato
	 * */
	protected void eseguiLogMessaggioInviato(String messaggio) {
		String voce = LocalDateTime.now(Configurazione.getInstance().getZoneId()) + ", "
				+ "I, "
				+ this.getClass().getSimpleName() + ", "
				+ messaggio;
		logger.scrivi(voce);
	}
	
}
