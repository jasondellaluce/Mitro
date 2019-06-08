package mitro.view.amministratore;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.amministratore.AmministrazioneClassi;
import mitro.controller.amministratore.AmministrazioneIscritti;
import mitro.controller.log.LoggerMessaggi;
import mitro.deployment.Configurazione;
import mitro.exceptions.OperazioneException;
import mitro.model.Classe;
import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.view.ViewUtenteAstratta;

public class ViewRegistrazioneUtente extends ViewUtenteAstratta {

	private static final long serialVersionUID = 5542459492857653364L;

	public ViewRegistrazioneUtente() {
		super(Ruolo.AMMINISTRATORE);
	}

	@Override
	protected void gestisciRichiestaGet(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LoggerMessaggi loggerMex= getLoggerMessaggi();
		try {	
			String log= LocalDateTime.now(Configurazione.getInstance().getZoneId()) + ", "
					+ utente.getId() + ", "
					+ "AMMINISTRATORE"+" - "
					+ "ViewRegistrazioneUtente,get ";
			Enumeration parametri=req.getParameterNames();
			while(parametri.hasMoreElements()) {
				String param=(String)parametri.nextElement();
				log+= param+": "+req.getParameter(param)+" ";
			}
			
			loggerMex.scrivi(log);
			
			AmministrazioneClassi amministrazioneClassi = getAmministrazioneClassi();
					
			ArrayList<String> ruoli= new ArrayList<String>();
			ruoli.add(""+Ruolo.STUDENTE);
			ruoli.add(""+Ruolo.PROFESSORE);
			List<Classe> classi= amministrazioneClassi.cercaClassi(null);
			req.setAttribute("ruoli", ruoli);
			req.setAttribute("classi", classi);
			
			
		}
		catch (OperazioneException e) {
			req.setAttribute("error", String.valueOf(e));
		}
		
		req.getRequestDispatcher("/amministratore-registra-utente.jsp").forward(req, resp);
	}

	@Override
	protected void gestisciRichiestaPost(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LoggerMessaggi loggerMex= getLoggerMessaggi();
		try {
			
			String log= LocalDateTime.now(Configurazione.getInstance().getZoneId()) + ", "
					+ utente.getId() + ", "
					+ "AMMINISTRATORE"+" - "
					+ "ViewRegistrazioneUtente,post ";
			Enumeration parametri=req.getParameterNames();
			while(parametri.hasMoreElements()) {
				String param=(String)parametri.nextElement();
				log+= param+": "+req.getParameter(param)+" ";
			}
			
			loggerMex.scrivi(log);
			
			AmministrazioneIscritti amministrazioneIscritti = getAmministrazioneIscritti();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String ruolo= req.getParameter("ruolo");
			String nome= req.getParameter("nome");
			String cognome= req.getParameter("cognome");
			String email=req.getParameter("email");
			int telefono=-1;
			try{
				telefono=Integer.parseInt(req.getParameter("telefono"));
			}
			catch(NumberFormatException e) {}
			String residenza=req.getParameter("residenza");
			String nascita=req.getParameter("nascita");
			String classe= req.getParameter("classe");
			
			if(!ruolo.isEmpty() && !nome.isEmpty() && !cognome.isEmpty() && ruolo!=null && nome!= null && cognome!= null && residenza!=null && telefono>0 && nascita!=null) {
				if(ruolo.equals(""+Ruolo.STUDENTE) && classe!=null) {
					Studente s= new Studente();
					s.setNome(nome);
					s.setCognome(cognome);
					s.setEmail(email);
					s.setTelefono(""+telefono);
					s.setIndirizzoResidenza(residenza);
					s.setDataNascita(LocalDate.parse(nascita,formatter));
					AmministrazioneClassi amministrazioneClassi = getAmministrazioneClassi();
					for(Classe c: amministrazioneClassi.cercaClassi(null)) if(c.getId().equals(classe))
					s.setClasse(c);
					amministrazioneIscritti.registraIscritto(s);
					String user="stud-"+s.getId();
					String password="password";
					amministrazioneIscritti.inserisciCredenzialiIscritto(s, user, password);			
					req.setAttribute("user", user);
					req.setAttribute("password",password);
				}
				else {
					if(ruolo.equals(""+Ruolo.PROFESSORE) ) {
						Professore p= new Professore();
						p.setNome(nome);
						p.setCognome(cognome);
						p.setEmail(email);
						p.setTelefono(""+telefono);
						p.setIndirizzoResidenza(residenza);
						p.setDataNascita(LocalDate.parse(nascita,formatter));
						amministrazioneIscritti.registraIscritto(p);
						String user="prof-"+p.getId();
						String password="password";
						amministrazioneIscritti.inserisciCredenzialiIscritto(p, user, password);
						
						req.setAttribute("user", user);
						req.setAttribute("password",password);
					} 
				}
			}
					
		}
		catch (OperazioneException e) {
			req.setAttribute("error", String.valueOf(e));
		}
		gestisciRichiestaGet(utente, req, resp);

	}
	
	private AmministrazioneIscritti getAmministrazioneIscritti() {
		return ControllerFactory.getInstance().getAmministrazioneIscritti();
	}
	
	private AmministrazioneClassi getAmministrazioneClassi() {
		return ControllerFactory.getInstance().getAmministrazioneClassi();
	}
	
	private LoggerMessaggi getLoggerMessaggi() {
		return ControllerFactory.getInstance().getLoggerMessaggi();
	}
	
}
