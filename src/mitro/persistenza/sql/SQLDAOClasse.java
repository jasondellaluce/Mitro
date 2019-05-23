package mitro.persistenza.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import mitro.exceptions.ElementoNonPersistenteException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Classe;
import mitro.persistenza.Cifratura;
import mitro.persistenza.DAOClasse;

public class SQLDAOClasse extends SQLDAOAstratto implements DAOClasse {

	private Cifratura cifratura;
	
	public SQLDAOClasse(DataSource dataSource, Cifratura cifratura) {
		super(dataSource);
		this.cifratura = cifratura;
	}
	
	private Classe parseResultSet(ResultSet resultSet) throws SQLException {
		Classe result = new Classe();
		result.setId(String.valueOf(resultSet.getInt("Id")));
		result.setNome(cifratura.decifra(resultSet.getString("Nome")));
		result.setAnnoScolastico(cifratura.decifra(resultSet.getString("AnnoScolastico")));
		result.setDescrizione(cifratura.decifra(resultSet.getString("Descrizione")));
		return result;
	}
	
	private int getNewId() throws PersistenzaException {
		return this.eseguiQuery("SELECT MAX(Id) FROM CLASSI", (s) -> {
			return s.executeQuery().getInt(1) + 1;
		});
	}
	
	@Override
	public void registraClasse(Classe classe) throws PersistenzaException {
		String query = "INSERT INTO CLASSI (Id, Nome, AnnoScolastico, Descrizione) "
				+ "VALUES(?, ?, ?, ?)";
		
		int newId = getNewId();
		this.eseguiUpdate(query, (statement) -> {
			statement.setInt(1, newId);
			statement.setString(2, cifratura.cifra(classe.getNome()));
			statement.setString(3, cifratura.cifra(classe.getAnnoScolastico()));
			statement.setString(4, cifratura.cifra(classe.getDescrizione()));
			
			statement.executeUpdate();
			classe.setId(String.valueOf(newId));
		});
	}

	@Override
	public void modificaClasse(Classe classe) throws PersistenzaException {
		String query = "UPDATE CLASSI SET Nome=?, AnnoScolastico=?, Descrizione=? "
				+ "WHERE Id=?";
		
		this.eseguiUpdate(query, (statement) -> {
			statement.setString(1, cifratura.cifra(classe.getNome()));
			statement.setString(2, cifratura.cifra(classe.getAnnoScolastico()));
			statement.setString(3, cifratura.cifra(classe.getDescrizione()));
			statement.setInt(4, Integer.parseInt(classe.getId()));
			
			if(statement.executeUpdate() != 1)
				throw new ElementoNonPersistenteException();
		});
	}

	@Override
	public void eliminaClasse(Classe classe) throws PersistenzaException {
		String query = "DELETE FROM CLASSI WHERE Id=?";
		
		this.eseguiUpdate(query, (statement) -> {
			statement.setInt(1, Integer.parseInt(classe.getId()));
			
			if(statement.executeUpdate() != 1)
				throw new ElementoNonPersistenteException("Id=" + classe.getId());
			classe.setId(null);
		});
	}

	@Override
	public Classe ottieniClassePerId(String id) throws PersistenzaException {
		String query = "SELECT * FROM CLASSI WHERE Id=?";
		
		return this.eseguiQuery(query, (statement) -> {
			statement.setInt(1, Integer.parseInt(id));
			
			ResultSet resultSet = statement.executeQuery();
			return resultSet.isClosed() ? null : parseResultSet(resultSet);
		});
	}

	@Override
	public List<Classe> ottieniClassi() throws PersistenzaException {
		List<Classe> result = new ArrayList<>();
		String query = "SELECT * FROM CLASSI";
		
		return this.eseguiQuery(query, (statement) -> {
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
				result.add(parseResultSet(resultSet));
			return result;
		});
	}

	@Override
	public List<Classe> ottieniClassiPerNome(String nome) throws PersistenzaException {
		List<Classe> result = new ArrayList<>();
		String query = "SELECT * FROM CLASSI WHERE Nome=?";
		
		return this.eseguiQuery(query, (statement) -> {
			statement.setString(1, cifratura.cifra(nome));
			
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
				result.add(parseResultSet(resultSet));
			return result;
		});
	}

	@Override
	public List<Classe> ottieniClassiPerAnnoScolastico(String annoScolastico) throws PersistenzaException {
		List<Classe> result = new ArrayList<>();
		String query = "SELECT * FROM CLASSI WHERE AnnoScolastico=?";
		
		return this.eseguiQuery(query, (statement) -> {
			statement.setString(1, cifratura.cifra(annoScolastico));
			
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
				result.add(parseResultSet(resultSet));
			return result;
		});
	}

}
