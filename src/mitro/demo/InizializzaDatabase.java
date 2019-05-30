package mitro.demo;

import javax.servlet.ServletException;

import mitro.persistenza.DAOFactory;

public class InizializzaDatabase {
	
	public static void main(String[] args) throws Exception {
		DAOFactory factory = DAOFactory.getInstance();
        
        try {
			System.out.print("Eliminazione DataBase SQLite pre-esistente...\n");
			factory.cancellaDati();
			
			System.out.print("Creazione nuovo DataBase SQLite...\n");
			factory.inizializzaDati();
			
			System.out.print("Database Inizializzato\n");
        }
        catch(Exception e) {
        	throw new ServletException(e);
        }
	}
	
}
