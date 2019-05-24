package mitro.controller.studente;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mitro.controller.log.LoggerOperazioni;
import mitro.controller.log.MockLoggerOperazioni;
import mitro.exceptions.OperazioneException;
import mitro.model.Studente;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.mock.CollectionDAOArchiviazione;
import mitro.persistenza.mock.CollectionDAOAttivita;
import mitro.persistenza.mock.CollectionDAOComunicazione;

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

	@Test
	void testGetListaAttivita() throws OperazioneException {
		// TODO: Implement test
	}

	@Test
	void testGetListaComunicazioni() {
		// TODO: Implement test
	}

	@Test
	void testGetListaVoti() {
		// TODO: Implement test
	}

	@Test
	void testGetListaPresenze() {
		// TODO: Implement test
	}

}
