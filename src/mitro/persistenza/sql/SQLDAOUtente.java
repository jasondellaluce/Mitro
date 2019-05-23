package mitro.persistenza.sql;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import mitro.exceptions.ElementoNonModificabileException;
import mitro.exceptions.ElementoNonPersistenteException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Amministratore;
import mitro.model.Classe;
import mitro.model.Iscritto;
import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.persistenza.Cifratura;
import mitro.persistenza.DAOClasse;
import mitro.persistenza.DAOUtente;

public class SQLDAOUtente extends SQLDAOAstratto implements DAOUtente {

	private DAOClasse daoClasse;
	private Cifratura cifratura;
	
	public SQLDAOUtente(DataSource dataSource, Cifratura cifratura) {
		super(dataSource);
		this.cifratura = cifratura;
		this.daoClasse = new SQLDAOClasse(dataSource, cifratura);
	}
	
	private String getCodiceRuolo(Ruolo ruolo) {
		switch(ruolo) {
			default:
				throw new IllegalArgumentException("ruolo");
			case AMMINISTRATORE:
				return "AM";
			case GESTORESICUREZZA:
				return "GS";
			case PROFESSORE:
				return "PR";
			case STUDENTE:
				return "ST";
		}
	}
	
	private Ruolo getRuoloDaCodice(String codice) {
		switch(codice) {
			default:
				throw new IllegalArgumentException("codice ruolo");
			case "AM":
				return Ruolo.AMMINISTRATORE;
			case "GS":
				return Ruolo.GESTORESICUREZZA;
			case "PR":
				return Ruolo.PROFESSORE;
			case "ST":
				return Ruolo.STUDENTE;
		}
	}
	
	private Utente parseResultSet(ResultSet resultSet) throws SQLException {
		Ruolo ruolo = getRuoloDaCodice(resultSet.getString("Ruolo"));
		switch(ruolo) {
			default:
				throw new IllegalArgumentException("ruolo");
			case GESTORESICUREZZA:
				Utente g = new Utente();
				g.setId(String.valueOf(resultSet.getInt("Id")));
				g.setRuolo(ruolo);
				return g;
			case AMMINISTRATORE:
				Amministratore a = new Amministratore();
				a.setId(String.valueOf(resultSet.getInt("Id")));
				a.setRuolo(ruolo);
				return a;
			case PROFESSORE:
				Professore p = new Professore();
				p.setId(String.valueOf(resultSet.getInt("Id")));
				p.setRuolo(ruolo);
				p.setNome(cifratura.decifra(resultSet.getString("Nome")));
				p.setCognome(cifratura.decifra(resultSet.getString("Cognome")));
				p.setEmail(cifratura.decifra(resultSet.getString("Email")));
				p.setIndirizzoResidenza(cifratura.decifra(resultSet.getString("Indirizzo")));
				p.setTelefono(cifratura.decifra(resultSet.getString("Telefono")));
				if(resultSet.getDate("DataNascita") != null)
					p.setDataNascita(resultSet.getDate("DataNascita").toLocalDate());
				return p;
			case STUDENTE:
				Studente s = new Studente();
				s.setId(String.valueOf(resultSet.getInt("Id")));
				s.setRuolo(ruolo);
				s.setNome(cifratura.decifra(resultSet.getString("Nome")));
				s.setCognome(cifratura.decifra(resultSet.getString("Cognome")));
				s.setEmail(cifratura.decifra(resultSet.getString("Email")));
				s.setIndirizzoResidenza(cifratura.decifra(resultSet.getString("Indirizzo")));
				s.setTelefono(cifratura.decifra(resultSet.getString("Telefono")));
				if(resultSet.getDate("DataNascita") != null)
					s.setDataNascita(resultSet.getDate("DataNascita").toLocalDate());
				if(resultSet.getInt("IdPartecipaIn") != 0) {
					Classe classe = new Classe();
					classe.setId(String.valueOf(resultSet.getInt("IdPartecipaIn")));
					s.setClasse(classe);
				}		
				return s;
		}
	}
	
