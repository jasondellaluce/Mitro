package mitro.controller.studente;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mitro.controller.log.LoggerOperazioni;
import mitro.controller.log.MockLoggerOperazioni;
import mitro.exceptions.OperazioneException;
import mitro.model.Comunicazione;
import mitro.model.Studente;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.collection.CollectionDAOArchiviazione;
import mitro.persistenza.collection.CollectionDAOAttivita;
import mitro.persistenza.collection.CollectionDAOComunicazione;

class GestioneStudenteControllerTest {

	private static GestioneStudenteController test;
	private static Studente studente;
	private static DAOComunicazione daoComunicazione;
	private static DAOArchiviazione daoArchiviazione;
	private static DAOAttivita daoAttivita;
	private static LoggerOperazioni logger;
	
	@BeforeAll
	static void inizializza() {
		studente = new Studente();
		studente.setId("TestID");
		studente.setNome("Amir");
		daoComunicazione = new CollectionDAOComunicazione();
		daoArchiviazione = new CollectionDAOArchiviazione();
		daoAttivita = new CollectionDAOAttivita();
		logger = new MockLoggerOperazioni();
		test = new GestioneStudenteController(logger, daoComunicazione, daoArchiviazione,
				daoAttivita, studente);
	}
	
	@Test
	void testGetStudente() throws OperazioneException {
		assertEquals(test.getStudente(), studente);
	}

	/*@Test
	void testGetListaAttivita() {
		fail("Not yet implemented");
	}*/

	@Test
	void testGetListaComunicazioni() {
		Comunicazione c1 = new Comunicazione();
		
		
	}

	@Test
	void testGetListaVoti() {
		fail("Not yet implemented");
	}

	@Test
	void testGetListaPresenze() {
		fail("Not yet implemented");
	}

}
