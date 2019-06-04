package mitro.view.amministratore;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.amministratore.AmministrazioneClassi;
import mitro.controller.amministratore.AmministrazioneIscritti;
import mitro.deployment.Configurazione;
import mitro.exceptions.OperazioneException;
import mitro.model.Classe;
import mitro.model.Comunicazione;
import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.view.ViewUtenteAstratta;

public class ViewRegistrazioneComunicazione extends ViewUtenteAstratta {

	private static final long serialVersionUID = -6757561119428211218L;

	public ViewRegistrazioneComunicazione() {
		super(Ruolo.AMMINISTRATORE);
	}
	
	@Override
	protected void gestisciRichiestaGet(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			AmministrazioneIscritti amministrazioneIscritti= getAmministrazioneIscritti();
			AmministrazioneClassi amministrazioneClassi = getAmministrazioneClassi();
			List<Classe> classi= amministrazioneClassi.cercaClassi(null);
			List<Studente> studenti= amministrazioneIscritti.cercaIscritti(null).stream()
									 .filter(o -> o instanceof Studente)
									 .map(o -> (Studente)o)
									 .collect(Collectors.toList());
			List<Professore> professori= amministrazioneIscritti.cercaIscritti(null).stream()
					 .filter(o -> o instanceof Professore)
					 .map(o -> (Professore)o)
					 .collect(Collectors.toList());
			req.setAttribute("classi", classi);
			req.setAttribute("studenti", studenti);
			req.setAttribute("professori", professori);
			
			
		} catch (OperazioneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		req.getRequestDispatcher("/amministratore-registra-comunicazione.jsp").forward(req, resp);
	}

	@Override
	protected void gestisciRichiestaPost(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			AmministrazioneIscritti amministrazioneIscritti= getAmministrazioneIscritti();
			AmministrazioneClassi amministrazioneClassi = getAmministrazioneClassi();
			String[] classi= req.getParameterValues("classiSelected");
			String[] studenti=req.getParameterValues("studentiSelected");
			String[] professori=req.getParameterValues("professoriSelected");
			List<Classe> tutteLeClassi=amministrazioneClassi.cercaClassi(null);
			List<Studente> tuttiGliStudenti= amministrazioneIscritti.cercaIscritti(null).stream()
											 .filter(o -> o instanceof Studente)
											 .map(o -> (Studente)o)
											 .collect(Collectors.toList());
			List<Professore> tuttiIProfessori= amministrazioneIscritti.cercaIscritti(null).stream()
					 .filter(o -> o instanceof Professore)
					 .map(o -> (Professore)o)
					 .collect(Collectors.toList());
			
			String annotazione= req.getParameter("comunicazione");
			String oggetto= req.getParameter("oggetto");
			
			if(!annotazione.isEmpty() && annotazione!=null && !oggetto.isEmpty() && oggetto!=null) {
				Comunicazione comunicazione= new Comunicazione();
				
				List<Professore> professoriSelezionati=new ArrayList<Professore>();
				List<Studente> studentiSelezionati = new ArrayList<Studente>();
				if(professori!=null) {
					for(String idProf: professori) 
						for(Professore prof: tuttiIProfessori)
							if(prof.getId().equals(idProf)){
								professoriSelezionati.add(prof);
								comunicazione= new Comunicazione();
								comunicazione.setOggetto(oggetto);
								comunicazione.setContenuto(annotazione);
								comunicazione.setDestinatario(prof);
								comunicazione.setDataOra(LocalDateTime.now(Configurazione.getInstance().getZoneId()));
								amministrazioneIscritti.registraComunicazione(comunicazione);
							}					
				}
				if(studenti!=null) {
					for(String idStud: studenti) 
						for(Studente stud: tuttiGliStudenti)
							if(stud.getId().equals(idStud)){
								studentiSelezionati.add(stud);
								comunicazione= new Comunicazione();
								comunicazione.setOggetto(oggetto);
								comunicazione.setContenuto(annotazione);
								comunicazione.setDestinatario(stud);
								comunicazione.setDataOra(LocalDateTime.now(Configurazione.getInstance().getZoneId()));
								amministrazioneIscritti.registraComunicazione(comunicazione);
							}	
				}

				
				if(classi!=null) {
					for(String idClasse: classi)
						for(Classe classe: tutteLeClassi)
							if(classe.getId().equals(idClasse))
								for(Studente stud:tuttiGliStudenti.stream()
										       .filter(o -> o.getClasse().equals(classe))
										       .collect(Collectors.toList())) {
									if(!studentiSelezionati.contains(stud)) {
										comunicazione= new Comunicazione();
										comunicazione.setOggetto(oggetto);
										comunicazione.setContenuto(annotazione);
										comunicazione.setDestinatario(stud);
										comunicazione.setDataOra(LocalDateTime.now(Configurazione.getInstance().getZoneId()));
										amministrazioneIscritti.registraComunicazione(comunicazione);
									}
								}
					
				}

				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
		gestisciRichiestaGet(utente,req,resp);
	}

	private AmministrazioneIscritti getAmministrazioneIscritti() {
		return ControllerFactory.getInstance().getAmministrazioneIscritti();
	}
	
	private AmministrazioneClassi getAmministrazioneClassi() {
		return ControllerFactory.getInstance().getAmministrazioneClassi();
	}
	
}