	private int getNewId() throws PersistenzaException {
		return this.eseguiQuery("SELECT MAX(Id) FROM UTENTI", (s) -> {
			return s.executeQuery().getInt(1) + 1;
		});
	}
	
	@Override
	public void registraUtente(Utente utente) throws PersistenzaException {
		if(utente == null)
			throw new IllegalArgumentException("utente");
		
		if(utente instanceof Studente)
			registraUtente((Studente) utente);
		
		else if(utente instanceof Professore)
			registraUtente((Professore) utente);
		
		else {
			int newId = getNewId();
			
			String query = "INSERT INTO UTENTI (Id, Ruolo) VALUES(?, ?)";
			this.eseguiUpdate(query, (s) -> {
				s.setInt(1, newId);
				s.setString(2, getCodiceRuolo(utente.getRuolo()));		
				s.executeUpdate();
				utente.setId(String.valueOf(newId));
			});
		}
	}
	
	private void registraUtente(Professore utente) throws PersistenzaException {
		String query = "INSERT INTO UTENTI (Id, Ruolo, Nome, Cognome, Email, Indirizzo, Telefono, DataNascita)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		int newId = getNewId();
		this.eseguiUpdate(query, (s) -> {
			s.setInt(1, newId);
			s.setString(2, getCodiceRuolo(utente.getRuolo()));
			s.setString(3, cifratura.cifra(utente.getNome()));
			s.setString(4, cifratura.cifra(utente.getCognome()));
			s.setString(5, cifratura.cifra(utente.getEmail()));
			s.setString(6, cifratura.cifra(utente.getIndirizzoResidenza()));
			s.setString(7, cifratura.cifra(utente.getTelefono()));
			if(utente.getDataNascita() == null)
				s.setString(8, null);
			else 
				s.setDate(8, Date.valueOf(utente.getDataNascita()));
			s.executeUpdate();
			utente.setId(String.valueOf(newId));
		});
	}

	private void registraUtente(Studente utente) throws PersistenzaException {
		String query = "INSERT INTO UTENTI (Id, Ruolo, Nome, Cognome, Email, Indirizzo, Telefono,"
				+ "DataNascita, IdPartecipaIn) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		if(utente.getClasse() != null)
			if(daoClasse.ottieniClassePerId(utente.getClasse().getId()) == null)
				throw new ElementoNonPersistenteException("class");
		
		int newId = getNewId();
		this.eseguiUpdate(query, (s) -> {
			s.setInt(1, newId);
			s.setString(2, getCodiceRuolo(utente.getRuolo()));
			s.setString(3, cifratura.cifra(utente.getNome()));
			s.setString(4, cifratura.cifra(utente.getCognome()));
			s.setString(5, cifratura.cifra(utente.getEmail()));
			s.setString(6, cifratura.cifra(utente.getIndirizzoResidenza()));
			s.setString(7, cifratura.cifra(utente.getTelefono()));
			if(utente.getDataNascita() == null)
				s.setString(8, null);
			else 
				s.setDate(8, Date.valueOf(utente.getDataNascita()));
			if(utente.getClasse() == null)
				s.setNull(9, Types.INTEGER);
			else 
				s.setInt(9, Integer.parseInt(utente.getClasse().getId()));
			s.executeUpdate();
			utente.setId(String.valueOf(newId));
		});
	}

	@Override
	public void modificaUtente(Utente utente) throws PersistenzaException {
		if(utente == null)
			throw new IllegalArgumentException("utente");
		
		if(utente instanceof Studente)
			modificaUtente((Studente) utente);
		
		else if(utente instanceof Professore)
			modificaUtente((Professore) utente);
		
		else
			throw new ElementoNonModificabileException("ruolo=" + utente.getRuolo());
	}
	
