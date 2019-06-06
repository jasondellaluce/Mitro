package mitro.view.professore;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.log.LoggerMessaggi;
import mitro.controller.log.LoggerOperazioni;
import mitro.controller.log.WriterLoggerOperazioni;
import mitro.controller.professore.GestioneClasse;
import mitro.controller.professore.GestioneProfessore;
import mitro.deployment.Configurazione;
import mitro.exceptions.OperazioneException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.model.Utente;
import mitro.view.ViewUtenteAstratta;

public class HomeProfessore extends ViewUtenteAstratta {

	private static final long serialVersionUID = 4280361850637171049L;

	public HomeProfessore() {
		super(Ruolo.PROFESSORE);
	}

	@Override
	protected void gestisciRichiestaGet(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GestioneProfessore gestioneProfessore = getGestioneProfessore(utente, req, resp);
		LoggerMessaggi loggerMex= getLoggerMessaggi();

		try {	
			
			String log= "Servlet HomeProfessore parametro get, parametri: ";
			String param;
			while((param=(String) req.getParameterNames().nextElement())!=null) {
				log+=param+": "+req.getParameter(param)+" ";
			}
			
			loggerMex.scrivi(log);
			
			if("disconnetti".equals(req.getParameter("azione"))) {
				this.eseguiDisconnessione(req, resp);
				return;
			}
			req.setAttribute("nomeProfessore", "Prof. " + gestioneProfessore.getProfessore().getNome()
					+ " " + gestioneProfessore.getProfessore().getCognome());
			
			LocalDate inizioSett = LocalDate.now(Configurazione.getInstance().getZoneId()).with(DayOfWeek.MONDAY);
			if(req.getParameter("inizioSett") != null)
				inizioSett = LocalDate.parse(req.getParameter("inizioSett"));
			req.setAttribute("inizioSett", inizioSett);
			req.setAttribute("fineSett", inizioSett.plusDays(6));
			req.setAttribute("prossimaSett", URLEncoder.encode(inizioSett.plusDays(7).toString(), "UTF-8"));
			req.setAttribute("precedenteSett", URLEncoder.encode(inizioSett.minusDays(7).toString(), "UTF-8"));		
			
			List<Attivita> listaAttivita = gestioneProfessore.getListaAttivita(inizioSett, inizioSett.plusDays(6));
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
		
		req.getRequestDispatcher("/professore-home.jsp").forward(req, resp);
	}

	@Override
	protected void gestisciRichiestaPost(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GestioneProfessore gestioneProfessore = getGestioneProfessore(utente, req, resp);
		LoggerMessaggi loggerMex= getLoggerMessaggi();
		
		try {
			
			String log= "Servlet HomeProfessore metodo post, parametri: ";
			String param;
			while((param=(String) req.getParameterNames().nextElement())!=null) {
				log+=param+": "+req.getParameter(param)+" ";
			}
			
			loggerMex.scrivi(log);
			
			if(req.getParameter("annotazione") != null && req.getParameter("annotazione").trim().length() > 0) {
				if(req.getParameter("selAtt") != null) {
					StringTokenizer stk = new StringTokenizer(req.getParameter("selAtt"), ";");
					LocalDate date = LocalDate.parse(stk.nextToken().trim());
					int time = Integer.parseInt(stk.nextToken().trim());
					String classeId = stk.nextToken().trim();
					Optional<Attivita> attivita = gestioneProfessore.getListaAttivita(date, date.plusDays(1)).stream()
						.filter(a -> a.getData().equals(date) && a.getOraInizio() == time && a.getClasse().getId().equals(classeId))
						.findAny();
					if(attivita.isPresent()) {
						GestioneClasse gestioneClasse = getGestioneClasse(attivita.get().getClasse(), req, resp);
						gestioneClasse.inserisciAnnotazione(attivita.get(), req.getParameter("annotazione").trim());
					}
				}
			}
		}
		catch (OperazioneException e) {
			req.setAttribute("error", String.valueOf(e));
		}
			
		gestisciRichiestaGet(utente, req, resp);
	}
	
	private GestioneProfessore getGestioneProfessore(Utente utente, HttpServletRequest req, HttpServletResponse resp) {
		return ControllerFactory.getInstance().getGestioneProfessore((Professore) utente);
	}
	
	private GestioneClasse getGestioneClasse(Classe classe, HttpServletRequest req, HttpServletResponse resp) {
		return ControllerFactory.getInstance().getGestioneClasse(classe);
	}
	
	private LoggerMessaggi getLoggerMessaggi() {
		return ControllerFactory.getInstance().getLoggerMessaggi();
	}
	

}
