package mitro.persistenza.sql;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import mitro.exceptions.PersistenzaException;
import mitro.model.Comunicazione;
import mitro.model.Iscritto;
import mitro.persistenza.Cifratura;
import mitro.persistenza.DAOComunicazione;

public class SQLDAOComunicazione implements DAOComunicazione {

	private DataSource dataSource;
	private Cifratura cifratura;

	public SQLDAOComunicazione(DataSource dataSource, Cifratura cifratura) {
		this.dataSource = dataSource;
		this.cifratura = cifratura;
	}
	
	@Override
	public void registraComunicazione(Comunicazione comunicazione) throws PersistenzaException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificaComunicazione(Comunicazione comunicazione) throws PersistenzaException {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminaComunicazione(Comunicazione comunicazione) throws PersistenzaException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Comunicazione> ottieniComunicazioni() throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comunicazione> ottieniComunicazioniPerDestinatario(Iscritto destinatario) throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comunicazione> ottieniComunicazioniPerData(LocalDate from, LocalDate to) throws PersistenzaException {
		// TODO Auto-generated method stub
		return null;
	}

}
