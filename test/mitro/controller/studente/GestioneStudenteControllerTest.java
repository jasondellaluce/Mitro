package mitro.controller.studente;

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
import mitro.model.Presenza;
import mitro.model.Professore;
import mitro.model.Studente;
import mitro.model.Voto;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.DAOFactory;
import mitro.persistenza.mock.MockDAOFactory;

class GestioneStudenteControllerTest {

	private static GestioneStudenteController test;
	private static Studente studente;
	private static DAOComunicazione daoComunicazione;
	private static DAOArchiviazione daoArchiviazione;
	private static DAOAttivita daoAttivita;
	private static LoggerOperazioni logger;
	private static Classe classe;
	private static Attivita attivita;
	private static Attivita attivita2;
	private static Comunicazione comunicazione;
	private static Professore professore;
	private static Voto voto;
	private static Voto voto2;
	private static Presenza presenza;
	private static Presenza presenza2;
	
	@BeforeAll
	static void inizializza() {
		
		professore = new Professore();
		professore.setId("TestID");
		professore.setNome("Federico");
		
		classe = new Classe();
		classe.setId("TestClasseID");
		classe.setNome("1B");
		
		studente= new Studente();
		studente.setClasse(classe);
		studente.setNome("Jason");
		studente.setCognome("Dellaluce");
		
		attivita= new Attivita();
		attivita.setClasse(classe);
		attivita.setData(LocalDate.of(2019,5,21));
		attivita.setOraInizio(1);
		
		attivita2= new Attivita();
		attivita2.setClasse(classe);
		attivita2.setData(LocalDate.of(2002,1,11));
		attivita2.setOraInizio(2);
		
		comunicazione = new Comunicazione();
		comunicazione.setContenuto("Comunicazione di test studente");
		comunicazione.setDestinatario(studente);
		comunicazione.setDataOra(LocalDateTime.of(2019, 5, 21, 12, 31, 0, 0));
		
		presenza= new Presenza();
		presenza.setAttivita(attivita);
		presenza.setStudente(studente);
		presenza.setValore(true);
		
		presenza2= new Presenza();
		presenza2.setAttivita(attivita2);
		presenza2.setStudente(studente);
		presenza2.setValore(false);
		
		voto= new Voto();
		voto.setAttivita(attivita);
		voto.setStudente(studente);
		voto.setValore(7);
		
		voto2= new Voto();
		voto2.setAttivita(attivita2);
		voto2.setStudente(studente);
		voto2.setValore(7);	
		
		DAOFactory factory = new MockDAOFactory();
		daoComunicazione = factory.getDAOComunicazione();
		daoArchiviazione = factory.getDAOArchiviazione();
		daoAttivita = factory.getDAOAttivita();
		logger = new MockLoggerOperazioni();
		test = new GestioneStudenteController(logger, daoComunicazione, daoArchiviazione,
				daoAttivita, studente);
	}
	
	@Test
	void testGetStudente() throws OperazioneException {
		assertEquals(test.getStudente(), studente);
	}

	@Test
	void testGetListaAttivita() throws OperazioneException, PersistenzaException {
		daoAttivita.registraAttivita(attivita);
		daoAttivita.registraAttivita(attivita2);
		assertTrue(test.getListaAttivita(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(attivita) &&
				!test.getListaAttivita(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(attivita2));
		assertTrue(!test.getListaAttivita(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 20) ).contains(attivita) &&
				test.getListaAttivita(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 20) ).contains(attivita2));		
		assertTrue(test.getListaAttivita(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 22) ).contains(attivita) &&
				test.getListaAttivita(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 22) ).contains(attivita2));
	}

	@Test
	void testGetListaComunicazioni() throws OperazioneException, PersistenzaException {
		daoComunicazione.registraComunicazione(comunicazione);
		assertTrue(test.getListaComunicazioni(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(comunicazione));
	}

	@Test
	void testGetListaVoti() throws PersistenzaException, OperazioneException {
		//voto già registrato in test sopra
		daoArchiviazione.registraArchiviazione(voto);
		daoArchiviazione.registraArchiviazione(voto2);
		assertTrue(test.getListaVoti(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(voto) &&
				!test.getListaVoti(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(voto2));
		assertTrue(!test.getListaVoti(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 20) ).contains(voto) &&
				test.getListaVoti(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 20) ).contains(voto2));		
		assertTrue(test.getListaVoti(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 22) ).contains(voto) &&
				test.getListaVoti(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 22) ).contains(voto2));
	}

	@Test
	void testGetListaPresenze() throws PersistenzaException, OperazioneException {
		daoArchiviazione.registraArchiviazione(presenza);
		daoArchiviazione.registraArchiviazione(presenza2);
		assertTrue(test.getListaPresenze(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(presenza) &&
				!test.getListaPresenze(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(presenza2));
		assertTrue(!test.getListaPresenze(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 20) ).contains(presenza) &&
				test.getListaPresenze(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 20) ).contains(presenza2));		
		assertTrue(test.getListaPresenze(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 22) ).contains(presenza) &&
				test.getListaPresenze(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 22) ).contains(presenza2));	
	}

}
