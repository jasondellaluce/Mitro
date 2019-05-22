package mitro.persistenza.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import mitro.exceptions.ElementoGiaPersistenteException;
import mitro.exceptions.PersistenzaException;

public abstract class SQLDAOAstratto {

	@FunctionalInterface
	interface PreparedStatementConsumer {
		public void apply(PreparedStatement statement) throws Exception;
	}
	
	@FunctionalInterface
	interface PreparedStatementFunction<T> {
		public T apply(PreparedStatement statement) throws Exception;
	}
	
	private DataSource dataSource;
	
	public SQLDAOAstratto(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	protected void eseguiUpdate(String query, PreparedStatementConsumer consumer)
			throws PersistenzaException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			
			consumer.apply(statement);
			
		}
		catch(SQLException e) {
			if(e.getMessage().contains("UNIQUE"))
				throw new ElementoGiaPersistenteException(e);
			
			throw new PersistenzaException(e);
		}
		catch(PersistenzaException e) {
			throw e;
		}
		catch(Exception e) {
			throw new PersistenzaException(e);
		}
	}
	
	protected <T> T eseguiQuery(String query, PreparedStatementFunction<T> function)
			throws PersistenzaException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			
			return function.apply(statement);
	
		}
		catch(SQLException e) {
			if(e.getMessage().contains("UNIQUE"))
				throw new ElementoGiaPersistenteException(e);
			
			throw new PersistenzaException(e);
		}
		catch(PersistenzaException e) {
			throw e;
		}
		catch(Exception e) {
			throw new PersistenzaException(e);
		}
	}

}
