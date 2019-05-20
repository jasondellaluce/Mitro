package mitro.persistenza;

import java.util.List;

import mitro.exceptions.PersistenzaException;
import mitro.model.Archiviazione;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Professore;
import mitro.model.Studente;

public interface DAOArchiviazione {

	public void registraArchiviazione(Archiviazione archiviazione) throws PersistenzaException;
	public void modificaArchiviazione(Archiviazione archiviazione) throws PersistenzaException;
	public void eliminaArchiviazione(Archiviazione archiviazione) throws PersistenzaException;
	public List<Archiviazione> ottieniArchiviazioni() throws PersistenzaException;
	public List<Archiviazione> ottieniArchiviazioniPerStudente(Studente studente) throws PersistenzaException;
	public List<Archiviazione> ottieniArchiviazioniPerProfessore(Professore professore) throws PersistenzaException;
	public List<Archiviazione> ottieniArchiviazioniPerClasse(Classe classe) throws PersistenzaException;
	public List<Archiviazione> ottieniArchiviazioniPerAttivita(Attivita attivita) throws PersistenzaException;
	
}
