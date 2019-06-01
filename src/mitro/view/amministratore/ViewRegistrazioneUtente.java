package mitro.view.amministratore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.amministratore.AmministrazioneClassi;
import mitro.controller.amministratore.AmministrazioneIscritti;
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
		try {	
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
		AmministrazioneIscritti amministrazioneIscritti = getAmministrazioneIscritti();
		try {
			String ruolo= req.getParameter("ruolo");
			String nome= req.getParameter("nome");
			String cognome= req.getParameter("cognome");
			String classe= req.getParameter("classe");
			
			if(ruolo!=null && nome!= null && cognome!= null) {
				if(ruolo.equals(""+Ruolo.STUDENTE) && classe!=null) {
					Studente s= new Studente();
					s.setNome(nome);
					s.setCognome(cognome);
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
	
}
