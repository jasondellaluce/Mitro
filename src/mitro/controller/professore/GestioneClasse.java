package mitro.controller.professore;

import java.time.LocalDate;
import java.util.List;

import mitro.exceptions.OperazioneException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Presenza;
import mitro.model.Studente;
import mitro.model.Voto;

public interface GestioneClasse {

	public Classe getClasse() throws OperazioneException;
	public void inserisciAnnotazione(Attivita attivita, String annotazione) throws OperazioneException;
	public void inserisciPresenza(Presenza presenza) throws OperazioneException;
	public void registraVoto(Voto voto) throws OperazioneException;
	public List<Studente> getListaStudenti() throws OperazioneException;
	public List<Attivita> getListaAttivita(LocalDate from, LocalDate to) throws OperazioneException, PersistenzaException;
	public List<Presenza> getListaPresenze(LocalDate from, LocalDate to) throws OperazioneException, PersistenzaException;
	public List<Voto> getListaVoti(LocalDate from, LocalDate to) throws OperazioneException, PersistenzaException;
	
}
