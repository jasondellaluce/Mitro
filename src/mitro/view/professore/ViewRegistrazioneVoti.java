package mitro.view.professore;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import mitro.controller.professore.GestioneProfessoreController;
import mitro.deployment.Configurazione;
import mitro.exceptions.OperazioneException;
import mitro.model.Attivita;
import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.model.Voto;
import mitro.view.ViewUtenteAstratta;

public class ViewRegistrazioneVoti extends ViewUtenteAstratta {

	private static final long serialVersionUID = -3325468930792939078L;

	public ViewRegistrazioneVoti() {
		super(Ruolo.PROFESSORE);
	}

	@Override
	protected void gestisciRichiestaGet(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GestioneProfessore gestioneProfessore = ControllerFactory.getInstance().getGestioneProfessore((Professore) utente);
		LoggerMessaggi loggerMex= getLoggerMessaggi();
		
		try {	
			
			String log= LocalDateTime.now(Configurazione.getInstance().getZoneId()) + ", "
					+ utente.getId() + ", "
					+ ((Professore)utente).getNome()+" "+((Professore)utente).getCognome()
					+ "ViewRegistrazioneVoti,get ";
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
					"Voti dell'attività di " + attivita.get().getMateria().getNome() 
					+ " del " + attivita.get().getData() + " alle " + attivita.get().getOraInizio()
					+ ", classe " + attivita.get().getClasse().getNome());
			req.setAttribute("listaStudenti", listaStudenti);
			gestioneClasse.getListaVoti(date.minusDays(1), date.plusDays(1)).forEach(p -> {
				if(p.getAttivita().equals(attivita.get())) {
					int indexStud = listaStudenti.indexOf(p.getStudente());
					if(indexStud >= 0)
						req.setAttribute("voto" + indexStud, p.getValore());
				}
			});
			
		}
		catch (DateTimeParseException | NumberFormatException | NullPointerException e){
			throw new ServletException(e);
			/*resp.sendRedirect("/professore");
			return;*/
		}
		catch (OperazioneException e) {
			throw new ServletException(e);
			//req.setAttribute("error", String.valueOf(e));
		}
		
		req.getRequestDispatcher("/professore-voti.jsp").forward(req, resp);
	}

	@Override
	protected void gestisciRichiestaPost(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GestioneProfessore gestioneProfessore = ControllerFactory.getInstance().getGestioneProfessore((Professore) utente);		

		LoggerMessaggi loggerMex= getLoggerMessaggi();
		
		try {	
			
			String log= LocalDateTime.now(Configurazione.getInstance().getZoneId()) + ", "
					+ utente.getId() + ", "
					+ ((Professore)utente).getNome()+" "+((Professore)utente).getCognome()
					+ "ViewRegistrazioneVoti,post ";
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
			
			if(!attivita.isPresent() || 
					(attivita.get().getData().isAfter(LocalDate.now(Configurazione.getInstance().getZoneId()))
					||(attivita.get().getData().isEqual(LocalDate.now(Configurazione.getInstance().getZoneId())) 
					&& attivita.get().getOraInizio() >= LocalDateTime.now(Configurazione.getInstance().getZoneId()).getHour())) ) {
				resp.sendRedirect("/professore");
				return;
			}
			
			GestioneClasse gestioneClasse = ControllerFactory.getInstance().getGestioneClasse(attivita.get().getClasse());
			List<Studente> listaStudenti = gestioneClasse.getListaStudenti();
			listaStudenti.sort(Comparator.comparing(Studente::getNome).thenComparing(Studente::getCognome));
			
			/* Check type safety */
			for(int i = 0; i < listaStudenti.size(); i++) {
				if(req.getParameter("checkVoto" + i) != null && req.getParameter("checkVoto" + i).trim().length() > 0) {
					Double.parseDouble(req.getParameter("checkVoto" + i).trim());
				}
			}
			
			for(int i = 0; i < listaStudenti.size(); i++) {
				if(req.getParameter("checkVoto" + i) != null && req.getParameter("checkVoto" + i).trim().length() > 0) {
					double valore = Double.parseDouble(req.getParameter("checkVoto" + i).trim());
					Voto voto = new Voto();
					voto.setAttivita(attivita.get());
					voto.setStudente(listaStudenti.get(i));
					voto.setValore(valore);
					gestioneClasse.registraVoto(voto);
				}
			}
			
			gestisciRichiestaGet(utente, req, resp);
			return;
		}
		catch (OperazioneException e) {
			throw new ServletException(e);
		}
		catch (Exception e) {
			req.setAttribute("error", String.valueOf(e));
			gestisciRichiestaGet(utente, req, resp);
			return;
		}
	}
	
	private LoggerMessaggi getLoggerMessaggi() {
		return ControllerFactory.getInstance().getLoggerMessaggi();
	}
	


}
