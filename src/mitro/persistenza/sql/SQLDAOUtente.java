package mitro.persistenza.sql;

import java.util.List;

import javax.sql.DataSource;

import mitro.exceptions.PersistenzaException;
import mitro.model.Iscritto;
import mitro.model.Ruolo;
import mitro.model.Utente;
import mitro.persistenza.Cifratura;
import mitro.persistenza.DAOUtente;

public class SQLDAOUtente implements DAOUtente {

	private DataSource dataSource;
	private Cifratura cifratura;
	
	public SQLDAOUtente(DataSource dataSource, Cifratura cifratura) {
		this.dataSource = dataSource;
		this.cifratura = cifratura;
	}

	@Override
	public void registraUtente(Utente utente) throws PersistenzaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificaUtente(Utente utente) throws PersistenzaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inserisciCredenziali(Utente utente, String username, String password) throws PersistenzaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminaUtente(Utente utente) throws PersistenzaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Utente ottieniUtentePerId(String id) throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utente ottieniUtentePerCredenziali(String username, String password) throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Utente> ottieniUtenti() throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Utente> ottieniUtentiPerRuolo(Ruolo ruolo) throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Iscritto> ottieniIscrittiPerNomeOCognome(String filtro) throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
