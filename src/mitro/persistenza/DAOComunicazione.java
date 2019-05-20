package mitro.persistenza;

import java.time.LocalDate;
import java.util.List;

import mitro.exceptions.PersistenzaException;
import mitro.model.Comunicazione;
import mitro.model.Iscritto;

public interface DAOComunicazione {

	public void registraComunicazione(Comunicazione comunicazione) throws PersistenzaException;
	public void modificaComunicazione(Comunicazione comunicazione) throws PersistenzaException;
	public void eliminaComunicazione(Comunicazione comunicazione) throws PersistenzaException;
	public List<Comunicazione> ottieniComunicazioni() throws PersistenzaException;
	public List<Comunicazione> ottieniComunicazioniPerDestinatario(Iscritto destinatario) throws PersistenzaException;
	public List<Comunicazione> ottieniComunicazioniPerData(LocalDate from, LocalDate to) throws PersistenzaException;
	
}
