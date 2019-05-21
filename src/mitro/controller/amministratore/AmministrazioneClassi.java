package mitro.controller.amministratore;

import java.util.List;

import mitro.exceptions.OperazioneException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Studente;
import mitro.model.Voto;

public interface AmministrazioneClassi {

	public void registraClasse(Classe classe) throws OperazioneException;
	public void modificaClasse(Classe classe) throws OperazioneException;
	public void registraAttivita(Attivita attivita) throws OperazioneException;
	public void eliminaAttivita(Attivita attivita) throws OperazioneException;
	public void modificaVoto(Voto voto) throws OperazioneException;
	public List<Attivita> getListaAttivita(Classe classe) throws OperazioneException;
	public List<Voto> getListaVoti(Studente studente) throws OperazioneException;
	public List<Classe> cercaClassi(String filtro) throws OperazioneException;
	
}
