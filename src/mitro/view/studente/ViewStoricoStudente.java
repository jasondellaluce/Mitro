package mitro.view.studente;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.studente.GestioneStudente;
import mitro.deployment.Configurazione;
import mitro.exceptions.OperazioneException;
import mitro.model.Archiviazione;
import mitro.model.Classe;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.view.ViewUtenteAstratta;

public class ViewStoricoStudente extends ViewUtenteAstratta {

	private static final long serialVersionUID = 2238045498081013921L;

	public ViewStoricoStudente() {
		super(Ruolo.STUDENTE);
	}

	@Override
	protected void gestisciRichiestaGet(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			int selectMateria = -1;
			boolean selectVoto = true;
			if(req.getAttribute("selectMateria") != null)
				selectMateria = (int) req.getAttribute("selectMateria");
			if(req.getAttribute("selectVoto") != null)
				selectVoto = (boolean) req.getAttribute("selectVoto");
			
			LocalDate yearBegin = LocalDate.now(Configurazione.getInstance().getZoneId()).withDayOfYear(1);
			GestioneStudente gestioneStudente = ControllerFactory.getInstance().getGestioneStudente((Studente) utente);
			List<String> listaMaterie = gestioneStudente.getListaAttivita(yearBegin, yearBegin.plusYears(1).minusDays(1)).stream()
					.map(a -> a.getClasse().getNome() + " - " + a.getMateria().getNome())
					.distinct()
					.sorted()
					.collect(Collectors.toList());
			
			req.setAttribute("selectMateria", selectMateria);
			req.setAttribute("selectVoto", selectVoto);
			req.setAttribute("listaMaterie", listaMaterie);
			req.setAttribute("listaStudenti", new ArrayList<Studente>());
			req.setAttribute("listaArchiviazioni", new ArrayList<Archiviazione>());
			if(selectMateria >= 0) {
				StringTokenizer stk = new StringTokenizer(listaMaterie.get(selectMateria), "-");
				stk.nextToken().trim();
				String nomeMateria = stk.nextToken().trim();
				Optional<Classe> classe = Optional.ofNullable(gestioneStudente.getStudente().getClasse());
				if(classe.isPresent()) {
					List<? extends Archiviazione> listaArchiviazioni = new ArrayList<>();
					if(selectVoto)
						listaArchiviazioni = gestioneStudente.getListaVoti(yearBegin, yearBegin.plusYears(1).minusDays(1));
					else
						listaArchiviazioni = gestioneStudente.getListaPresenze(yearBegin, yearBegin.plusYears(1).minusDays(1));
					
					listaArchiviazioni = listaArchiviazioni.stream()
							.filter(a -> a.getStudente().equals((Studente) utente))
							.filter(a -> a.getAttivita().getMateria().getNome().equals(nomeMateria))
							.sorted(Comparator.comparing((Function<Archiviazione, LocalDate>) (a -> a.getAttivita().getData()))
									.thenComparingInt(a -> a.getAttivita().getOraInizio())
									.reversed())
							.collect(Collectors.toList());
					req.setAttribute("listaArchiviazioni", listaArchiviazioni);
				}	
			}		
		}
		catch(OperazioneException e) {
			req.setAttribute("error", String.valueOf(e));
		}
		req.getRequestDispatcher("/studente-storico.jsp").forward(req, resp);
	}

	@Override
	protected void gestisciRichiestaPost(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int selectMateria = -1;
		boolean selectVoto = true;
		String selectedMateria = req.getParameter("selectionMaterie");
		String selectedRicerca = req.getParameter("tipoRicerca");
		
		try { selectMateria = Integer.parseInt(selectedMateria); 
		} catch (NumberFormatException e) { }
		selectVoto = selectedRicerca.equals("voto");
		
		req.setAttribute("selectMateria", selectMateria);
		req.setAttribute("selectVoto", selectVoto);
		
		gestisciRichiestaGet(utente, req, resp);
	}

}
