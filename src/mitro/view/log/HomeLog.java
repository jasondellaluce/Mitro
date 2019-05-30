package mitro.view.log;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.log.GestioneLog;
import mitro.deployment.Configurazione;
import mitro.model.Ruolo;
import mitro.model.Utente;
import mitro.model.VoceLog;
import mitro.view.ViewUtenteAstratta;

public class HomeLog extends ViewUtenteAstratta {

	private static final long serialVersionUID = 4685751374595848132L;

	public HomeLog() {
		super(Ruolo.GESTORESICUREZZA);
	}

	@Override
	protected void gestisciRichiestaGet(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int richiesta = 0;
		try {
			if("disconnetti".equals(req.getParameter("azione"))) {
				this.eseguiDisconnessione(req, resp);
				return;
			}
			
			GestioneLog gestioneLog = ControllerFactory.getInstance().getGestioneLog();
			String selezioneDataInizioLog = req.getParameter("selezioneDataInizioLog");
			String selezioneDataFineLog = req.getParameter("selezioneDataFineLog");
			String selezioneDataInizioAnalisi = req.getParameter("selezioneDataInizioAnalisi");
			String selezioneDataFineAnalisi = req.getParameter("selezioneDataFineAnalisi");
			
			/* Gestisci richiesta ricerca log */
			if(selezioneDataInizioLog != null && selezioneDataFineLog != null) {
				richiesta = 1;
				LocalDateTime dataInizio = LocalDate.parse(selezioneDataInizioLog)
						.atStartOfDay(Configurazione.getInstance().getZoneId()).toLocalDateTime();
				LocalDateTime dataFine = LocalDate.parse(selezioneDataFineLog).plusDays(1)
						.atStartOfDay(Configurazione.getInstance().getZoneId()).toLocalDateTime();
				if(dataFine.isBefore(dataInizio))
					throw new IllegalArgumentException("fine prima di inizio");
				List<VoceLog> lista = gestioneLog.getLog(dataInizio, dataFine)
						.getListaVoci(dataInizio, dataFine);
				lista.sort(Comparator.comparing(VoceLog::getDataOra));
				req.setAttribute("listaVoci", lista);
			}
			
			/* Gestisci richiesta analisi log */
			else if(selezioneDataInizioAnalisi != null && selezioneDataFineAnalisi != null) {
				richiesta = 2;
				LocalDateTime dataInizio = LocalDate.parse(selezioneDataInizioAnalisi)
						.atStartOfDay(Configurazione.getInstance().getZoneId()).toLocalDateTime();
				LocalDateTime dataFine = LocalDate.parse(selezioneDataFineAnalisi).plusDays(1)
						.atStartOfDay(Configurazione.getInstance().getZoneId()).toLocalDateTime();
				if(dataFine.isBefore(dataInizio))
					throw new IllegalArgumentException("fine prima di inizio");
				List<String> lista = gestioneLog.getAnomalieMessaggi(dataInizio, dataFine);
				lista.addAll(gestioneLog.getAnomalieOperazioni(dataInizio, dataFine));
				req.setAttribute("listaAnomalie", lista);
			}		
		}
		catch(Exception e) {
			switch(richiesta) {
				default:
				case 0:
					throw new ServletException(e);
				case 1:
					req.setAttribute("erroreLog", String.valueOf(e));
					break;
				case 2:
					req.setAttribute("erroreAnalisi", String.valueOf(e));
					break;
			}			
		}
		req.getRequestDispatcher("/log-home.jsp").forward(req, resp);
	}

	@Override
	protected void gestisciRichiestaPost(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		gestisciRichiestaGet(utente, req, resp);		
	}

}
