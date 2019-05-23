package mitro.persistenza.sql;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import mitro.exceptions.ElementoNonPersistenteException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Materia;
import mitro.model.Professore;
import mitro.model.Studente;
import mitro.persistenza.Cifratura;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOClasse;
import mitro.persistenza.DAOUtente;

public class SQLDAOAttivita extends SQLDAOAstratto implements DAOAttivita {

	private Cifratura cifratura;
	private DAOClasse daoClasse;
	private DAOUtente daoUtente;
	private SQLDAOMateria daoMateria;

	public SQLDAOAttivita(DataSource dataSource, Cifratura cifratura) {
		super(dataSource);
		this.cifratura = cifratura;
		this.daoUtente = new SQLDAOUtente(dataSource, cifratura);
		this.daoClasse = new SQLDAOClasse(dataSource, cifratura);
		this.daoMateria = new SQLDAOMateria(dataSource, cifratura);
	}
	
	private Attivita parseResultSet(ResultSet resultSet) throws SQLException {
		Attivita result = new Attivita();
		result.setData(resultSet.getDate("Data").toLocalDate());
		result.setOraInizio(resultSet.getInt("OraInizio"));
		result.setAnnotazione(cifratura.decifra(resultSet.getString("Annotazione")));
		Professore destinatario = new Professore();
		destinatario.setId(String.valueOf(resultSet.getInt("IdReferenteDi")));
		result.setProfessore(destinatario);
		Classe classe = new Classe();
		classe.setId(String.valueOf(resultSet.getInt("IdSvoltaIn")));
		result.setClasse(classe);
		Materia materia = new Materia();
		materia.setId(String.valueOf(resultSet.getInt("IdInsegnataIn")));
		result.setMateria(materia);
		return result;
	}
	
	@Override
	public void registraAttivita(Attivita attivita) throws PersistenzaException {
		if(attivita == null)
			throw new IllegalArgumentException("attivita");
		if(attivita.getData() == null)
			throw new IllegalArgumentException("data");
		if(attivita.getClasse() == null
				|| daoClasse.ottieniClassePerId(attivita.getClasse().getId()) == null)
			throw new ElementoNonPersistenteException("classe");
		if(attivita.getProfessore() == null
				|| daoUtente.ottieniUtentePerId(attivita.getProfessore().getId()) == null)
			throw new ElementoNonPersistenteException("professore");
		if(attivita.getMateria() == null || attivita.getMateria().getId() == null)
			throw new ElementoNonPersistenteException("materia");
		
		if(daoMateria.ottieniMateriaPerId(attivita.getMateria().getId()) == null)
			daoMateria.registraMateria(attivita.getMateria());
		
		String query = "INSERT INTO ATTIVITA (Data, OraInizio, IdSvoltaIn, " +
				"IdInsegnataIn, IdReferenteDi, Annotazione) " +
				"VALUES(?, ?, ?, ?, ?, ?)";
		this.eseguiUpdate(query, (s) -> {
			s.setDate(1, Date.valueOf(attivita.getData()));
			s.setInt(2, attivita.getOraInizio());
			s.setInt(3, Integer.parseInt(attivita.getClasse().getId()));
			s.setInt(4, Integer.parseInt(attivita.getMateria().getId()));
			s.setInt(5, Integer.parseInt(attivita.getProfessore().getId()));
			s.setString(6, cifratura.cifra(attivita.getAnnotazione()));
			s.executeUpdate();
		});
	}

	@Override
	public void modificaAttivita(Attivita attivita) throws PersistenzaException {
		if(attivita == null)
			throw new IllegalArgumentException("attivita");
		if(attivita.getData() == null)
			throw new IllegalArgumentException("data");
		if(attivita.getClasse() == null
				|| daoClasse.ottieniClassePerId(attivita.getClasse().getId()) == null)
			throw new ElementoNonPersistenteException("classe");
		if(attivita.getProfessore() == null
				|| daoUtente.ottieniUtentePerId(attivita.getProfessore().getId()) == null)
			throw new ElementoNonPersistenteException("professore");
		if(attivita.getMateria() == null || attivita.getMateria().getId() == null)
			throw new ElementoNonPersistenteException("materia");
		
		// TODO: Handle Materia deletion (check if still referenced)
		
		if(daoMateria.ottieniMateriaPerId(attivita.getMateria().getId()) == null)
			daoMateria.registraMateria(attivita.getMateria());
		
		String query = "UPDATE ATTIVITA SET IdInsegnataIn=, IdReferenteDi=?, Annotazione=? "
				+ "WhHERE Data=? and OraInizio=? and IdSvoltaIn=?";
		this.eseguiUpdate(query, (s) -> {
			s.setInt(1, Integer.parseInt(attivita.getMateria().getId()));
			s.setInt(2, Integer.parseInt(attivita.getProfessore().getId()));
			s.setString(3, cifratura.cifra(attivita.getAnnotazione()));
			s.setDate(4, Date.valueOf(attivita.getData()));
			s.setInt(5, attivita.getOraInizio());
			s.setInt(6, Integer.parseInt(attivita.getClasse().getId()));
			if(s.executeUpdate() != 1)
				throw new ElementoNonPersistenteException();
		});
	}

