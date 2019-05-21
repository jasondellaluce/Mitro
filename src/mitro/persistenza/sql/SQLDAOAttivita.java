package mitro.persistenza.sql;

import java.util.List;

import javax.sql.DataSource;

import mitro.exceptions.PersistenzaException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Professore;
import mitro.model.Studente;
import mitro.persistenza.Cifratura;
import mitro.persistenza.DAOAttivita;

public class SQLDAOAttivita implements DAOAttivita {

	private DataSource dataSource;
	private Cifratura cifratura;

	public SQLDAOAttivita(DataSource dataSource, Cifratura cifratura) {
		this.dataSource = dataSource;
		this.cifratura = cifratura;
	}
	
	@Override
	public void registraAttivita(Attivita attivita) throws PersistenzaException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificaAttivita(Attivita attivita) throws PersistenzaException {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminaAttivita(Attivita attivita) throws PersistenzaException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Attivita> ottieniAttivita() throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attivita> ottieniAttivitaPerClasse(Classe classe) throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attivita> ottieniAttivitaPerStudente(Studente studente) throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attivita> ottieniAttivitaPerProfessore(Professore professore) throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

}
