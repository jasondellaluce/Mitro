package mitro.persistenza;

import java.util.List;

import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Professore;
import mitro.model.Studente;

public interface DAOAttivita {

	public void registraAttivita(Attivita attivita) throws PersistenzaException;
	public void modificaAttivita(Attivita attivita) throws PersistenzaException;
	public void eliminaAttivita(Attivita attivita) throws PersistenzaException;
	public List<Attivita> ottieniAttivita() throws PersistenzaException;
	public List<Attivita> ottieniAttivitaPerClasse(Classe classe) throws PersistenzaException;
	public List<Attivita> ottieniAttivitaPerStudente(Studente studente) throws PersistenzaException;
	public List<Attivita> ottieniAttivitaPerProfessore(Professore professore) throws PersistenzaException;
	
}