	private void modificaUtente(Professore utente) throws PersistenzaException {
		String query = "UPDATE UTENTE SET Ruolo=?, Nome=?, Cognome=?, Email=?, Indirizzo=?,"
				+ "Telefono=?, DataNascita=? WHERE Id=?";
		this.eseguiUpdate(query, (s) -> {
			s.setString(1, getCodiceRuolo(utente.getRuolo()));
			s.setString(2, cifratura.cifra(utente.getNome()));
			s.setString(3, cifratura.cifra(utente.getCognome()));
			s.setString(4, cifratura.cifra(utente.getEmail()));
			s.setString(5, cifratura.cifra(utente.getIndirizzoResidenza()));
			s.setString(6, cifratura.cifra(utente.getTelefono()));
			if(utente.getDataNascita() == null)
				s.setString(7, null);
			else 
				s.setDate(7, Date.valueOf(utente.getDataNascita()));
			s.setInt(8, Integer.parseInt(utente.getId()));
			
			if(s.executeUpdate() != 1)
				throw new ElementoNonPersistenteException();
		});
	}

	private void modificaUtente(Studente utente) throws PersistenzaException {
		if(utente.getClasse() != null)
			if(daoClasse.ottieniClassePerId(utente.getClasse().getId()) == null)
				throw new ElementoNonPersistenteException("class");
		
		String query = "UPDATE UTENTI SET Ruolo=?, Nome=?, Cognome=?, Email=?, Indirizzo=?,"
				+ "Telefono=?, DataNascita=?, IdPartecipaIn=? WHERE Id=?";
		this.eseguiUpdate(query, (s) -> {
			s.setString(1, getCodiceRuolo(utente.getRuolo()));
			s.setString(2, cifratura.cifra(utente.getNome()));
			s.setString(3, cifratura.cifra(utente.getCognome()));
			s.setString(4, cifratura.cifra(utente.getEmail()));
			s.setString(5, cifratura.cifra(utente.getIndirizzoResidenza()));
			s.setString(6, cifratura.cifra(utente.getTelefono()));
			if(utente.getDataNascita() == null)
				s.setString(7, null);
			else 
				s.setDate(7, Date.valueOf(utente.getDataNascita()));
			if(utente.getClasse() == null)
				s.setNull(8, Types.INTEGER);
			else 
				s.setInt(8, Integer.parseInt(utente.getClasse().getId()));
			s.setInt(9, Integer.parseInt(utente.getId()));
			
			if(s.executeUpdate() != 1)
				throw new ElementoNonPersistenteException();
		});
	}
	
	@Override
	public void inserisciCredenziali(Utente utente, String username, String password) throws PersistenzaException {
		if(utente == null)
			throw new IllegalArgumentException("utente");
		if(username == null || username.length() == 0)
			throw new IllegalArgumentException("username");
		if(password == null || password.length() == 0)
			throw new IllegalArgumentException("password");
		
		String query = "UPDATE UTENTI SET Username=?, Password=? WHERE Id=?";
		this.eseguiUpdate(query, (s) -> {
			s.setString(1, cifratura.cifra(username));
			s.setString(2, cifratura.cifraHash(password));
			s.setInt(3, Integer.parseInt(utente.getId()));
			
			if(s.executeUpdate() != 1)
				throw new ElementoNonPersistenteException();
		});
	}

	@Override
	public void eliminaUtente(Utente utente) throws PersistenzaException {
		if(utente == null)
			throw new IllegalArgumentException("utente");
		
		String query = "DELETE FROM UTENTI WHERE Id=?";
		this.eseguiUpdate(query, (s) -> {
			s.setInt(1, Integer.parseInt(utente.getId()));	
			if(s.executeUpdate() != 1)
				throw new ElementoNonPersistenteException();
			utente.setId(null);
		});
	}

