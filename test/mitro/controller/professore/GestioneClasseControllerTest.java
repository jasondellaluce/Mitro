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
import mitro.persistenza.mock.CollectionDAOArchiviazione;
import mitro.persistenza.mock.CollectionDAOAttivita;
import mitro.persistenza.mock.CollectionDAOUtente;

public class GestioneClasseControllerTest {

	private static GestioneClasseController test;
	private static Classe classe;
	private static DAOArchiviazione daoArchiviazione;
	private static DAOAttivita daoAttivita;
	private static DAOUtente daoUtente;
	private static LoggerOperazioni logger;
	
	@BeforeAll
	static void inizializza() {
		classe = new Classe();
		classe.setId("TestID");
		classe.setNome("Amir");
		daoArchiviazione = new CollectionDAOArchiviazione();
		daoAttivita = new CollectionDAOAttivita();
		daoUtente = new CollectionDAOUtente();
		logger = new MockLoggerOperazioni();
		test = new GestioneClasseController(logger, daoArchiviazione,
				daoAttivita, daoUtente);
	}
	
	@Test
	void testGetClasse() throws OperazioneException {
		assertEquals(test.getClasse(), classe);
	}
	
}
