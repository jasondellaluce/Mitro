package mitro.persistenza;

import mitro.persistenza.sql.SQLDAOFactory;

public abstract class DAOFactory {

	private static DAOFactory instance;

	public static DAOFactory getInstance() {
		if(instance == null)
			instance = new SQLDAOFactory("database.db");
		return instance;
	}
	
	public abstract DAOUtente getDAOUtente();
	public abstract DAOComunicazione getDAOComunicazione();
	public abstract DAOClasse getDAOClasse();
	public abstract DAOArchiviazione getDAOArchiviazione();	
	public abstract DAOAttivita getDAOAttivita();
		
}
