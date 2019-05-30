package mitro.persistenza.sql;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import mitro.deployment.Configurazione;
import mitro.exceptions.PersistenzaException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Materia;
import mitro.model.Professore;
import mitro.model.Studente;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOClasse;
import mitro.persistenza.DAOFactory;
import mitro.persistenza.DAOUtente;

class SQLDAOAttivitaTest {

	private static final String dbName = "testDaoAttivita.db";
	private static DAOClasse daoClasse;
	private static DAOUtente daoUtente;
	private static DAOAttivita daoAttivita;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		DAOFactory factory = new SQLDAOFactory(dbName);
		factory.cancellaDati();
		factory.inizializzaDati();
		daoClasse = factory.getDAOClasse();
		daoUtente = factory.getDAOUtente();
		daoAttivita = factory.getDAOAttivita();
	}

	@Test
	void testGenerale() throws Exception {
		/* Oggetti da rendere persistenti */
		Classe c1 = new Classe();
		c1.setNome("4A");
		c1.setAnnoScolastico("18/19");
		Classe c2 = new Classe();
		c2.setNome("4C");
		c2.setAnnoScolastico("18/19");
		daoClasse.registraClasse(c1);
		daoClasse.registraClasse(c2);
		
		Professore p1 = new Professore();
		p1.setNome("Federico");
		p1.setCognome("Baldini");
		Professore p2 = new Professore();
		p2.setNome("Amir");
		p2.setCognome("Al Sadi");
		daoUtente.registraUtente(p1);
		daoUtente.registraUtente(p2);
		
		Studente s1 = new Studente();
		s1.setNome("Jason");
		s1.setCognome("Dellaluce");
		s1.setClasse(c1);
		daoUtente.registraUtente(s1);
		
		Materia m1 = new Materia();
		m1.setNome("MAT");
		m1.setDescrizione("Matematica");
		
		Attivita a1 = new Attivita();
		a1.setProfessore(p1);
		a1.setData(LocalDate.now(Configurazione.getInstance().getZoneId()));
		a1.setMateria(m1);
		a1.setClasse(c1);
		a1.setOraInizio(10);
		a1.setAnnotazione("Lezione");
		Attivita a2 = new Attivita();
		a2.setProfessore(p2);
		a2.setData(LocalDate.now(Configurazione.getInstance().getZoneId()).plusDays(1));
		a2.setMateria(m1);
		a2.setClasse(c2);
		a2.setOraInizio(11);
		a2.setAnnotazione("Verifica");
		
		Attivita a3 = new Attivita();
		a3.setProfessore(p2);
		
		/* Inserimento normale */
		assertDoesNotThrow(() -> daoAttivita.registraAttivita(a1));
		
		/* Inserimento ripetuto */
		assertThrows(PersistenzaException.class, () -> daoAttivita.registraAttivita(a1));
		
		/* Inserimento illecito */
		assertThrows(PersistenzaException.class, () -> daoAttivita.registraAttivita(a3));
		
		/* Modifica illecita */
		assertThrows(PersistenzaException.class, () -> daoAttivita.modificaAttivita(a2));
		assertDoesNotThrow(() -> daoAttivita.registraAttivita(a2));
		
		/* Modifica lecita */
		a1.setAnnotazione("aaaaa");
		assertDoesNotThrow(() -> daoAttivita.modificaAttivita(a1));
		assertTrue(daoAttivita.ottieniAttivita().contains(a1));
		
		/* Eliminazione illecita */
		assertThrows(PersistenzaException.class, () -> daoAttivita.eliminaAttivita(a3));
		
		/* Eliminazione lecita */
		assertDoesNotThrow(() -> daoAttivita.eliminaAttivita(a2));
		assertThrows(PersistenzaException.class, () -> daoAttivita.eliminaAttivita(a2));
		assertDoesNotThrow(() -> daoAttivita.registraAttivita(a2));
		
		/* Correttezza ricerche */
		List<Attivita> lista;
		
		lista = daoAttivita.ottieniAttivita();
		assertEquals(lista.size(), 2);
		assertTrue(lista.contains(a1));
		assertTrue(lista.contains(a2));
		
		lista = daoAttivita.ottieniAttivitaPerClasse(c1);
		assertEquals(lista.size(), 1);
		assertTrue(lista.contains(a1));
		assertFalse(lista.contains(a2));
		
		lista = daoAttivita.ottieniAttivitaPerClasse(c2);
		assertEquals(lista.size(), 1);
		assertTrue(lista.contains(a2));
		assertFalse(lista.contains(a1));
		
		lista = daoAttivita.ottieniAttivitaPerProfessore(p1);
		assertEquals(lista.size(), 1);
		assertTrue(lista.contains(a1));
		assertFalse(lista.contains(a2));
		
		lista = daoAttivita.ottieniAttivitaPerProfessore(p2);
		assertEquals(lista.size(), 1);
		assertTrue(lista.contains(a2));
		assertFalse(lista.contains(a1));
		
		lista = daoAttivita.ottieniAttivitaPerStudente(s1);
		assertEquals(lista.size(), 1);
		assertTrue(lista.contains(a1));
		assertFalse(lista.contains(a2));
	}

	@AfterAll
	static void afterClass() throws Exception {
		new File(dbName).delete();
	}

}
