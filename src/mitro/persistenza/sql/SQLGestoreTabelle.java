package mitro.persistenza.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

import javax.sql.DataSource;

import mitro.controller.deployment.Configurazione;
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
import mitro.persistenza.Cifratura;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOClasse;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.DAOUtente;

public class SQLGestoreTabelle {

	private DataSource dataSource;
	private Cifratura cifratura;

	public SQLGestoreTabelle(DataSource dataSource, Cifratura cifratura) {
		this.dataSource = dataSource;
		this.cifratura = cifratura;
	}

	public void creaTabelle() throws SQLException {
		 try (Connection connessione = dataSource.getConnection()) {
			 creaTabellaClassi(connessione);
	         creaTabellaUtenti(connessione);
	         creaTabellaComunicazioni(connessione);
	         creaTabellaMaterie(connessione);
	         creaTabellaAttivita(connessione);
	         creaTabellaArchiviazioni(connessione);
		 }      
	}
	
	public void eliminaTabelle() throws SQLException {
		try (Connection connessione = dataSource.getConnection()) {
			eliminaTabella(connessione, "ARCHIVIAZIONI");
			eliminaTabella(connessione, "ATTIVITA");
			eliminaTabella(connessione, "MATERIE");
			eliminaTabella(connessione, "COMUNICAZIONI");
			eliminaTabella(connessione, "UTENTI");
			eliminaTabella(connessione, "CLASSI");
		}
	}

