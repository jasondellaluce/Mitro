package mitro.controller.professore;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mitro.controller.log.LoggerOperazioni;
import mitro.controller.log.MockLoggerOperazioni;
import mitro.exceptions.OperazioneException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Presenza;
import mitro.model.Studente;
import mitro.model.Voto;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOFactory;
import mitro.persistenza.DAOUtente;
import mitro.persistenza.mock.MockDAOFactory;

class GestioneClasseControllerTest {

	private static GestioneClasseController test;
	private static DAOArchiviazione daoArchiviazione;
	private static DAOAttivita daoAttivita;
	private static DAOUtente daoUtente;
	private static LoggerOperazioni logger;
	private static Presenza presenza;
	private static Presenza presenza2;
	private static Voto voto;
	private static Voto voto2;
	private static Attivita attivita;
	private static Attivita attivita2;
	private static Classe classe;	
	private static Studente studente;	
	
	@BeforeAll
	static void inizializza() {		
		
		classe = new Classe();
		classe.setId("TestID");
		classe.setNome("1A");
		
		studente= new Studente();
		studente.setClasse(classe);
		studente.setNome("Jason");
		studente.setCognome("Dellaluce");
		
		attivita= new Attivita();
		attivita.setClasse(classe);
		attivita.setData(LocalDate.of(2019,5,21));
		attivita.setOraInizio(1);
		
		attivita2 = new Attivita();
		attivita2.setClasse(classe);
		attivita2.setOraInizio(2);
		attivita2.setData(LocalDate.of(2002,1,11));
		
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
		daoArchiviazione = factory.getDAOArchiviazione();
		daoAttivita = factory.getDAOAttivita();
		daoUtente = factory.getDAOUtente();
		logger = new MockLoggerOperazioni();
		test = new GestioneClasseController(logger, daoArchiviazione,
				daoAttivita, daoUtente,classe);
	}

	@Test
	void testGetClasse() throws OperazioneException {
		assertEquals(test.getClasse(),classe);
	}

	@Test
	void testInserisciAnnotazione() throws OperazioneException, PersistenzaException {
		test.inserisciAnnotazione(attivita, "Annotazione di test");
		assertEquals(attivita.getAnnotazione(),"Annotazione di test");
		assertTrue(daoAttivita.ottieniAttivitaPerClasse(classe).contains(attivita));
	}

	@Test
	void testInserisciPresenza() throws OperazioneException, PersistenzaException {
		daoUtente.registraUtente(studente);
		test.inserisciPresenza(presenza);
		assertTrue(daoArchiviazione.ottieniArchiviazioniPerClasse(classe).contains(presenza));
		assertTrue(daoArchiviazione.ottieniArchiviazioniPerStudente(studente).contains(presenza));
	}

	@Test
	void testRegistraVoto() throws OperazioneException, PersistenzaException {
		test.registraVoto(voto);
		assertTrue(daoArchiviazione.ottieniArchiviazioniPerClasse(classe).contains(voto));
		assertTrue(daoArchiviazione.ottieniArchiviazioniPerStudente(studente).contains(voto));
	}

	@Test
	void testGetListaStudenti() throws PersistenzaException, OperazioneException {
		daoUtente.registraUtente(studente);
		assertTrue(test.getListaStudenti().contains(studente));
	}

	@Test
	void testGetListaAttivita() throws PersistenzaException, OperazioneException {
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
	void testGetListaPresenze() throws OperazioneException {
		test.inserisciPresenza(presenza);
		test.inserisciPresenza(presenza2);
		assertTrue(test.getListaPresenze(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(presenza) &&
				!test.getListaPresenze(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(presenza2));
		assertTrue(!test.getListaPresenze(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 20) ).contains(presenza) &&
				test.getListaPresenze(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 20) ).contains(presenza2));		
		assertTrue(test.getListaPresenze(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 22) ).contains(presenza) &&
				test.getListaPresenze(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 22) ).contains(presenza2));	
	}

	@Test
	void testGetListaVoti() throws OperazioneException {
		//voto già registrato in test sopra
		test.registraVoto(voto2);
		assertTrue(test.getListaVoti(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(voto) &&
				!test.getListaVoti(LocalDate.of(2019, 1, 3 ), LocalDate.of(2019 , 5, 22) ).contains(voto2));
		assertTrue(!test.getListaVoti(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 20) ).contains(voto) &&
				test.getListaVoti(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 20) ).contains(voto2));		
		assertTrue(test.getListaVoti(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 22) ).contains(voto) &&
				test.getListaVoti(LocalDate.of(1997, 11, 30 ), LocalDate.of(2019 , 5, 22) ).contains(voto2));			
	}

}
