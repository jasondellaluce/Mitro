package mitro.persistenza.sql;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteDataSource;

import mitro.exceptions.PersistenzaException;
import mitro.model.Classe;
import mitro.persistenza.cifrature.MockCifratura;

class SQLDAOClasseTest {

	private static final String dbName = "testDaoClasse.db";
	private static SQLDAOClasse dao;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		SQLiteDataSource ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:" + dbName); 
		new SQLGestoreTabelle(ds).eliminaTabelle();
		new SQLGestoreTabelle(ds).creaTabelle();
		dao = new SQLDAOClasse(ds, new MockCifratura());	
	}

	@Test
	void testGenerale() throws Exception {
		/* Oggetti da rendere persistenti */
		Classe e1 = new Classe();
		e1.setNome("4A");
		e1.setAnnoScolastico("18/19");
		Classe e2 = new Classe();
		e2.setNome("4B");
		e2.setAnnoScolastico("18/19");
		Classe e3 = new Classe();
		
		/* Inserimento normale */
		assertDoesNotThrow(() -> dao.registraClasse(e1));
		
		/* Inserimento ripetuto */
		assertThrows(PersistenzaException.class, () -> dao.registraClasse(e1));
		
		/* Inserimento illecito */
		assertThrows(PersistenzaException.class, () -> dao.registraClasse(e3));
		
		/* Modifica illecita */
		assertThrows(PersistenzaException.class, () -> dao.modificaClasse(e2));
		assertDoesNotThrow(() -> dao.registraClasse(e2));
		
		/* Modifica lecita */
		e1.setDescrizione("aaaaa");
		assertDoesNotThrow(() -> dao.modificaClasse(e1));
		assertEquals(e1, dao.ottieniClassePerId(e1.getId()));
		
		/* Eliminazione illecita */
		assertThrows(PersistenzaException.class, () -> dao.eliminaClasse(e3));
		
		/* Eliminazione lecita */
		assertDoesNotThrow(() -> dao.eliminaClasse(e2));
		assertThrows(PersistenzaException.class, () -> dao.eliminaClasse(e2));
		assertDoesNotThrow(() -> dao.registraClasse(e2));
		
		/* Correttezza ricerche */
		List<Classe> lista;
		
		lista = dao.ottieniClassi();
		assertEquals(lista.size(), 2);
		assertTrue(lista.contains(e1));
		assertTrue(lista.contains(e2));
		
		lista = dao.ottieniClassiPerAnnoScolastico("18/19");
		assertEquals(lista.size(), 2);
		assertTrue(lista.contains(e1));
		assertTrue(lista.contains(e2));
		
		lista = dao.ottieniClassiPerAnnoScolastico("aaaa");
		assertEquals(lista.size(), 0);
		
		lista = dao.ottieniClassiPerNome("4A");
		assertEquals(lista.size(), 1);
		assertTrue(lista.contains(e1));
		assertFalse(lista.contains(e2));
		
		lista = dao.ottieniClassiPerNome("5B");
		assertEquals(lista.size(), 0);
	}

	@AfterAll
	static void afterClass() throws Exception {
		new File(dbName).delete();
	}
	
}
