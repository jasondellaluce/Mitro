package mitro.view.amministratore;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.amministratore.AmministrazioneClassi;
import mitro.controller.amministratore.AmministrazioneIscritti;
import mitro.controller.amministratore.AmministrazioneIscrittiController;
import mitro.controller.professore.GestioneClasse;
import mitro.controller.professore.GestioneProfessore;
import mitro.deployment.Configurazione;
import mitro.exceptions.OperazioneException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.view.ViewUtenteAstratta;

public class HomeAmministratore extends ViewUtenteAstratta {

	private static final long serialVersionUID = -2450523432502584339L;

	public HomeAmministratore() {
		super(Ruolo.AMMINISTRATORE);
	}

	@Override
	protected void gestisciRichiestaGet(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			AmministrazioneClassi amministrazioneClassi = getAmministrazioneClassi();
		try {	
			if("disconnetti".equals(req.getParameter("azione"))) {
				this.eseguiDisconnessione(req, resp);
				return;
			}
			ArrayList<Enum> ruoli= new ArrayList<Enum>();
			ruoli.add(Ruolo.STUDENTE);
			ruoli.add(Ruolo.PROFESSORE);
			List<Classe> classi= amministrazioneClassi.cercaClassi(null);
			req.setAttribute("ruoli", ruoli);
			req.setAttribute("classi", classi);
			req.setAttribute("nomeAmministratore", "Amministratore scolastico ");
			
		}
		catch (OperazioneException e) {
			req.setAttribute("error", String.valueOf(e));
		}
		
		req.getRequestDispatcher("/amministratore-home.jsp").forward(req, resp);
	}

	@Override
	protected void gestisciRichiestaPost(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		AmministrazioneIscritti amministrazioneIscritti = getAmministrazioneIscritti();
		try {
			Enum ruolo= (Enum) req.getAttribute("ruolo");
			String nome= (String) req.getParameter("nome");
			String cognome= (String) req.getParameter("cognome");
			Classe classe= (Classe) req.getAttribute("classe");
			
			if(ruolo!=null && nome!= null && cognome!= null) {
				if(ruolo.equals(Ruolo.STUDENTE) && classe!=null) {
					Studente s= new Studente();
					s.setNome(nome);
					s.setCognome(cognome);
					s.setClasse(classe);
					amministrazioneIscritti.registraIscritto(s);
				}
				else {
					if(ruolo.equals(Ruolo.PROFESSORE) ) {
						Professore p= new Professore();
						p.setNome(nome);
						p.setCognome(cognome);
						amministrazioneIscritti.registraIscritto(p);
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
