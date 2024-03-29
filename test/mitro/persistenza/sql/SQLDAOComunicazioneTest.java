package mitro.persistenza.sql;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import mitro.deployment.Configurazione;
import mitro.exceptions.PersistenzaException;
import mitro.model.Comunicazione;
import mitro.model.Studente;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.DAOFactory;
import mitro.persistenza.DAOUtente;

class SQLDAOComunicazioneTest {

	private static final String dbName = "testDaoComunicazione.db";
	private static DAOComunicazione daoComunicazione;
	private static DAOUtente daoUtente;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		DAOFactory factory = new SQLDAOFactory(dbName);
		factory.cancellaDati();
		factory.inizializzaDati();
		daoComunicazione = factory.getDAOComunicazione();
		daoUtente = factory.getDAOUtente();
	}

	@Test
	void testGenerale() throws Exception {
		/* Oggetti da rendere persistenti */
		Studente u1 = new Studente();
		u1.setNome("MArco");
		u1.setCognome("Dellaluce");
		
		Comunicazione c1 = new Comunicazione();
		Comunicazione c2 = new Comunicazione();
		Comunicazione c3 = new Comunicazione();
		c1.setDataOra(LocalDateTime.now(Configurazione.getInstance().getZoneId()));
		c2.setDataOra(LocalDateTime.now(Configurazione.getInstance().getZoneId()).minusDays(1));
		c3.setDataOra(LocalDateTime.now(Configurazione.getInstance().getZoneId()));
		c1.setDestinatario(u1);
		c3.setDestinatario(u1);
		c1.setOggetto("Comm1");
		c2.setOggetto("Comm2");
		c3.setOggetto("Comm3");
		c1.setContenuto("cont1");
		c2.setContenuto("cont2");
		c3.setContenuto("cont3");
		
		daoUtente.registraUtente(u1);
		
		/* Inserimento normale */
		assertDoesNotThrow(() -> daoComunicazione.registraComunicazione(c1));
		
		/* Inserimento ripetuto */
		assertThrows(PersistenzaException.class, () -> daoComunicazione.registraComunicazione(c1));
		
		/* Inserimento illecito */
		assertThrows(PersistenzaException.class, () -> daoComunicazione.registraComunicazione(c2));
		
		/* Modifica illecita */
		assertThrows(PersistenzaException.class, () -> daoComunicazione.modificaComunicazione(c2));
		c2.setDestinatario(u1);
		assertDoesNotThrow(() -> daoComunicazione.registraComunicazione(c2));
		
		/* Modifica lecita */
		c1.setContenuto("Nuovo contenuto");
		assertDoesNotThrow(() -> daoComunicazione.modificaComunicazione(c1));
		
		/* Eliminazione illecita */
		c3.setDataOra(LocalDateTime.now(Configurazione.getInstance().getZoneId()).plusDays(1));
		assertThrows(PersistenzaException.class, () -> daoComunicazione.eliminaComunicazione(c3));
		
		/* Eliminazione lecita */
		assertDoesNotThrow(() -> daoComunicazione.eliminaComunicazione(c2));
		assertThrows(PersistenzaException.class, () ->  daoComunicazione.eliminaComunicazione(c2));
		assertDoesNotThrow(() -> daoComunicazione.registraComunicazione(c2));
		
		/* Correttezza ricerche */
		List<Comunicazione> lista;
		
		lista = daoComunicazione.ottieniComunicazioni();
		assertEquals(lista.size(), 2);
		assertTrue(lista.contains(c1));
		assertTrue(lista.contains(c2));
		
		lista = daoComunicazione.ottieniComunicazioniPerData(
				LocalDate.now(Configurazione.getInstance().getZoneId()), 
				LocalDate.now(Configurazione.getInstance().getZoneId()));
		assertEquals(lista.size(), 1);
		assertTrue(lista.contains(c1));
		assertFalse(lista.contains(c2));
		
		lista = daoComunicazione.ottieniComunicazioniPerData(
				LocalDate.now(Configurazione.getInstance().getZoneId()).minusDays(2), 
				LocalDate.now(Configurazione.getInstance().getZoneId()).minusDays(1));
		assertEquals(lista.size(), 1);
		assertTrue(lista.contains(c2));
		assertFalse(lista.contains(c1));
		
		lista = daoComunicazione.ottieniComunicazioniPerDestinatario(u1);
		assertEquals(lista.size(), 2);
		assertTrue(lista.contains(c1));
		assertTrue(lista.contains(c2));	
	}

	@AfterAll
	static void afterClass() throws Exception {
		new File(dbName).delete();
	}

}
