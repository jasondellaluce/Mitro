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
<<<<<<< HEAD
		// TODO: Implement test
=======
		
		Professore professoreStoria = new Professore("Federico", "Baldini",
				"FB@mail.it", "Via A 1", "333000111222", LocalDate.now());
		Professore professoreMatematica = new Professore("Amir", "Al Sadi",
				"AS@mail.it", "Via B 2", "333111222333", LocalDate.now());
		
		Materia materiaStoria = new Materia("001", "storia",
				"storia informatica");
		Materia materiaMatematica = new Materia("002", "matematica",
				"matematica chimica");
		
		Classe classeA = new Classe("001", "classe A", "2019",
				"3� informatica");
		Classe classeB = new Classe("002", "classe B", "2019",
				"4� chimica");
		
		Attivita lezioneStoria = new Attivita(professoreStoria,
				LocalDate.of(2019, 1, 2), 9, materiaStoria, "lezione di storia", classeA);
		Attivita lezioneMatematica = new Attivita(professoreMatematica,
				LocalDate.of(2019, 2, 3), 10, materiaMatematica, "lezione di matematica", classeB);
		
		try {
			daoAttivita.registraAttivita(lezioneStoria);
			daoAttivita.registraAttivita(lezioneMatematica);
			List<Attivita> listaAttivita = test.getListaAttivita(LocalDate.of(2018, 1, 1), LocalDate.of(2020, 1, 1));
			//la lista � vuota
			assertEquals(listaAttivita.get(0), lezioneStoria);
			assertEquals(listaAttivita.get(1), lezioneMatematica);
		}
		catch (PersistenzaException e) {
			throw new OperazioneException(e);
		}
>>>>>>> branch 'master' of https://gitlab.com/jasondellaluce/mitro.git
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
