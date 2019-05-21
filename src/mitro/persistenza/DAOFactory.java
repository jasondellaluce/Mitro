package mitro.persistenza;

import javax.sql.DataSource;

import org.sqlite.SQLiteDataSource;

import mitro.persistenza.mock.MockCifratura;
import mitro.persistenza.sql.SQLDAOArchiviazione;
import mitro.persistenza.sql.SQLDAOAttivita;
import mitro.persistenza.sql.SQLDAOClasse;
import mitro.persistenza.sql.SQLDAOComunicazione;
import mitro.persistenza.sql.SQLDAOUtente;

public class DAOFactory {

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
		return new SQLDAOUtente(dataSourceCondiviso, new MockCifratura());
	}
	
	public DAOComunicazione getDAOComunicazione() {
		return new SQLDAOComunicazione(dataSourceCondiviso, new MockCifratura());
	}
	
	public DAOClasse getDAOClasse() {
		return new SQLDAOClasse(dataSourceCondiviso, new MockCifratura());
	}
	
	public DAOArchiviazione getDAOArchiviazione() {
		return new SQLDAOArchiviazione(dataSourceCondiviso, new MockCifratura());
	}
	
	public DAOAttivita getDAOAttivita() {
		return new SQLDAOAttivita(dataSourceCondiviso, new MockCifratura());
	}
	
	private DataSource creaDataSource() {
		SQLiteDataSource sqliteDataSource = new SQLiteDataSource();
		sqliteDataSource.setUrl("jdbc:sqlite:database.db"); 
		return sqliteDataSource;
	}
	
}
