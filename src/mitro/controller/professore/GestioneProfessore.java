package mitro.controller.professore;

import java.time.LocalDate;
import java.util.List;

import mitro.exceptions.OperazioneException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Comunicazione;
import mitro.model.Professore;

public interface GestioneProfessore {
	
	public Professore getProfessore() throws OperazioneException;
	public List<Classe> getListaClassi() throws OperazioneException;
	public List<Attivita> getListaAttivita(LocalDate from, LocalDate to) throws OperazioneException;
	public List<Comunicazione> getListaComunicazioni(LocalDate from, LocalDate to) throws OperazioneException;
	
}
