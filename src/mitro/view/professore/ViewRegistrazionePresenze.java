package mitro.view.professore;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.log.LoggerMessaggi;
import mitro.controller.log.LoggerOperazioni;
import mitro.controller.professore.GestioneClasse;
import mitro.controller.professore.GestioneProfessore;
import mitro.exceptions.OperazioneException;
import mitro.model.Attivita;
import mitro.model.Presenza;
import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.view.ViewUtenteAstratta;

public class ViewRegistrazionePresenze extends ViewUtenteAstratta {

	private static final long serialVersionUID = -4164770752637442866L;

	public ViewRegistrazionePresenze() {
		super(Ruolo.PROFESSORE);
	}

	@Override
	protected void gestisciRichiestaGet(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GestioneProfessore gestioneProfessore = ControllerFactory.getInstance().getGestioneProfessore((Professore) utente);
		LoggerMessaggi loggerMex= getLoggerMessaggi();
		
		try {	
			
			String log= "Servlet ViewRegistrazionePresenze del Professore metodo get, parametri: ";
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
			StringTokenizer stk = new StringTokenizer(req.getParameter("selAtt"), ";");
			LocalDate date = LocalDate.parse(stk.nextToken().trim());
			int time = Integer.parseInt(stk.nextToken().trim());
			String classeId = stk.nextToken().trim();
			
			Optional<Attivita> attivita = gestioneProfessore.getListaAttivita(date, date.plusDays(1)).stream()
					.filter(a -> a.getData().equals(date) && a.getOraInizio() == time && a.getClasse().getId().equals(classeId))
					.findAny();
			if(!attivita.isPresent()) {
				resp.sendRedirect("/professore");
				return;
			}
			GestioneClasse gestioneClasse = ControllerFactory.getInstance().getGestioneClasse(attivita.get().getClasse());
			List<Studente> listaStudenti = gestioneClasse.getListaStudenti();
			listaStudenti.sort(Comparator.comparing(Studente::getNome).thenComparing(Studente::getCognome));
			
			req.setAttribute("testoTitolo", 
					"Appello dell'attività di " + attivita.get().getMateria().getNome() 
					+ " del " + attivita.get().getData() + " alle " + attivita.get().getOraInizio()
					+ ", classe " + attivita.get().getClasse().getNome());
			req.setAttribute("listaStudenti", listaStudenti);
			gestioneClasse.getListaPresenze(date.minusDays(1), date.plusDays(1)).forEach(p -> {
				if(p.getAttivita().equals(attivita.get()) && p.isValore()) {
					int indexStud = listaStudenti.indexOf(p.getStudente());
					if(indexStud >= 0)
						req.setAttribute("presente" + indexStud, "yes");
				}
			});
			
		}
		catch (DateTimeParseException | NumberFormatException | NullPointerException e){
			throw new ServletException(e);
			//resp.sendRedirect("/professore");
			//return;
		}
		catch (OperazioneException e) {
			throw new ServletException(e);
			//req.setAttribute("error", String.valueOf(e));
		}
		
		req.getRequestDispatcher("/professore-appello.jsp").forward(req, resp);
	}

	@Override
	protected void gestisciRichiestaPost(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GestioneProfessore gestioneProfessore = ControllerFactory.getInstance().getGestioneProfessore((Professore) utente);		
		LoggerMessaggi loggerMex= getLoggerMessaggi();
		
		try {	
			
			String log= "Servlet ViewRegistrazionePresenze del Professore metodo post, parametri: ";
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
			StringTokenizer stk = new StringTokenizer(req.getParameter("selAtt"), ";");
			LocalDate date = LocalDate.parse(stk.nextToken().trim());
			int time = Integer.parseInt(stk.nextToken().trim());
			String classeId = stk.nextToken().trim();
			
			Optional<Attivita> attivita = gestioneProfessore.getListaAttivita(date, date.plusDays(1)).stream()
					.filter(a -> a.getData().equals(date) && a.getOraInizio() == time && a.getClasse().getId().equals(classeId))
					.findAny();
			if(!attivita.isPresent()) {
				resp.sendRedirect("/professore");
				return;
			}
			GestioneClasse gestioneClasse = ControllerFactory.getInstance().getGestioneClasse(attivita.get().getClasse());
			List<Studente> listaStudenti = gestioneClasse.getListaStudenti();
			listaStudenti.sort(Comparator.comparing(Studente::getNome).thenComparing(Studente::getCognome));
			
			String[] presenzeStr = req.getParameterValues("checkPresenza");
			if(presenzeStr != null) {
				for(String p : presenzeStr) {
					Studente studente = listaStudenti.get(Integer.parseInt(p));
					Presenza presenza = new Presenza();
					presenza.setAttivita(attivita.get());
					presenza.setStudente(studente);
					presenza.setValore(true);
					gestioneClasse.inserisciPresenza(presenza);
					listaStudenti.set(Integer.parseInt(p), null);
				}
			}
			for(Studente studente : listaStudenti) {
				if(studente == null)
					continue;
				Presenza presenza = new Presenza();
				presenza.setAttivita(attivita.get());
				presenza.setStudente(studente);
				presenza.setValore(false);
				gestioneClasse.inserisciPresenza(presenza);
			}
			
			gestisciRichiestaGet(utente, req, resp);
			return;
		}
		catch (OperazioneException e) {
			throw new ServletException(e);
		}
		catch (Exception e) {
			throw new ServletException(e);
			//req.setAttribute("error", String.valueOf(e));
		}
		
		//resp.sendRedirect("/professore");
	}
	private LoggerMessaggi getLoggerMessaggi() {
		return ControllerFactory.getInstance().getLoggerMessaggi();
	}
	

}
