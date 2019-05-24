package mitro.persistenza.sql;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteDataSource;

import mitro.controller.deployment.Configurazione;
import mitro.exceptions.PersistenzaException;
import mitro.model.Archiviazione;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Materia;
import mitro.model.Presenza;
import mitro.model.Professore;
import mitro.model.Studente;
import mitro.model.Voto;
import mitro.persistenza.cifrature.MockCifratura;

class SQLDAOArchiviazioneTest {

	private static final String dbName = "testDaoArchiviazioni.db";
	private static SQLDAOClasse daoClasse;
	private static SQLDAOUtente daoUtente;
	private static SQLDAOAttivita daoAttivita;
	private static SQLDAOArchiviazione daoArch;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		SQLiteDataSource ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:" + dbName); 
		new SQLGestoreTabelle(ds, new MockCifratura()).eliminaTabelle();
		new SQLGestoreTabelle(ds, new MockCifratura()).creaTabelle();
		daoClasse = new SQLDAOClasse(ds, new MockCifratura());
		daoUtente = new SQLDAOUtente(ds, new MockCifratura());
		daoAttivita = new SQLDAOAttivita(ds, new MockCifratura());
		daoArch = new SQLDAOArchiviazione(ds, new MockCifratura());
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
		Studente s2 = new Studente();
		s2.setNome("Marco");
		s2.setCognome("Balilla");
		s2.setClasse(c1);
		daoUtente.registraUtente(s1);
		daoUtente.registraUtente(s2);
		
		Materia m1 = new Materia();
		m1.setNome("MAT");
		m1.setDescrizione("Matematica");
		
		Attivita a1 = new Attivita();
		a1.setProfessore(p1);
		a1.setData(LocalDate.now(Configurazione.ZONE_ID));
		a1.setMateria(m1);
		a1.setClasse(c1);
		a1.setOraInizio(10);
		a1.setAnnotazione("Lezione");
		Attivita a2 = new Attivita();
		a2.setProfessore(p2);
		a2.setData(LocalDate.now(Configurazione.ZONE_ID).plusDays(1));
		a2.setMateria(m1);
		a2.setClasse(c1);
		a2.setOraInizio(11);
		a2.setAnnotazione("Verifica");
		daoAttivita.registraAttivita(a1);
		daoAttivita.registraAttivita(a2);
		
		Voto ar1 = new Voto();
		ar1.setStudente(s1);
		ar1.setAttivita(a1);
		ar1.setValore(7.5);
		Presenza ar2 = new Presenza();
		ar2.setStudente(s1);
		ar2.setAttivita(a2);
		ar2.setValore(true);
		Presenza ar3 = new Presenza();
		ar3.setStudente(s1);
		
		/* Inserimento normale */
		assertDoesNotThrow(() -> daoArch.registraArchiviazione(ar1));
		
		/* Inserimento ripetuto */
		assertThrows(PersistenzaException.class, () ->  daoArch.registraArchiviazione(ar1));
		
		/* Inserimento illecito */
		assertThrows(PersistenzaException.class, () ->  daoArch.registraArchiviazione(ar3));
		
		/* Modifica illecita */
		assertThrows(PersistenzaException.class, () ->  daoArch.modificaArchiviazione(ar2));
		assertDoesNotThrow(() ->  daoArch.registraArchiviazione(ar2));
		
		/* Modifica lecita */
		ar1.setValore(6.0);
		assertDoesNotThrow(() -> daoArch.modificaArchiviazione(ar1));
		assertTrue(daoArch.ottieniArchiviazioni().contains(ar1));
		
		/* Eliminazione illecita */
		assertThrows(PersistenzaException.class, () -> daoArch.eliminaArchiviazione(ar3));
		
		/* Eliminazione lecita */
		assertDoesNotThrow(() -> daoArch.eliminaArchiviazione(ar2));
		assertThrows(PersistenzaException.class, () -> daoArch.eliminaArchiviazione(ar2));
		assertDoesNotThrow(() ->  daoArch.registraArchiviazione(ar2));
		
		/* Correttezza ricerche */
		List<Archiviazione> lista;
		
		lista = daoArch.ottieniArchiviazioni();
		assertEquals(lista.size(), 2);
		assertTrue(lista.contains(ar1));
		assertTrue(lista.contains(ar2));
		
		lista = daoArch.ottieniArchiviazioniPerClasse(c1);
		assertEquals(lista.size(), 2);
		assertTrue(lista.contains(ar1));
		assertTrue(lista.contains(ar2));
		
		lista = daoArch.ottieniArchiviazioniPerAttivita(a1);
		assertEquals(lista.size(), 1);
		assertTrue(lista.contains(ar1));
		assertFalse(lista.contains(ar2));
		
		lista = daoArch.ottieniArchiviazioniPerProfessore(p2);
		assertEquals(lista.size(), 1);
		assertTrue(lista.contains(ar2));
		assertFalse(lista.contains(ar1));
		
		lista = daoArch.ottieniArchiviazioniPerStudente(s1);
		assertEquals(lista.size(), 2);
		assertTrue(lista.contains(ar1));
		assertTrue(lista.contains(ar2));
		
		lista = daoArch.ottieniArchiviazioniPerStudente(s2);
		assertEquals(lista.size(), 0);
	}

	@AfterAll
	static void afterClass() throws Exception {
		new File(dbName).delete();
	}

}
