package mitro.controller.amministratore;

import java.util.List;

import mitro.exceptions.OperazioneException;
import mitro.model.Comunicazione;
import mitro.model.Iscritto;

public interface AmministrazioneIscritti {

	public void registraIscritto(Iscritto iscritto) throws OperazioneException;
	public void modificaIscritto(Iscritto iscritto) throws OperazioneException;
	public void inserisciCredenzialiIscritto(Iscritto iscritto, String username,
			String password) throws OperazioneException;
	public void registraComunicazione(Comunicazione comunicazione) throws OperazioneException;
	public List<Iscritto> cercaIscritti(String filtro) throws OperazioneException;	
	
}
