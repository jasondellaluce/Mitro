package mitro.persistenza;

import java.util.List;

import mitro.exceptions.PersistenzaException;
import mitro.model.Iscritto;
import mitro.model.Ruolo;
import mitro.model.Utente;

public interface DAOUtente {

	public void registraUtente(Utente utente) throws PersistenzaException;
	public void modificaUtente(Utente utente) throws PersistenzaException;
	public void inserisciCredenziali(Utente utente, String username, String password) throws PersistenzaException;
	public void eliminaUtente(Utente utente) throws PersistenzaException;
	public Utente ottieniUtentePerId(String id) throws PersistenzaException;
	public Utente ottieniUtentePerCredenziali(String username, String password) throws PersistenzaException;
	public List<Utente> ottieniUtenti() throws PersistenzaException;
	public List<Utente> ottieniUtentiPerRuolo(Ruolo ruolo) throws PersistenzaException;
	public List<Iscritto> ottieniIscrittiPerNomeOCognome(String filtro) throws PersistenzaException;

}
