package mitro.persistenza.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import mitro.exceptions.ElementoNonPersistenteException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Comunicazione;
import mitro.model.Iscritto;
import mitro.model.Studente;
import mitro.persistenza.Cifratura;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.DAOUtente;

public class SQLDAOComunicazione extends SQLDAOAstratto implements DAOComunicazione {

	private Cifratura cifratura;
	private DAOUtente daoUtente;

	public SQLDAOComunicazione(DataSource dataSource, Cifratura cifratura) {
		super(dataSource);
		this.cifratura = cifratura;
		this.daoUtente = new SQLDAOUtente(dataSource, cifratura);
	}
	
	private Comunicazione parseResultSet(ResultSet resultSet) throws SQLException {
		Comunicazione result = new Comunicazione();
		result.setDataOra(resultSet.getTimestamp("DataOra").toLocalDateTime());
		result.setOggetto(cifratura.decifra(resultSet.getString("Oggetto")));
		result.setContenuto(cifratura.decifra(resultSet.getString("Contenuto")));
		Iscritto destinatario = new Studente();
		destinatario.setId(String.valueOf(resultSet.getInt("IdDestinataA")));
		result.setDestinatario(destinatario);
		return result;
	}
	
	@Override
	public void registraComunicazione(Comunicazione comunicazione) throws PersistenzaException {
		if(comunicazione == null)
			throw new IllegalArgumentException("comunicazione");
		if(comunicazione.getDataOra() == null)
			throw new IllegalArgumentException("dataOra");
		if(comunicazione.getOggetto() == null)
			throw new IllegalArgumentException("oggetto");
		if(comunicazione.getContenuto() == null)
			throw new IllegalArgumentException("contenuto");
		if(comunicazione.getDestinatario() == null
				|| daoUtente.ottieniUtentePerId(comunicazione.getDestinatario().getId()) == null)
			throw new ElementoNonPersistenteException("destinatario");
		
		String query = "INSERT INTO COMUNICAZIONI (DataOra, IdDestinataA, Oggetto, Contenuto) " +
				"VALUES(?, ?, ?, ?)";
		this.eseguiUpdate(query, (s) -> {
			s.setTimestamp(1, Timestamp.valueOf(comunicazione.getDataOra()));
			s.setInt(2, Integer.parseInt(comunicazione.getDestinatario().getId()));
			s.setString(3, cifratura.cifra(comunicazione.getOggetto()));
			s.setString(4, cifratura.cifra(comunicazione.getContenuto()));
			s.executeUpdate();
		});
	}

	@Override
	public void modificaComunicazione(Comunicazione comunicazione) throws PersistenzaException {
		if(comunicazione == null)
			throw new IllegalArgumentException("comunicazione");
		if(comunicazione.getDataOra() == null)
			throw new IllegalArgumentException("dataOra");
		if(comunicazione.getOggetto() == null)
			throw new IllegalArgumentException("oggetto");
		if(comunicazione.getContenuto() == null)
			throw new IllegalArgumentException("contenuto");
		if(comunicazione.getDestinatario() == null
				|| daoUtente.ottieniUtentePerId(comunicazione.getDestinatario().getId()) == null)
			throw new ElementoNonPersistenteException("destinatario");
		
		String query = "UPDATE COMUNICAZIONI SET Oggetto=?, Contenuto=? WHERE DataOra=? and IdDestinataA=?" ;
		this.eseguiUpdate(query, (s) -> {
			s.setString(1, cifratura.cifra(comunicazione.getOggetto()));
			s.setString(2, cifratura.cifra(comunicazione.getContenuto()));
			s.setTimestamp(3, Timestamp.valueOf(comunicazione.getDataOra()));
			s.setInt(4, Integer.parseInt(comunicazione.getDestinatario().getId()));
			if(s.executeUpdate() != 1)
				throw new ElementoNonPersistenteException();
		});
	}

	@Override
	public void eliminaComunicazione(Comunicazione comunicazione) throws PersistenzaException {
		if(comunicazione == null)
			throw new IllegalArgumentException("comunicazione");
		if(comunicazione.getDataOra() == null)
			throw new IllegalArgumentException("dataOra");
		if(comunicazione.getDestinatario() == null)
			throw new ElementoNonPersistenteException("destinatario");
		
		String query = "DELETE FROM COMUNICAZIONI WHERE DataOra=? and IdDestinataA=?";
		this.eseguiUpdate(query, (s) -> {
			s.setTimestamp(1, Timestamp.valueOf(comunicazione.getDataOra()));
			s.setInt(2, Integer.parseInt(comunicazione.getDestinatario().getId()));
			if(s.executeUpdate() != 1)
				throw new ElementoNonPersistenteException();
		});
	}

	@Override
	public List<Comunicazione> ottieniComunicazioni() throws PersistenzaException {
		List<Comunicazione> result = new ArrayList<>();
		String query = "SELECT * FROM COMUNICAZIONI";
		this.eseguiQuery(query, (s) -> {
			ResultSet resultSet = s.executeQuery();
			while(resultSet.next())
				result.add(parseResultSet(resultSet));
			return result;
		});
		for(Comunicazione c : result)
			c.setDestinatario((Iscritto) daoUtente.ottieniUtentePerId(c.getDestinatario().getId()));
		return result;
	}

	@Override
	public List<Comunicazione> ottieniComunicazioniPerDestinatario(Iscritto destinatario) throws PersistenzaException {
		if(destinatario == null || destinatario.getId() == null)
			throw new IllegalArgumentException("destinatario");
		
		List<Comunicazione> result = new ArrayList<>();
		String query = "SELECT * FROM COMUNICAZIONI WHERE IdDestinataA=?";
		this.eseguiQuery(query, (s) -> {
			s.setInt(1, Integer.parseInt(destinatario.getId()));
			ResultSet resultSet = s.executeQuery();
			while(resultSet.next())
				result.add(parseResultSet(resultSet));
			return result;
		});
		for(Comunicazione c : result)
			c.setDestinatario((Iscritto) daoUtente.ottieniUtentePerId(c.getDestinatario().getId()));
		return result;
	}

	@Override
	public List<Comunicazione> ottieniComunicazioniPerData(LocalDate from, LocalDate to) throws PersistenzaException {
		if(from == null || to == null || to.isBefore(from))
			throw new IllegalArgumentException("from-to");
		
		List<Comunicazione> result = new ArrayList<>();
		String query = "SELECT * FROM COMUNICAZIONI WHERE DataOra >= ? and DataOra < ?";
		this.eseguiQuery(query, (s) -> {
			s.setTimestamp(1, Timestamp.valueOf(from.atTime(0, 0)));
			s.setTimestamp(2, Timestamp.valueOf(to.atTime(23, 59)));
			ResultSet resultSet = s.executeQuery();
			while(resultSet.next())
				result.add(parseResultSet(resultSet));
			return result;
		});
		for(Comunicazione c : result)
			c.setDestinatario((Iscritto) daoUtente.ottieniUtentePerId(c.getDestinatario().getId()));
		return result;
	}

}
