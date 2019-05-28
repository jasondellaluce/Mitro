package mitro.persistenza.sql;

import javax.sql.DataSource;

import org.sqlite.SQLiteDataSource;

import mitro.deployment.Configurazione;
import mitro.persistenza.Cifratura;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOClasse;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.DAOFactory;
import mitro.persistenza.DAOUtente;
import mitro.persistenza.cifrature.TestoInChiaro;

public class SQLDAOFactory extends DAOFactory {

	private String nomeFileDatabase;
	private DataSource dataSourceCondiviso;
	private Cifratura cifratura;
	
	public SQLDAOFactory(String nomeFileDatabase) {
		this.nomeFileDatabase = nomeFileDatabase;
		this.dataSourceCondiviso = creaDataSource();
		this.cifratura = new TestoInChiaro();
	}
	
	@Override
	public DAOUtente getDAOUtente() {
		return new SQLDAOUtente(dataSourceCondiviso, cifratura);
	}
	
	@Override
	public DAOComunicazione getDAOComunicazione() {
		return new SQLDAOComunicazione(dataSourceCondiviso, cifratura);
	}
	
	@Override
	public DAOClasse getDAOClasse() {
		return new SQLDAOClasse(dataSourceCondiviso, cifratura);
	}
	
	@Override
	public DAOArchiviazione getDAOArchiviazione() {
		return new SQLDAOArchiviazione(dataSourceCondiviso, cifratura);
	}
	
	@Override
	public DAOAttivita getDAOAttivita() {
		return new SQLDAOAttivita(dataSourceCondiviso, cifratura);
	}
	
	private DataSource creaDataSource() {
		SQLiteDataSource sqliteDataSource = new SQLiteDataSource();
		sqliteDataSource.setUrl("jdbc:sqlite:" 
				+ Configurazione.getInstance().getPercorsoEsecuzione()
				+ "/" + nomeFileDatabase); 
		return sqliteDataSource;
	}

}
