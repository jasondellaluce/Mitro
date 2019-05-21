package mitro.controller.professore;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mitro.controller.log.LoggerOperazioni;
import mitro.controller.log.MockLoggerOperazioni;
import mitro.exceptions.OperazioneException;
import mitro.model.Professore;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.mock.CollectionDAOAttivita;
import mitro.persistenza.mock.CollectionDAOComunicazione;

public class GestioneProfessoreControllerTest {

	private static GestioneProfessoreController test;
	private static Professore professore;
	private static DAOComunicazione daoComunicazione;
	private static DAOAttivita daoAttivita;
	private static LoggerOperazioni logger;
	
	@BeforeAll
	static void inizializza() {
		professore = new Professore();
		professore.setId("TestID");
		professore.setNome("Federico");
		daoComunicazione = new CollectionDAOComunicazione();
		daoAttivita = new CollectionDAOAttivita();
		logger = new MockLoggerOperazioni();
		test = new GestioneProfessoreController(professore, daoComunicazione,
				daoAttivita, logger);
	}
	
	@Test
	void testGetProfessore() throws OperazioneException {
		assertEquals(test.getProfessore(), professore);
	}
	
}