	@Override
	public Utente ottieniUtentePerId(String id) throws PersistenzaException {
		if(id == null || id.length() == 0)
			throw new IllegalArgumentException("id");
		
		String query = "SELECT * FROM UTENTI WHERE Id=?";
		Utente u = this.eseguiQuery(query, (s) -> {
			s.setInt(1, Integer.parseInt(id));
			ResultSet resultSet = s.executeQuery();
			return resultSet.isClosed() ? null : parseResultSet(resultSet);
		});
		if(u instanceof Studente) {
			Studente s = (Studente) u;
			if(s.getClasse() != null && s.getClasse().getId() != null)
				s.setClasse(daoClasse.ottieniClassePerId(s.getClasse().getId()));
		}
		return u;
	}

	@Override
	public Utente ottieniUtentePerCredenziali(String username, String password) throws PersistenzaException {
		if(username == null || username.length() == 0)
			throw new IllegalArgumentException("username");
		if(password == null || password.length() == 0)
			throw new IllegalArgumentException("password");
		
		String query = "SELECT * FROM UTENTI WHERE Username=? and Password=?";
		Utente u = this.eseguiQuery(query, (s) -> {
			s.setString(1, cifratura.cifra(username));
			s.setString(2, cifratura.cifraHash(password));
			ResultSet resultSet = s.executeQuery();
			return resultSet.isClosed() ? null : parseResultSet(resultSet);
		});
		if(u instanceof Studente) {
			Studente s = (Studente) u;
			if(s.getClasse() != null && s.getClasse().getId() != null)
				s.setClasse(daoClasse.ottieniClassePerId(s.getClasse().getId()));
		}
		return u;
	}

	@Override
	public List<Utente> ottieniUtenti() throws PersistenzaException {
		List<Utente> result = new ArrayList<>();
		String query = "SELECT * FROM UTENTI";
		this.eseguiQuery(query, (s) -> {
			ResultSet resultSet = s.executeQuery();
			while(resultSet.next())
				result.add(parseResultSet(resultSet));
			return result;
		});
		for(Utente u : result)
			if(u instanceof Studente) {
				Studente s = (Studente) u;
				if(s.getClasse() != null && s.getClasse().getId() != null)
					s.setClasse(daoClasse.ottieniClassePerId(s.getClasse().getId()));
			}
		return result;
	}

	@Override
	public List<Utente> ottieniUtentiPerRuolo(Ruolo ruolo) throws PersistenzaException {
		List<Utente> result = new ArrayList<>();
		String query = "SELECT * FROM UTENTI WHERE Ruolo=?";
		this.eseguiQuery(query, (s) -> {
			s.setString(1, getCodiceRuolo(ruolo));
			ResultSet resultSet = s.executeQuery();
			while(resultSet.next())
				result.add(parseResultSet(resultSet));
			return result;
		});
		for(Utente u : result)
			if(u instanceof Studente) {
				Studente s = (Studente) u;
				if(s.getClasse() != null && s.getClasse().getId() != null)
					s.setClasse(daoClasse.ottieniClassePerId(s.getClasse().getId()));
			}
		return result;
	}

	@Override
	public List<Iscritto> ottieniIscrittiPerNomeOCognome(String filtro) throws PersistenzaException {
		List<Iscritto> result = new ArrayList<>();
		String query = "SELECT * FROM UTENTI WHERE (Ruolo='ST' or Ruolo='PR') and (Nome=? or Cognome=?)";
		this.eseguiQuery(query, (s) -> {
			s.setString(1, cifratura.cifra(filtro));
			s.setString(2, cifratura.cifra(filtro));
			ResultSet resultSet = s.executeQuery();
			while(resultSet.next())
				result.add((Iscritto) parseResultSet(resultSet));
			return result;
		});
		for(Iscritto u : result)
			if(u instanceof Studente) {
				Studente s = (Studente) u;
				if(s.getClasse() != null && s.getClasse().getId() != null)
					s.setClasse(daoClasse.ottieniClassePerId(s.getClasse().getId()));
			}
		return result;
	}
	
}
