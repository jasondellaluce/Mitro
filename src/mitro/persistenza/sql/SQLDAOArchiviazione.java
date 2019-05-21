package mitro.persistenza.sql;

import java.util.List;

import javax.sql.DataSource;

import mitro.exceptions.PersistenzaException;
import mitro.model.Archiviazione;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Professore;
import mitro.model.Studente;
import mitro.persistenza.Cifratura;
import mitro.persistenza.DAOArchiviazione;

public class SQLDAOArchiviazione implements DAOArchiviazione {

	private DataSource dataSource;
	private Cifratura cifratura;

	public SQLDAOArchiviazione(DataSource dataSource, Cifratura cifratura) {
		this.dataSource = dataSource;
		this.cifratura = cifratura;
	}
	
	@Override
	public void registraArchiviazione(Archiviazione archiviazione) throws PersistenzaException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificaArchiviazione(Archiviazione archiviazione) throws PersistenzaException {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminaArchiviazione(Archiviazione archiviazione) throws PersistenzaException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Archiviazione> ottieniArchiviazioni() throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioniPerStudente(Studente studente) throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioniPerProfessore(Professore professore) throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioniPerClasse(Classe classe) throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioniPerAttivita(Attivita attivita) throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

}