	@Override
	public void eliminaAttivita(Attivita attivita) throws PersistenzaException {
		if(attivita == null)
			throw new IllegalArgumentException("attivita");
		if(attivita.getData() == null)
			throw new IllegalArgumentException("data");
		if(attivita.getClasse() == null
				|| daoClasse.ottieniClassePerId(attivita.getClasse().getId()) == null)
			throw new ElementoNonPersistenteException("classe");
		if(attivita.getMateria() == null || attivita.getMateria().getId() == null)
			throw new ElementoNonPersistenteException("materia");
		
		// TODO: Handle Materia deletion (check if still referenced)
		
		String query = "DELETE FROM ATTIVITA WHERE WhHERE Data=? and OraInizio=? and IdSvoltaIn=?";
		this.eseguiUpdate(query, (s) -> {
			s.setDate(1, Date.valueOf(attivita.getData()));
			s.setInt(2, attivita.getOraInizio());
			s.setInt(3, Integer.parseInt(attivita.getClasse().getId()));
			if(s.executeUpdate() != 1)
				throw new ElementoNonPersistenteException();
		});
	}

	@Override
	public List<Attivita> ottieniAttivita() throws PersistenzaException {
		List<Attivita> result = new ArrayList<>();
		String query = "SELECT * FROM ATTIVITA";
		this.eseguiQuery(query, (s) -> {
			ResultSet resultSet = s.executeQuery();
			while(resultSet.next())
				result.add(parseResultSet(resultSet));
			return result;
		});
		for(Attivita a : result) {
			a.setProfessore((Professore) daoUtente.ottieniUtentePerId(a.getProfessore().getId()));
			a.setClasse(daoClasse.ottieniClassePerId(a.getClasse().getId()));
			a.setMateria(daoMateria.ottieniMateriaPerId(a.getMateria().getId()));
		}
		return result;
	}

	@Override
	public List<Attivita> ottieniAttivitaPerClasse(Classe classe) throws PersistenzaException {
		if(classe == null || classe.getId() == null)
			throw new IllegalArgumentException("classe");
		
		List<Attivita> result = new ArrayList<>();
		String query = "SELECT * FROM ATTIVITA WHERE IdSvoltaIn=?";
		this.eseguiQuery(query, (s) -> {
			s.setInt(1, Integer.parseInt(classe.getId()));
			ResultSet resultSet = s.executeQuery();
			while(resultSet.next())
				result.add(parseResultSet(resultSet));
			return result;
		});
		for(Attivita a : result) {
			a.setProfessore((Professore) daoUtente.ottieniUtentePerId(a.getProfessore().getId()));
			a.setClasse(daoClasse.ottieniClassePerId(a.getClasse().getId()));
			a.setMateria(daoMateria.ottieniMateriaPerId(a.getMateria().getId()));
		}
		return result;
	}

	@Override
	public List<Attivita> ottieniAttivitaPerStudente(Studente studente) throws PersistenzaException {
		if(studente == null)
			throw new IllegalArgumentException("studente");
		return ottieniAttivitaPerClasse(studente.getClasse());
	}

	@Override
	public List<Attivita> ottieniAttivitaPerProfessore(Professore professore) throws PersistenzaException {
		if(professore == null || professore.getId() == null)
			throw new IllegalArgumentException("professore");
		
		List<Attivita> result = new ArrayList<>();
		String query = "SELECT * FROM ATTIVITA WHERE IdReferenteDi=?";
		this.eseguiQuery(query, (s) -> {
			s.setInt(1, Integer.parseInt(professore.getId()));
			ResultSet resultSet = s.executeQuery();
			while(resultSet.next())
				result.add(parseResultSet(resultSet));
			return result;
		});
		for(Attivita a : result) {
			a.setProfessore((Professore) daoUtente.ottieniUtentePerId(a.getProfessore().getId()));
			a.setClasse(daoClasse.ottieniClassePerId(a.getClasse().getId()));
			a.setMateria(daoMateria.ottieniMateriaPerId(a.getMateria().getId()));
		}
		return result;
	}

}
