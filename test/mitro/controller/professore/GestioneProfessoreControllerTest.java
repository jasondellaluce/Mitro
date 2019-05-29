package mitro.controller.professore;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mitro.controller.log.LoggerOperazioni;
import mitro.controller.log.MockLoggerOperazioni;
import mitro.exceptions.OperazioneException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Comunicazione;
import mitro.model.Professore;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.DAOFactory;
import mitro.persistenza.mock.MockDAOFactory;

class GestioneProfessoreControllerTest {

	private static GestioneProfessoreController test;
	private static Professore professore;
	private static DAOComunicazione daoComunicazione;
	private static DAOAttivita daoAttivita;
	private static LoggerOperazioni logger;
	private static Classe classe;
	private static Classe classe2;
	private static Attivita attivita;
	private static Attivita attivita2;
	private static Comunicazione comunicazione;
	
	@BeforeAll
	static void inizializza() {
		professore = new Professore();
		professore.setId("TestID");
		professore.setNome("Federico");
		
		classe = new Classe();
		classe.setId("TestClasseID");
		classe.setNome("1B");
		
		classe2 = new Classe();
		classe2.setId("TestClasseID2");
		classe2.setNome("1C");
		
		attivita= new Attivita();
		attivita.setClasse(classe);
		attivita.setProfessore(professore);
		attivita.setData(LocalDate.of(2019,5,21));
		attivita.setOraInizio(1);
		
		attivita2= new Attivita();
		attivita2.setClasse(classe2);
		attivita2.setProfessore(professore);
		attivita2.setData(LocalDate.of(2002,1,11));
		attivita2.setOraInizio(2);
		
		comunicazione = new Comunicazione();
		comunicazione.setContenuto("Comunicazione di test professore");
		comunicazione.setDestinatario(professore);
		comunicazione.setDataOra(LocalDateTime.of(2019, 5, 21, 12, 31, 0, 0));
		
		DAOFactory factory = new MockDAOFactory();
		daoComunicazione = factory.getDAOComunicazione();
		daoAttivita = factory.getDAOAttivita();
		logger = new MockLoggerOperazioni();
		test = new GestioneProfessoreController(logger, daoComunicazione,
				daoAttivita, professore);
	}
	

	@Test
	void testGetProfessore() throws OperazioneException {
		assertEquals(test.getProfessore(),professore);
	}

	@Test
	void testGetListaClassi() throws PersistenzaException, OperazioneException {
		daoAttivita.registraAttivita(attivita);
		daoAttivita.registraAttivita(attivita2);
		assertTrue(test.getListaClassi().contains(classe));
		assertTrue(test.getListaClassi().contains(classe2));
	}

	@Test
	void testGetListaAttivita() throws PersistenzaException, OperazioneException {
		//attività registrate in precedenza
		assertTrue(test.getListaAttivita(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(attivita) &&
				!test.getListaAttivita(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(attivita2));
		assertTrue(!test.getListaAttivita(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 20) ).contains(attivita) &&
				test.getListaAttivita(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 20) ).contains(attivita2));		
		assertTrue(test.getListaAttivita(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 22) ).contains(attivita) &&
				test.getListaAttivita(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 22) ).contains(attivita2));
	}

	@Test
	void testGetListaComunicazioni() throws PersistenzaException, OperazioneException {
		daoComunicazione.registraComunicazione(comunicazione);
		assertTrue(test.getListaComunicazioni(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(comunicazione));
	}

}