	private void creaTabellaArchiviazioni(Connection connection) throws SQLException {
		String query = 
				"CREATE TABLE ARCHIVIAZIONI (\r\n" + 
				"    Id                 INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
				"    IdRegistrataPer    REFERENCES UTENTI NOT NULL,\r\n" + 
				"    IdRegistrataIn     REFERENCES ATTIVIA NOT NULL,\r\n" + 
				"    Tipo               VARCHAR(10) NOT NULL,\r\n" + 
				"    ValoreVoto         DOUBLE,\r\n" + 
				"    ValorePresenza     BOOLEAN,\r\n" + 
				"    \r\n" + 
				"    UNIQUE             (IdRegistrataPer, IdRegistrataIn, Tipo),\r\n" + 
				"    CONSTRAINT TipiAmmessi     CHECK (Tipo='VO' or Tipo='PR'),\r\n" + 
				"    CONSTRAINT TipiCorretti    CHECK (\r\n" + 
				"        (Tipo='VO' and ValorePresenza IS NULL and ValoreVoto IS NOT NULL) or\r\n" + 
				"        (Tipo='PR' and ValorePresenza IS NOT NULL and ValoreVoto IS NULL))\r\n" + 
				");";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	private void creaTabellaAttivita(Connection connection) throws SQLException {
		String query = 
				"CREATE TABLE ATTIVITA (\r\n" + 
				"    Id                 INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
				"    Data               DATE NON NULL,\r\n" + 
				"    OraInizio          INT NOT NULL,\r\n" + 
				"    IdSvoltaIn         REFERENCES CLASSI NOT NULL,\r\n" + 
				"    IdInsegnataIn      REFERENCES MATERIE NOT NULL,\r\n" + 
				"    IdReferenteDi      REFERENCES UTENTI NOT NULL,\r\n" +
				"    Annotazione        VARCHAR(1000),\r\n" + 
				"    \r\n" + 
				"    UNIQUE             (Data, OraInizio, IdSvoltaIn)\r\n" + 
				");";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	private void creaTabellaMaterie(Connection connection) throws SQLException {
		String query = 
				"CREATE TABLE MATERIE (\r\n" + 
				"    Id                INTEGER NOT NULL PRIMARY KEY,\r\n" + 
				"    Nome              VARCHAR(50) NOT NULL,\r\n" + 
				"    Descrizione       VARCHAR(1024)\r\n" + 
				");";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	private void creaTabellaComunicazioni(Connection connection) throws SQLException {
		String query = 
				"CREATE TABLE COMUNICAZIONI (\r\n" + 
				"    Id                 INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
				"    DataOra            TIMESTAMP NOT NULL,\r\n" + 
				"    IdDestinataA       REFERENCES UTENTI NOT NULL,\r\n" + 
				"    Oggetto            VARCHAR(100) NOT NULL,\r\n" + 
				"    Contenuto          TEXT NOT NULL,\r\n" + 
				"    \r\n" + 
				"    UNIQUE             (DataOra, IdDestinataA)\r\n" + 
				");";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	private void creaTabellaUtenti(Connection connection) throws SQLException {
		String query = 
				"CREATE TABLE UTENTI (\r\n" + 
				"    Id                INTEGER NOT NULL PRIMARY KEY,\r\n" + 
				"    Username          VARCHAR(32),\r\n" + 
				"    Password          VARCHAR(32),\r\n" + 
				"    Ruolo             VARCHAR(10) NOT NULL,\r\n" + 
				"    Nome              VARCHAR(50),\r\n" + 
				"    Cognome           VARCHAR(50),\r\n" + 
				"    Email             VARCHAR(256),\r\n" + 
				"    Indirizzo         VARCHAR(128),\r\n" + 
				"    Telefono          VARCHAR(16),\r\n" + 
				"    DataNascita       DATE,\r\n" + 
				"    IdPartecipaIn     REFERENCES CLASSI,\r\n" + 
				"    \r\n" + 
				"    CONSTRAINT UsernameUnico   UNIQUE(Username),\r\n" + 
				"    CONSTRAINT RuoliAmmessi    CHECK (Ruolo='AM' or Ruolo='GS' or Ruolo='PR' or Ruolo='ST'),\r\n" + 
				"    CONSTRAINT RuoliCorreti    CHECK (\r\n" + 
				"        (Ruolo='AM' and Nome IS NULL and Cognome IS NULL\r\n" + 
				"            and Email IS NULL and Indirizzo IS NULL and Telefono IS NULL\r\n" + 
				"            and DataNascita IS NULL and IdPartecipaIn IS NULL) or\r\n" + 
				"        (Ruolo='GS' and Nome IS NULL and Cognome IS NULL\r\n" + 
				"            and Email IS NULL and Indirizzo IS NULL and Telefono IS NULL\r\n" + 
				"            and DataNascita IS NULL and IdPartecipaIn IS NULL) or\r\n" + 
				"        (Ruolo='PR' and Nome IS NOT NULL and Cognome IS NOT NULL and IdPartecipaIn IS NULL) or\r\n" + 
				"        (Ruolo='ST' and Nome IS NOT NULL and Cognome IS NOT NULL))\r\n" + 
				");";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	private void creaTabellaClassi(Connection connection) throws SQLException {
		String query = 
				"CREATE TABLE CLASSI (\r\n" + 
				"    Id                INTEGER NOT NULL PRIMARY KEY,\r\n" + 
				"    Nome              VARCHAR(50) NOT NULL,\r\n" + 
				"    AnnoScolastico    VARCHAR(20) NOT NULL,\r\n" + 
				"    Descrizione       VARCHAR(255),\r\n" + 
				"    \r\n" + 
				"    CONSTRAINT ClasseInAnno UNIQUE(Nome, AnnoScolastico)\r\n" + 
				");";
		Statement statement = connection.createStatement();
        statement.executeUpdate(query);
		statement.close();
	}
	
	private void eliminaTabella(Connection connection, String nome) throws SQLException {
		try {
			String query = "DROP TABLE " + nome;
			Statement statement = connection.createStatement();
	        statement.executeUpdate(query);
			statement.close();
		}
		catch (SQLException e) {
			if(!e.getMessage().contains("no such table"))
				throw e;
		}
	}
	
	public void creaInformazioniIniziali() throws PersistenzaException {
		Random random = new Random(System.currentTimeMillis());
		DAOUtente daoUtente = new SQLDAOUtente(dataSource, cifratura);
		DAOClasse daoClasse = new SQLDAOClasse(dataSource, cifratura);
		DAOAttivita daoAttivita = new SQLDAOAttivita(dataSource, cifratura);
		DAOComunicazione daoComunicazione = new SQLDAOComunicazione(dataSource, cifratura);
		
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
		LocalDate startDate = LocalDate.now(Configurazione.ZONE_ID).with(DayOfWeek.MONDAY);
		for(int i = 0; i < 6; i++) {
			for(int j = 8; j <= 12; j++) {
				Attivita att = new Attivita();
				att.setProfessore(Arrays.asList(prof1, prof2).get(random.nextInt(2)));
				att.setData(startDate.plusDays(i));
				att.setClasse(Arrays.asList(c1, c2, c3).get(random.nextInt(3)));
				att.setOraInizio(j);
				switch(random.nextInt(2)) {
					default:
					case 0:
						att.setMateria(m1);
						daoAttivita.registraAttivita(att);
						break;
					case 1:
						att.setMateria(m2);
						daoAttivita.registraAttivita(att);
						break;
				}
			}
		}
		
		/* Comunicazioni */
		Comunicazione c = new Comunicazione();
		c.setDataOra(LocalDateTime.now());
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
		
		c.setDataOra(LocalDateTime.now().minusHours(3));
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
		
		c.setDataOra(LocalDateTime.now().minusDays(8));
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
