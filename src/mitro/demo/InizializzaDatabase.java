package mitro.demo;

import org.sqlite.SQLiteDataSource;

import mitro.persistenza.cifrature.MockCifratura;
import mitro.persistenza.sql.SQLGestoreTabelle;

public class InizializzaDatabase {

	private final static String nomeFileDatabase = "database.db";
	
	public static void main(String[] args) throws Exception {
		SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + nomeFileDatabase); 
        SQLGestoreTabelle gestore = new SQLGestoreTabelle(ds, new MockCifratura());
        
		System.out.println("Eliminazione DataBase SQLite pre-esistente...");
		gestore.eliminaTabelle();
		
		System.out.println("Creazione nuovo DataBase SQLite...");
		gestore.creaTabelle();
		
		gestore.creaInformazioniIniziali();
		System.out.println("Database Inizializzato");
	}
	
}
