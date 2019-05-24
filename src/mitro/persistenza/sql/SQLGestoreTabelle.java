package mitro.persistenza.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import mitro.exceptions.PersistenzaException;
import mitro.model.Amministratore;
import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.persistenza.Cifratura;
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
		DAOUtente daoUtente = new SQLDAOUtente(dataSource, cifratura);
		Amministratore u1 = new Amministratore();
		Studente u2 = new Studente();
		u2.setNome("Jason");
		u2.setCognome("Dellaluce");
		Professore u3 = new Professore();
		u3.setNome("Amir");
		u3.setCognome("Al Sadi");
		Utente u4 = new Utente();
		u4.setRuolo(Ruolo.GESTORESICUREZZA);
		
		daoUtente.registraUtente(u1);
		daoUtente.inserisciCredenziali(u1, "user1", "password");
		daoUtente.registraUtente(u2);
		daoUtente.inserisciCredenziali(u2, "user2", "password");
		daoUtente.registraUtente(u3);
		daoUtente.inserisciCredenziali(u3, "user3", "password");
		daoUtente.registraUtente(u4);
		daoUtente.inserisciCredenziali(u4, "user4", "password");
		
	}
}
