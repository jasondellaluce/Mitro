package mitro.controller.professore;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mitro.controller.log.LoggerOperazioni;
import mitro.controller.log.MockLoggerOperazioni;
import mitro.exceptions.OperazioneException;
import mitro.model.Classe;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOUtente;
import mitro.persistenza.collection.CollectionDAOArchiviazione;
import mitro.persistenza.collection.CollectionDAOAttivita;
import mitro.persistenza.collection.CollectionDAOUtente;

public class GestioneClasseControllerTest {

	private static GestioneClasseController test;
	private static Classe classe;
	private static DAOArchiviazione daoArchiviazione;
	private static DAOAttivita daoAttivita;
	private static LoggerOperazioni logger;
	
	@BeforeAll
	static void inizializza() {
		classe = new Classe();
		classe.setId("TestID");
		classe.setNome("Amir");
		daoArchiviazione = new CollectionDAOArchiviazione();
		daoAttivita = new CollectionDAOAttivita();
		logger = new MockLoggerOperazioni();
		test = new GestioneClasseController(daoArchiviazione,
				daoAttivita, logger);
	}
	
	@Test
	void testGetClasse() throws OperazioneException {
		assertEquals(test.getClasse(), classe);
	}
	
}
