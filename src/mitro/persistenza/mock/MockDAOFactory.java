package mitro.persistenza.mock;

import java.util.ArrayList;
import java.util.Collection;
import mitro.model.*;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOClasse;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.DAOFactory;
import mitro.persistenza.DAOUtente;

public class MockDAOFactory extends DAOFactory {

	private Collection<Utente> utenti = new ArrayList<>();
	private Collection<Classe> classi = new ArrayList<>();
	private Collection<Attivita> attivita = new ArrayList<>();
	private Collection<Comunicazione> comunicazioni = new ArrayList<>();
	private Collection<Archiviazione> archiviazioni = new ArrayList<>();
	
	@Override
	public DAOUtente getDAOUtente() {
		return new CollectionDAOUtente(utenti);
	}

	@Override
	public DAOComunicazione getDAOComunicazione() {
		return new CollectionDAOComunicazione(comunicazioni);
	}

	@Override
	public DAOClasse getDAOClasse() {
		return new CollectionDAOClasse(classi);
	}

	@Override
	public DAOArchiviazione getDAOArchiviazione() {
		return new CollectionDAOArchiviazione(archiviazioni);
	}

	@Override
	public DAOAttivita getDAOAttivita() {
		return new CollectionDAOAttivita(attivita);
	}
	
}
