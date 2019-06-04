package mitro.view;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

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
import mitro.model.Presenza;
import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.model.Voto;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOClasse;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.DAOFactory;
import mitro.persistenza.DAOUtente;

public class InizializzaDatabaseCompleto extends ViewAstratta {


	private static final long serialVersionUID = -1011037578517892004L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DAOFactory factory = DAOFactory.getInstance();
        
        try {
        	long time = System.currentTimeMillis();
        	
			resp.getWriter().write("Eliminazione DataBase SQLite Completo pre-esistente...\n");
			factory.cancellaDati();
			
			resp.getWriter().write("Creazione nuovo DataBase SQLite Completo...\n");
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
		
		/* Classi */
		
		ArrayList<Classe> classi= new ArrayList<Classe>();
		Classe c1 = new Classe();
		c1.setNome("1A");
		c1.setAnnoScolastico("18/19");
		c1.setDescrizione("Prima A Tradizionale");
		daoClasse.registraClasse(c1);
		classi.add(c1);
		
		Classe c2 = new Classe();	
		c2.setNome("2A");
		c2.setAnnoScolastico("18/19");
		c2.setDescrizione("Seconda A Tradizionale");
		daoClasse.registraClasse(c2);
		classi.add(c2);
		
		Classe c3 = new Classe();
		c3.setNome("3A");
		c3.setAnnoScolastico("18/19");
		c3.setDescrizione("Terza A Tradizionale");
		daoClasse.registraClasse(c3);
		classi.add(c3);
		
		Classe c4 = new Classe();
		c4.setNome("4A");
		c4.setAnnoScolastico("18/19");
		c4.setDescrizione("Quarta A Tradizionale");
		daoClasse.registraClasse(c4);
		classi.add(c4);
		
		Classe c5 = new Classe();
		c5.setNome("5A");
		c5.setAnnoScolastico("18/19");
		c5.setDescrizione("Quinta A Tradizionale");
		daoClasse.registraClasse(c5);
		classi.add(c5);
		
		/* Amministratori */
		Amministratore amm1 = new Amministratore();
		daoUtente.registraUtente(amm1);
		daoUtente.inserisciCredenziali(amm1, "admin1", "password");
		
		amm1 = new Amministratore();
		daoUtente.registraUtente(amm1);
		daoUtente.inserisciCredenziali(amm1, "admin2", "password");
		
		amm1 = new Amministratore();
		daoUtente.registraUtente(amm1);
		daoUtente.inserisciCredenziali(amm1, "admin3", "password");
		
		amm1 = new Amministratore();
		daoUtente.registraUtente(amm1);
		daoUtente.inserisciCredenziali(amm1, "admin4", "password");
		
		/*Studenti*/
		ArrayList<String> nomi= new ArrayList<String>();
		nomi.add("Marco");
		nomi.add("Federico");
		nomi.add("Nicolò");
		nomi.add("Mario");
		nomi.add("Francesco");
		nomi.add("Giulia");
		nomi.add("Laura");
		nomi.add("Irene");
		nomi.add("Chiara");
		nomi.add("Marta");
		
		ArrayList<String> cognomi= new ArrayList<String>();
		cognomi.add("Rossi");
		cognomi.add("Russo");
		cognomi.add("Ferrari");
		cognomi.add("Esposito");
		cognomi.add("Bianchi");
		cognomi.add("Romano");
		cognomi.add("Colombo");
		cognomi.add("Ricci");
		cognomi.add("Marino");
		cognomi.add("Greco");		
		
		ArrayList<Studente> studenti= new ArrayList<Studente>();
		
		/*Popolamento classe c1*/
		Random r= new Random();
		for(int i=0;i<10;i++) {
			String nome=nomi.get(r.nextInt()/10);
			String cognome=cognomi.get(r.nextInt()/10);
			Studente studente= new Studente();
			studente.setNome(nome);
			studente.setCognome(cognome);
			studente.setClasse(c1);
			daoUtente.registraUtente(studente);
			daoUtente.inserisciCredenziali(studente, "stud-"+studente.getId(), "password");
			studenti.add(studente);
		}
		/*Popolamento classe c3*/		
		for(int i=0;i<10;i++) {
			String nome=nomi.get(r.nextInt()/10);
			String cognome=cognomi.get(r.nextInt()/10);
			Studente studente= new Studente();
			studente.setNome(nome);
			studente.setCognome(cognome);
			studente.setClasse(c3);
			daoUtente.registraUtente(studente);
			daoUtente.inserisciCredenziali(studente, "stud-"+studente.getId(), "password");
			studenti.add(studente);
		}
		/*Popolamento classe c5*/
		for(int i=0;i<10;i++) {
			String nome=nomi.get(r.nextInt()/10);
			String cognome=cognomi.get(r.nextInt()/10);
			Studente studente= new Studente();
			studente.setNome(nome);
			studente.setCognome(cognome);
			studente.setClasse(c5);
			daoUtente.registraUtente(studente);
			daoUtente.inserisciCredenziali(studente, "stud-"+studente.getId(), "password");
			studenti.add(studente);
		}
		
		/*Studenti noti per accesso in demo*/
		Studente stud1 = new Studente();
		stud1.setNome("Jason");
		stud1.setCognome("Dellaluce");
		stud1.setClasse(c1);
		daoUtente.registraUtente(stud1);
		daoUtente.inserisciCredenziali(stud1, "stud1", "password");
		
		Studente stud2 = new Studente();
		stud2.setNome("Federico");
		stud2.setCognome("Baldini");
		stud2.setClasse(c3);
		daoUtente.registraUtente(stud2);
		daoUtente.inserisciCredenziali(stud2, "stud2", "password");
		
		Studente stud3 = new Studente();
		stud3.setNome("Amir");
		stud3.setCognome("Al Sadi");
		stud3.setClasse(c5);
		daoUtente.registraUtente(stud3);
		daoUtente.inserisciCredenziali(stud3, "stud3", "password");
		
		/*Professori*/
		ArrayList<Professore> professori = new ArrayList<Professore>();
		
		for(int i=0;i<6;i++) {
			String nome=nomi.get(r.nextInt()/10);
			String cognome=cognomi.get(r.nextInt()/10);			
			Professore professore= new Professore();
			professore.setNome(nome);
			professore.setCognome(cognome);
			daoUtente.registraUtente(professore);
			daoUtente.inserisciCredenziali(professore, "prof-"+professore.getId(), "password");
			professori.add(professore);
		}
		
		
		/*Professori noti per accesso a demo*/
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
		
		professori.add(prof1);
		professori.add(prof2);
		
		/*Gestori*/
		Utente gestore1 = new Utente();
		gestore1.setRuolo(Ruolo.GESTORESICUREZZA);	
		daoUtente.registraUtente(gestore1);
		daoUtente.inserisciCredenziali(gestore1, "gestore1", "password");

		Utente gestore2 = new Utente();
		gestore2.setRuolo(Ruolo.GESTORESICUREZZA);	
		daoUtente.registraUtente(gestore2);
		daoUtente.inserisciCredenziali(gestore2, "gestore2", "password");
		
		Utente gestore3 = new Utente();
		gestore3.setRuolo(Ruolo.GESTORESICUREZZA);	
		daoUtente.registraUtente(gestore3);
		daoUtente.inserisciCredenziali(gestore3, "gestore3", "password");
		
		/*Materie*/
		ArrayList<Materia> materie= new ArrayList<Materia>();
		
		Materia materia= new Materia();
		materia.setNome("Matematica");
		materia.setDescrizione("Matematica liceo tradizionale");
		materie.add(materia);
		
		materia= new Materia();
		materia.setNome("Fisica");
		materia.setDescrizione("Fisica liceo tradizionale");
		materie.add(materia);
		
		materia= new Materia();
		materia.setNome("Scienze");
		materia.setDescrizione("Scienze liceo tradizionale");
		materie.add(materia);
		
		materia= new Materia();
		materia.setNome("Italiano");
		materia.setDescrizione("Italiano liceo tradizionale");
		materie.add(materia);
		
		materia= new Materia();
		materia.setNome("Storia e Filosofia");
		materia.setDescrizione("Storia e Filosofia liceo tradizionale");
		materie.add(materia);
		
		int sizeMaterie= materie.size();
		int sizeProfessori= professori.size();
		
		/* Attivita */
		LocalDate startDate = LocalDate.now(Configurazione.getInstance().getZoneId())
				.withDayOfYear(1).plusWeeks(21)
				.with(DayOfWeek.MONDAY);
		for(int count=0;count<classi.size();count++){
			Classe classe=classi.get(count);
			for(int i = 0; i < 6; i++) { //#settimane
				for(int k = 0; k < 6; k++) { //#giorni
					for(int j = 8; j <= 14; j++) { //#ora
						Attivita att = new Attivita();
						att.setProfessore(professori.get(random.nextInt()/sizeProfessori));
						att.setClasse(classe);
						att.setMateria(materie.get(random.nextInt()/sizeMaterie));
						att.setData(startDate.plusWeeks(i).plusDays(k));
						att.setOraInizio(j);
						daoAttivita.registraAttivita(att);
						if(r.nextInt()%2==0 && att.getData().isBefore(LocalDate.now(Configurazione.getInstance().getZoneId()))) {
							for(Studente stud: studenti.stream().filter(o -> o.getClasse().equals(classe)).collect(Collectors.toList())) {
								Presenza presenza= new Presenza();
								Voto voto= new Voto();
								voto.setValore((double)r.nextInt());
								presenza.setValore(true);
								presenza.setStudente(stud);
								voto.setStudente(stud);
								presenza.setAttivita(att);
								voto.setAttivita(att);
								daoArchiviazione.registraArchiviazione(presenza);
								daoArchiviazione.registraArchiviazione(voto);
							}							
						}
					}
				}	
			}
		}
		

		
		/* Comunicazioni */
		HashMap<String,String> comunicazioni= new HashMap<String,String>();
		comunicazioni.put("Circolare","Corso di recupero di Matematica domani alle 16 presso aula 2.6 .");
		comunicazioni.put("Premio","Premio per gli studenti che hanno vinto le olimpiadi di matematica.");
		comunicazioni.put("Comunicazione dalla presidenza",
				"Si terrà domani la simulazione di prima prova, tutti i docenti sono tenuti a far rispettare ordine e silenzio.");

		Comunicazione comunicazione = new Comunicazione();
		comunicazione.setDataOra(LocalDateTime.now(Configurazione.getInstance().getZoneId()));
		comunicazione.setOggetto("Comunicazione dalla presidenza");
		comunicazione.setContenuto(comunicazioni.get("Comunicazione dalla presidenza"));
		
		for(Professore prof: professori) {
			comunicazione.setDestinatario(prof);
			daoComunicazione.registraComunicazione(comunicazione);
		}
		
		comunicazione = new Comunicazione();
		comunicazione.setDataOra(LocalDateTime.now(Configurazione.getInstance().getZoneId()));
		comunicazione.setOggetto("Premio");
		comunicazione.setContenuto(comunicazioni.get("Premio"));
		comunicazione.setDestinatario(stud1);
		daoComunicazione.registraComunicazione(comunicazione);	
		
		comunicazione.setDestinatario(stud2);
		daoComunicazione.registraComunicazione(comunicazione);
		
		comunicazione.setDestinatario(stud3);
		daoComunicazione.registraComunicazione(comunicazione);
		
		comunicazione = new Comunicazione();
		comunicazione.setDataOra(LocalDateTime.now(Configurazione.getInstance().getZoneId()));
		comunicazione.setOggetto("Circolare");
		comunicazione.setContenuto(comunicazioni.get("Circolare"));
		for(Professore professore: professori) {
			comunicazione.setDestinatario(professore);
			daoComunicazione.registraComunicazione(comunicazione);
		}
		for(Studente studente: studenti) {
			comunicazione.setDestinatario(studente);
			daoComunicazione.registraComunicazione(comunicazione);
		}
		
	}


}
