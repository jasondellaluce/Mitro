package mitro.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.log.LoggerMessaggi;
import mitro.model.Attivita;
import mitro.model.Materia;
import mitro.model.Presenza;
import mitro.model.Ruolo;
import mitro.model.Utente;
import mitro.model.Voto;

public class HomeProfessore extends ViewUtenteAstratta {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LoggerMessaggi logger;

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
	
	public List<Materia> getListaMaterie() {
		return null;
	}
	
	public List<Voto> getListaVoti() {
		return null;
	}
	
	public List<Presenza> getListaPresenza() {
		return null;
	}
	
	public List<Attivita> getListaAttivita(LocalDate from, LocalDate to) {
		return null;
	}

}
