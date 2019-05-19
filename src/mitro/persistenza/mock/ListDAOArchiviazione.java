package mitro.persistenza.mock;

import java.util.ArrayList;
import java.util.List;

import mitro.model.Archiviazione;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Professore;
import mitro.model.Studente;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.PersistenzaException;

public class ListDAOArchiviazione implements DAOArchiviazione {

	public List<Archiviazione> list = new ArrayList<>(100);
	
	@Override
	public void registraArchiviazione(Archiviazione archiviazione) throws PersistenzaException {
		
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
