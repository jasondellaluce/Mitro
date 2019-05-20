package mitro.persistenza;

import java.util.List;

import mitro.exceptions.PersistenzaException;
import mitro.model.Classe;

public interface DAOClasse {

	public void registraClasse(Classe classe) throws PersistenzaException;
	public void modificaClasse(Classe classe) throws PersistenzaException;
	public void eliminaClasse(Classe classe) throws PersistenzaException;
	public Classe ottieniClassePerId(String id) throws PersistenzaException;
	public List<Classe> ottieniClassi() throws PersistenzaException;
	public List<Classe> ottieniClassiPerNome(String nome) throws PersistenzaException;
	public List<Classe> ottieniClassiPerAnnoScolastico(String annoScolastico) throws PersistenzaException;
	
}
