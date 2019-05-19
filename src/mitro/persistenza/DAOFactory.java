package mitro.persistenza;

public class DAOFactory {

	private static DAOFactory instance;
	
	private DAOFactory() {
		
	}
	
	public static DAOFactory getInstance() {
		if(instance == null)
			instance = new DAOFactory();
		return instance;
	}
	
	public DAOUtente getDAOUtente() {
		return null;
	}
	
	public DAOComunicazione getDAOComunicazione() {
		return null;
	}
	
	public DAOClasse getDAOClasse() {
		return null;
	}
	
	public DAOArchiviazione getDAOArchiviazione() {
		return null;
	}
	
	public DAOAttivita getDAOAttivita() {
		return null;
	}
	
}
