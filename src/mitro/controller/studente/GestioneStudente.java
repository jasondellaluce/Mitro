package mitro.controller.studente;

import java.time.LocalDate;
import java.util.List;

import mitro.exceptions.OperazioneException;
import mitro.model.Attivita;
import mitro.model.Comunicazione;
import mitro.model.Presenza;
import mitro.model.Studente;
import mitro.model.Voto;

public interface GestioneStudente {
	
	public Studente getStudente() throws OperazioneException;
	public List<Attivita> getListaAttivita(LocalDate startDate, LocalDate endDate) throws OperazioneException;
	public List<Comunicazione> getListaComunicazioni(LocalDate startDate, LocalDate endDate) throws OperazioneException;
	public List<Voto> getListaVoti(LocalDate startDate, LocalDate endDate) throws OperazioneException;
	public List<Presenza> getListaPresenze(LocalDate startDate, LocalDate endDate) throws OperazioneException;

}
