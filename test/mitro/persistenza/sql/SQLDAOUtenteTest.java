package mitro.persistenza.sql;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteDataSource;

import mitro.exceptions.ElementoGiaPersistenteException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Amministratore;
import mitro.model.Classe;
import mitro.model.Iscritto;
import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.model.Studente;
import mitro.model.Utente;
import mitro.persistenza.mock.TestoInChiaro;

class SQLDAOUtenteTest {

	private static final String dbName = "testDaoUtente.db";
	private static SQLDAOUtente daoUtente;
	private static SQLDAOClasse daoClasse;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		SQLiteDataSource ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:" + dbName); 
		new SQLGestoreTabelle(ds).eliminaTabelle();
		new SQLGestoreTabelle(ds).creaTabelle();
		
		daoUtente = new SQLDAOUtente(ds, new TestoInChiaro());	
		daoClasse = new SQLDAOClasse(ds, new TestoInChiaro());	
	}

	@Test
	void testGenerale() throws Exception {
		/* Oggetti da rendere persistenti */
		Classe e1 = new Classe();
		e1.setNome("4A");
		e1.setAnnoScolastico("18/19");
		Amministratore u1 = new Amministratore();
		Studente u2 = new Studente();
		Professore u3 = new Professore();
		Utente u4 = new Utente();
		Studente u5 = new Studente();
		u2.setNome("Jason");
		u2.setCognome("Dellaluce");
		u2.setClasse(e1);
		u3.setNome("Amir");
		u3.setCognome("Al Sadi");
		u4.setRuolo(Ruolo.GESTORESICUREZZA);
		
		/* Persistenza elementi ausiliari */
		daoClasse.registraClasse(e1);
		
		/* Inserimento lecito */
		assertDoesNotThrow(() -> daoUtente.registraUtente(u1));
		assertDoesNotThrow(() -> daoUtente.registraUtente(u4));
		
		/* Inserimento illecito */
		assertThrows(PersistenzaException.class, () -> daoUtente.registraUtente(u5));
		
		/* Modifica illecita */
		assertThrows(PersistenzaException.class, () -> daoUtente.modificaUtente(u2));
		assertDoesNotThrow(() -> daoUtente.registraUtente(u2));
		
		/* Modifica lecita */
		u2.setCognome("Baldini");
		assertDoesNotThrow(() -> daoUtente.modificaUtente(u2));
		assertEquals(u2, daoUtente.ottieniUtentePerId(u2.getId()));
		
		/* Eliminazione illecita */
		assertThrows(PersistenzaException.class, () -> daoUtente.eliminaUtente(u3));
		assertDoesNotThrow(() -> daoUtente.registraUtente(u3));
		
		/* Eliminazione lecita */
		assertDoesNotThrow(() -> daoUtente.eliminaUtente(u2));
		assertThrows(PersistenzaException.class, () -> daoUtente.eliminaUtente(u2));
		assertDoesNotThrow(() -> daoUtente.registraUtente(u2));
		
		/* Correttezza ricerche */
		List<Utente> lista;
		
		lista = daoUtente.ottieniUtenti();
		assertEquals(lista.size(), 4);
		assertTrue(lista.contains(u1));
		assertTrue(lista.contains(u2));
		assertTrue(lista.contains(u3));
		assertTrue(lista.contains(u4));
		
		lista = daoUtente.ottieniUtentiPerRuolo(Ruolo.AMMINISTRATORE);
		assertEquals(lista.size(), 1);
		assertTrue(lista.contains(u1));
		
		lista = daoUtente.ottieniUtentiPerRuolo(Ruolo.STUDENTE);
		assertEquals(lista.size(), 1);
		assertTrue(lista.contains(u2));
		
		List<Iscritto> lista2 = daoUtente.ottieniIscrittiPerNomeOCognome("Jason");
		assertEquals(lista2.size(), 1);
		assertTrue(lista2.contains(u2));
		
		lista2 = daoUtente.ottieniIscrittiPerNomeOCognome("Al Sadi");
		assertEquals(lista2.size(), 1);
		assertTrue(lista2.contains(u3));
		
		lista2 = daoUtente.ottieniIscrittiPerNomeOCognome("Patella");
		assertEquals(lista2.size(), 0);
		
		/* Correttezza delle credenziali */
		daoUtente.inserisciCredenziali(u1, "user", "pass");
		assertNull(daoUtente.ottieniUtentePerCredenziali("test", "password"));
		assertEquals(daoUtente.ottieniUtentePerCredenziali("user", "pass"), u1);
		assertThrows(ElementoGiaPersistenteException.class,
				() -> daoUtente.inserisciCredenziali(u2, "user", "pass2"));
	}

	@AfterAll
	static void afterClass() throws Exception {
		new File(dbName).delete();
	}

}
