package mitro.persistenza;

import javax.sql.DataSource;

import org.sqlite.SQLiteDataSource;

import mitro.controller.deployment.Configurazione;
import mitro.persistenza.cifrature.TestoInChiaro;
import mitro.persistenza.sql.SQLDAOArchiviazione;
import mitro.persistenza.sql.SQLDAOAttivita;
import mitro.persistenza.sql.SQLDAOClasse;
import mitro.persistenza.sql.SQLDAOComunicazione;
import mitro.persistenza.sql.SQLDAOUtente;

public class DAOFactory {

	public static String nomeFileDatabase = "database.db";
	private static DAOFactory instance;
	private DataSource dataSourceCondiviso;
	
	private DAOFactory() {
		this.dataSourceCondiviso = creaDataSource();
	}
	
	public static DAOFactory getInstance() {
		if(instance == null)
			instance = new DAOFactory();
		return instance;
	}
	
	public DAOUtente getDAOUtente() {
		return new SQLDAOUtente(dataSourceCondiviso, new TestoInChiaro());
	}
	
	public DAOComunicazione getDAOComunicazione() {
		return new SQLDAOComunicazione(dataSourceCondiviso, new TestoInChiaro());
	}
	
	public DAOClasse getDAOClasse() {
		return new SQLDAOClasse(dataSourceCondiviso, new TestoInChiaro());
	}
	
	public DAOArchiviazione getDAOArchiviazione() {
		return new SQLDAOArchiviazione(dataSourceCondiviso, new TestoInChiaro());
	}
	
	public DAOAttivita getDAOAttivita() {
		return new SQLDAOAttivita(dataSourceCondiviso, new TestoInChiaro());
	}
	
	private DataSource creaDataSource() {
		SQLiteDataSource sqliteDataSource = new SQLiteDataSource();
		sqliteDataSource.setUrl("jdbc:sqlite:" + Configurazione.PATH_RELATIVO 
				+ "/" + nomeFileDatabase); 
		return sqliteDataSource;
	}
	
}
