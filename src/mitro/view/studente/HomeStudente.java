package mitro.view.studente;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.log.LoggerMessaggi;
import mitro.controller.studente.GestioneStudente;
import mitro.deployment.Configurazione;
import mitro.exceptions.OperazioneException;
import mitro.model.Attivita;
import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.view.ViewUtenteAstratta;

public class HomeStudente extends ViewUtenteAstratta {

	private static final long serialVersionUID = -5919910173044717518L;

	public HomeStudente() {
		super(Ruolo.STUDENTE);
	}

	@Override
	protected void gestisciRichiestaGet(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GestioneStudente gestioneStudente = ControllerFactory.getInstance().getGestioneStudente((Studente) utente);
		LoggerMessaggi loggerMex= getLoggerMessaggi();
		
		try {	
			
			String log= LocalDateTime.now(Configurazione.getInstance().getZoneId()) + ", "
					+ utente.getId() + ", "
					+ ((Studente)utente).getNome()+" "+((Studente)utente).getCognome()
					+ "HomeStudente,get ";
			Enumeration parametri=req.getParameterNames();
			while(parametri.hasMoreElements()) {
				String param=(String)parametri.nextElement();
				log+= param+": "+req.getParameter(param)+" ";
			}
			
			loggerMex.scrivi(log);
			
			
			if("disconnetti".equals(req.getParameter("azione"))) {
				this.eseguiDisconnessione(req, resp);
				return;
			}
			req.setAttribute("nomeStudente", gestioneStudente.getStudente().getNome()
					+ " " + gestioneStudente.getStudente().getCognome());
			
			LocalDate inizioSett = LocalDate.now(Configurazione.getInstance().getZoneId()).with(DayOfWeek.MONDAY);
			if(req.getParameter("inizioSett") != null)
				inizioSett = LocalDate.parse(req.getParameter("inizioSett"));
			
			if(req.getParameter("selAtt") != null) {
				StringTokenizer stk = new StringTokenizer(req.getParameter("selAtt"), ";");
				LocalDate date = LocalDate.parse(stk.nextToken().trim());
				inizioSett = date.with(DayOfWeek.MONDAY);
			}
			
			req.setAttribute("inizioSett", inizioSett);
			req.setAttribute("fineSett", inizioSett.plusDays(6));
			req.setAttribute("prossimaSett", URLEncoder.encode(inizioSett.plusDays(7).toString(), "UTF-8"));
			req.setAttribute("precedenteSett", URLEncoder.encode(inizioSett.minusDays(7).toString(), "UTF-8"));		
			
			List<Attivita> listaAttivita = gestioneStudente.getListaAttivita(inizioSett, inizioSett.plusDays(6));
			for(Attivita a : listaAttivita) {
				String testo = a.getClasse().getNome() + " - " + a.getMateria().getNome();
				req.setAttribute("att" + (a.getData().getDayOfWeek().ordinal() + 1) + "-" + a.getOraInizio(), testo);
				req.setAttribute("selAtt" + (a.getData().getDayOfWeek().ordinal() + 1) + "-" + a.getOraInizio(), 
						URLEncoder.encode(a.getData() + ";" + a.getOraInizio() + ";" + a.getClasse().getId(), "UTF-8"));
			}
			
			if(req.getParameter("selAtt") != null) {
				StringTokenizer stk = new StringTokenizer(req.getParameter("selAtt"), ";");
				LocalDate date = LocalDate.parse(stk.nextToken().trim());
				int time = Integer.parseInt(stk.nextToken().trim());
				String classeId = stk.nextToken().trim();
				req.setAttribute("colorAtt" + (date.getDayOfWeek().ordinal() + 1) + "-" + time, "yes");
				listaAttivita.stream()
					.filter(a -> a.getData().equals(date) && a.getOraInizio() == time && a.getClasse().getId().equals(classeId))
					.findAny().ifPresent(a -> {
						req.setAttribute("annotazione", a.getAnnotazione());
					});
			}
		}
		catch (OperazioneException e) {
			req.setAttribute("error", String.valueOf(e));
		}
		
		req.getRequestDispatcher("/studente-home.jsp").forward(req, resp);
	}

	@Override
	protected void gestisciRichiestaPost(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		gestisciRichiestaGet(utente, req, resp);
	}
	private LoggerMessaggi getLoggerMessaggi() {
		return ControllerFactory.getInstance().getLoggerMessaggi();
	}

}
