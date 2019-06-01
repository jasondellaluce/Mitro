package mitro.view.professore;

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
import mitro.controller.professore.GestioneClasse;
import mitro.controller.professore.GestioneProfessore;
import mitro.deployment.Configurazione;
import mitro.exceptions.OperazioneException;
import mitro.model.Archiviazione;
import mitro.model.Classe;
import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.view.ViewUtenteAstratta;

public class ViewStoricoProfessore extends ViewUtenteAstratta {

	private static final long serialVersionUID = 8415486690574371075L;

	public ViewStoricoProfessore() {
		super(Ruolo.PROFESSORE);
	}

	@Override
	protected void gestisciRichiestaGet(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			int selectMateria = -1;
			int selectStudente = -1;
			boolean selectVoto = true;
			if(req.getAttribute("selectMateria") != null)
				selectMateria = (int) req.getAttribute("selectMateria");
			if(req.getAttribute("selectStudente") != null)
				selectStudente = (int) req.getAttribute("selectStudente");
			if(req.getAttribute("selectVoto") != null)
				selectVoto = (boolean) req.getAttribute("selectVoto");
			
			LocalDate yearBegin = LocalDate.now(Configurazione.getInstance().getZoneId()).withDayOfYear(1);
			GestioneProfessore gestioneProfessore = ControllerFactory.getInstance().getGestioneProfessore((Professore) utente);
			List<String> listaMaterie = gestioneProfessore.getListaAttivita(yearBegin, yearBegin.plusYears(1).minusDays(1)).stream()
					.map(a -> a.getClasse().getNome() + " - " + a.getMateria().getNome())
					.distinct()
					.sorted()
					.collect(Collectors.toList());
			
			req.setAttribute("selectMateria", selectMateria);
			req.setAttribute("selectStudente", selectStudente);
			req.setAttribute("selectVoto", selectVoto);
			req.setAttribute("listaMaterie", listaMaterie);
			req.setAttribute("listaStudenti", new ArrayList<Studente>());
			req.setAttribute("listaArchiviazioni", new ArrayList<Archiviazione>());
			if(selectMateria >= 0) {
				StringTokenizer stk = new StringTokenizer(listaMaterie.get(selectMateria), "-");
				String nomeClasse = stk.nextToken().trim();
				String nomeMateria = stk.nextToken().trim();
				Optional<Classe> classe = gestioneProfessore.getListaClassi().stream().filter(c -> c.getNome().equals(nomeClasse)).findAny();
				if(classe.isPresent()) {
					GestioneClasse gestioneClasse = ControllerFactory.getInstance().getGestioneClasse(classe.get());
					List<Studente> listaStudenti = gestioneClasse.getListaStudenti();
					listaStudenti.sort(Comparator.comparing(Studente::getNome).thenComparing(Studente::getCognome));
					req.setAttribute("listaStudenti", listaStudenti);
					if(selectStudente >= 0 && selectStudente < listaStudenti.size()) {
						Studente studente = listaStudenti.get(selectStudente);
						req.setAttribute("listaStudenti", listaStudenti);
						List<? extends Archiviazione> listaArchiviazioni = new ArrayList<>();
						if(selectVoto)
							listaArchiviazioni = gestioneClasse.getListaVoti(yearBegin, yearBegin.plusYears(1).minusDays(1));
						else
							listaArchiviazioni = gestioneClasse.getListaPresenze(yearBegin, yearBegin.plusYears(1).minusDays(1));
						
						listaArchiviazioni = listaArchiviazioni.stream()
								.filter(a -> a.getStudente().equals(studente))
								.filter(a -> a.getAttivita().getMateria().getNome().equals(nomeMateria))
								.filter(a -> a.getAttivita().getProfessore().equals((Professore) utente))
								.sorted(Comparator.comparing((Function<Archiviazione, LocalDate>) (a -> a.getAttivita().getData()))
										.thenComparingInt(a -> a.getAttivita().getOraInizio())
										.reversed())
								.collect(Collectors.toList());
						req.setAttribute("listaArchiviazioni", listaArchiviazioni);
					}
				}	
			}		
		}
		catch(OperazioneException e) {
			req.setAttribute("error", String.valueOf(e));
		}
		req.getRequestDispatcher("/professore-storico.jsp").forward(req, resp);
	}

	@Override
	protected void gestisciRichiestaPost(Utente utente, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int selectMateria = -1;
		int selectStudente = -1;
		boolean selectVoto = true;
		String selectedMateria = req.getParameter("selectionMaterie");
		String selectedStudente = req.getParameter("selectionStudenti");
		String selectedRicerca = req.getParameter("tipoRicerca");
		
		try { selectMateria = Integer.parseInt(selectedMateria); 
		} catch (NumberFormatException e) { }
		try { selectStudente = Integer.parseInt(selectedStudente); 
		} catch (NumberFormatException e) { }
		selectVoto = selectedRicerca.equals("voto");
		
		req.setAttribute("selectMateria", selectMateria);
		req.setAttribute("selectStudente", selectStudente);
		req.setAttribute("selectVoto", selectVoto);
		
		gestisciRichiestaGet(utente, req, resp);
	}

}
