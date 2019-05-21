package mitro.controller.professore;

import java.time.LocalDate;
import java.util.List;

import mitro.exceptions.OperazioneException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Comunicazione;
import mitro.model.Professore;

public class GestioneProfessoreController implements GestioneProfessore {

	@Override
	public Professore getProfessore() throws OperazioneException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Classe> getListaClassi() throws OperazioneException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attivita> getListaAttivita(LocalDate from, LocalDate to) throws OperazioneException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comunicazione> getListaComunicazioni(LocalDate from, LocalDate to) throws OperazioneException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
