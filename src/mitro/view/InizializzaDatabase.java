package mitro.view;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.deployment.Configurazione;
import mitro.exceptions.PersistenzaException;
import mitro.model.Amministratore;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Comunicazione;
import mitro.model.Materia;
import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOClasse;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.DAOFactory;
import mitro.persistenza.DAOUtente;

public class InizializzaDatabase extends ViewAstratta {

	private static final long serialVersionUID = -1011037578517892004L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DAOFactory factory = DAOFactory.getInstance();
        
        try {
        	long time = System.currentTimeMillis();
        	
			resp.getWriter().write("Eliminazione DataBase SQLite pre-esistente...\n");
			factory.cancellaDati();
			
			resp.getWriter().write("Creazione nuovo DataBase SQLite...\n");
			factory.inizializzaDati();
			
			resp.getWriter().write("Inizializzazione informazioni...\n");
			creaInformazioniIniziali(factory);
			
			time = System.currentTimeMillis() - time;
			resp.getWriter().write("Database Inizializzato\n");
			resp.getWriter().write("Tempo: " + (time / 1000) + " secondi");
        }
        catch(Exception e) {
        	throw new ServletException(e);
        }
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	private void creaInformazioniIniziali(DAOFactory factory) throws PersistenzaException {
		Random random = new Random(System.currentTimeMillis());
		DAOUtente daoUtente = factory.getDAOUtente();
		DAOClasse daoClasse = factory.getDAOClasse();
		DAOAttivita daoAttivita = factory.getDAOAttivita();
		DAOComunicazione daoComunicazione = factory.getDAOComunicazione();
		DAOArchiviazione daoArchiviazione = factory.getDAOArchiviazione();
		
		/* Classe */
		Classe c1 = new Classe();
		c1.setNome("5A");
		c1.setAnnoScolastico("18/19");
		c1.setDescrizione("Quinta A Tradizionale");
		daoClasse.registraClasse(c1);
		Classe c2 = new Classe();
		c2.setNome("4B");
		c2.setAnnoScolastico("18/19");
		c2.setDescrizione("Quarta B Scientifica");
		daoClasse.registraClasse(c2);
		Classe c3 = new Classe();
		c3.setNome("3C");
		c3.setAnnoScolastico("18/19");
		c3.setDescrizione("Terza C sportiva");
		daoClasse.registraClasse(c3);
		
		/* Utenti */
		Amministratore amm1 = new Amministratore();
		daoUtente.registraUtente(amm1);
		daoUtente.inserisciCredenziali(amm1, "admin1", "password");
		
		Studente stud1 = new Studente();
		stud1.setNome("Jason");
		stud1.setCognome("Dellaluce");
		stud1.setClasse(c1);
		daoUtente.registraUtente(stud1);
		daoUtente.inserisciCredenziali(stud1, "stud1", "password");
		
		Studente stud2 = new Studente();
		stud2.setNome("Federico");
		stud2.setCognome("Baldini");
		stud2.setClasse(c1);
		daoUtente.registraUtente(stud2);
		daoUtente.inserisciCredenziali(stud2, "stud2", "password");
		
		Studente stud3 = new Studente();
		stud3.setNome("Amir");
		stud3.setCognome("Al Sadi");
		stud3.setClasse(c1);
		daoUtente.registraUtente(stud3);
		daoUtente.inserisciCredenziali(stud3, "stud3", "password");
		
		Professore prof1 = new Professore();
		prof1.setNome("Marco");
		prof1.setCognome("Dalla Bella");
		daoUtente.registraUtente(prof1);
		daoUtente.inserisciCredenziali(prof1, "prof1", "password");
		
		Professore prof2 = new Professore();
		prof2.setNome("Bruno");
		prof2.setCognome("Rossi");
		daoUtente.registraUtente(prof2);
		daoUtente.inserisciCredenziali(prof2, "prof2", "password");
		
		Utente gestore1 = new Utente();
		gestore1.setRuolo(Ruolo.GESTORESICUREZZA);	
		daoUtente.registraUtente(gestore1);
		daoUtente.inserisciCredenziali(gestore1, "gestore", "password");
		
		/* Attivita */
		Materia m1 = new Materia();
		m1.setNome("Scienze");
		m1.setDescrizione("Scienze e Biologia per la quinta superiore");
		Materia m2 = new Materia();
		m2.setNome("Matematica");
		m2.setDescrizione("Matematica ed analisi per la quinta superiore");
		LocalDate startDate = LocalDate.now(Configurazione.getInstance().getZoneId())
				.withDayOfYear(1).plusWeeks(18)
				.with(DayOfWeek.MONDAY);
		for(int i = 0; i < 6; i++) {
			for(int j = 8; j <= 12; j++) {
				Attivita att = new Attivita();
				att.setProfessore(Arrays.asList(prof1, prof2).get(random.nextInt(2)));
				att.setClasse(Arrays.asList(c1, c2, c3).get(random.nextInt(3)));
				att.setOraInizio(j);
				switch(random.nextInt(2)) {
					default:
					case 0:
						att.setMateria(m1);
						break;
					case 1:
						att.setMateria(m2);
						break;
				}
				for(int k = 0; k < 6; k++) {
					att.setData(startDate.plusDays(i).plusWeeks(k));
					daoAttivita.registraAttivita(att);
					/*if(att.getData().isBefore(LocalDate.now(Configurazione.getInstance().getZoneId()).minusDays(2))) {
						for(Studente studente : Arrays.asList(stud1, stud2, stud3)) {
							if(Objects.equals(studente.getClasse(), att.getClasse())) {
								Presenza presenza = new Presenza();
								Voto voto = new Voto();
								presenza.setAttivita(att);
								voto.setAttivita(att);
								presenza.setStudente(studente);
								voto.setStudente(studente);
								presenza.setValore(random.nextBoolean());
								voto.setValore(Math.floor(random.nextDouble() * 9 + 1.0));
								daoArchiviazione.registraArchiviazione(voto);
								daoArchiviazione.registraArchiviazione(presenza);
							}
						}
					}*/
				}
			}
		}
		
		/* Comunicazioni */
		Comunicazione c = new Comunicazione();
		c.setDataOra(LocalDateTime.now(Configurazione.getInstance().getZoneId()));
		c.setOggetto("Messaggio privato dalla segreteria");
		c.setContenuto("Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
				+ "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
				+ "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
				+ "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in"
				+ "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
				+ "Excepteur sint occaecat cupidatat non proident,"
				+ "sunt in culpa qui officia deserunt mollit anim id est laborum.");
		c.setDestinatario(prof1);
		daoComunicazione.registraComunicazione(c);
		c.setDestinatario(stud1);
		daoComunicazione.registraComunicazione(c);	
		
		c.setDataOra(LocalDateTime.now(Configurazione.getInstance().getZoneId()).minusHours(3));
		c.setOggetto("Avvisio sciopero docenti");
		c.setContenuto("Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
				+ "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
				+ "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
				+ "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in"
				+ "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
				+ "Excepteur sint occaecat cupidatat non proident,"
				+ "sunt in culpa qui officia deserunt mollit anim id est laborum.");
		c.setDestinatario(prof1);
		daoComunicazione.registraComunicazione(c);
		c.setDestinatario(stud1);
		daoComunicazione.registraComunicazione(c);	
		
		c.setDataOra(LocalDateTime.now(Configurazione.getInstance().getZoneId()).minusDays(8));
		c.setOggetto("Emissione pratiche gita 2019/20");
		c.setContenuto("Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
				+ "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
				+ "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
				+ "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in"
				+ "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
				+ "Excepteur sint occaecat cupidatat non proident,"
				+ "sunt in culpa qui officia deserunt mollit anim id est laborum.");
		c.setDestinatario(prof1);
		daoComunicazione.registraComunicazione(c);	
		c.setDestinatario(stud1);
		daoComunicazione.registraComunicazione(c);			
	}

}
