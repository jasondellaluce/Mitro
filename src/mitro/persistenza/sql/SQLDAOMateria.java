package mitro.persistenza.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import mitro.exceptions.ElementoNonPersistenteException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Materia;
import mitro.persistenza.Cifratura;

public class SQLDAOMateria extends SQLDAOAstratto {

	private Cifratura cifratura;

	public SQLDAOMateria(DataSource dataSource, Cifratura cifratura) {
		super(dataSource);
		this.cifratura = cifratura;
	}

	private int getNewId() throws PersistenzaException {
		return this.eseguiQuery("SELECT MAX(Id) FROM MATERIE", (s) -> {
			return s.executeQuery().getInt(1) + 1;
		});
	}
	
	public void registraMateria(Materia materia) throws PersistenzaException {
		if(materia == null)
			throw new IllegalArgumentException("materia");
		if(materia.getNome() == null || materia.getNome().length() == 0)
			throw new IllegalArgumentException("nome");
		
		String query = "INSERT INTO MATERIE (Id, Nome, Descrizione) "
				+ "VALUES(?, ?, ?)";
		
		int newId = getNewId();
		this.eseguiUpdate(query, (statement) -> {
			statement.setInt(1, newId);
			statement.setString(2, cifratura.cifra(materia.getNome()));
			statement.setString(3, cifratura.cifra(materia.getDescrizione()));
			
			statement.executeUpdate();
			materia.setId(String.valueOf(newId));
		});
	}
	
	public void modificaMateria(Materia materia) throws PersistenzaException {
		if(materia == null)
			throw new IllegalArgumentException("materia");
		if(materia.getId() == null || materia.getId().length() == 0)
			throw new IllegalArgumentException("id");
		if(materia.getNome() == null || materia.getNome().length() == 0)
			throw new IllegalArgumentException("nome");
		
		String query = "UPDATE MATERIE SET Nome=?, Descrizione=? WHERE Id=?";
		
		this.eseguiUpdate(query, (statement) -> {
			statement.setString(1, cifratura.cifra(materia.getNome()));
			statement.setString(2, cifratura.cifra(materia.getDescrizione()));
			statement.setInt(3, Integer.parseInt(materia.getId()));
			
			if(statement.executeUpdate() != 1)
				throw new ElementoNonPersistenteException();
		});
	}

	public void eliminaMateria(Materia materia) throws PersistenzaException {
		String query = "DELETE FROM MATERIE WHERE Id=?";
		
		this.eseguiUpdate(query, (statement) -> {
			statement.setInt(1, Integer.parseInt(materia.getId()));
			
			if(statement.executeUpdate() != 1)
				throw new ElementoNonPersistenteException();
			materia.setId(null);
		});
	}
	
	public Materia ottieniMateriaPerId(String id) throws PersistenzaException {
		String query = "SELECT * FROM MATERIE WHERE Id=?";
		
		return this.eseguiQuery(query, (statement) -> {
			statement.setInt(1, Integer.parseInt(id));
			
			ResultSet resultSet = statement.executeQuery();
			return resultSet.isClosed() ? null : parseResultSet(resultSet);
		});
	}

	private Materia parseResultSet(ResultSet resultSet) throws SQLException {
		Materia result = new Materia();
		result.setId(String.valueOf(resultSet.getInt("Id")));
		result.setNome(cifratura.decifra(resultSet.getString("Nome")));
		result.setDescrizione(cifratura.decifra(resultSet.getString("Descrizione")));
		return result;
	}

}
