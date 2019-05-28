package mitro.view.studente;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.studente.GestioneStudente;
import mitro.deployment.Configurazione;
import mitro.exceptions.OperazioneException;
import mitro.model.Comunicazione;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.view.ViewUtenteAstratta;

public class ViewComunicazioni extends ViewUtenteAstratta {

	private static final long serialVersionUID = -7638928177270647305L;

	public ViewComunicazioni() {
		super(Ruolo.STUDENTE);
	}

	@Override
	protected void gestisciRichiestaGet(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GestioneStudente gestioneStudente = ControllerFactory.getInstance().getGestioneStudente((Studente) utente);
		
		try {	
			if("disconnetti".equals(req.getParameter("azione"))) {
				this.eseguiDisconnessione(req, resp);
				return;
			}
			req.setAttribute("nomeStudente", gestioneStudente.getStudente().getNome()
					+ " " + gestioneStudente.getStudente().getCognome());
			
			LocalDate inizioSett = LocalDate.now(Configurazione.getInstance().getZoneId()).with(DayOfWeek.MONDAY);
			if(req.getParameter("inizioSett") != null)
				inizioSett = LocalDate.parse(req.getParameter("inizioSett"));
			req.setAttribute("inizioSett", inizioSett);
			req.setAttribute("fineSett", inizioSett.plusDays(6));
			req.setAttribute("prossimaSett", URLEncoder.encode(inizioSett.plusDays(7).toString(), "UTF-8"));
			req.setAttribute("precedenteSett", URLEncoder.encode(inizioSett.minusDays(7).toString(), "UTF-8"));		
			
			List<Comunicazione> listaComunicazioni = gestioneStudente.getListaComunicazioni(inizioSett, inizioSett.plusDays(6));
			listaComunicazioni.sort(Comparator.comparing(Comunicazione::getDataOra).reversed());
			req.setAttribute("listaComunicazioni", listaComunicazioni);
		}
		catch (OperazioneException e) {
			req.setAttribute("error", String.valueOf(e));
		}
		
		req.getRequestDispatcher("/studente-comunicazioni.jsp").forward(req, resp);
	}

	@Override
	protected void gestisciRichiestaPost(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		gestisciRichiestaGet(utente, req, resp);
	}

}
