package mitro.controller.login;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mitro.controller.log.MockLoggerOperazioni;
import mitro.exceptions.OperazioneException;
import mitro.exceptions.UtenteGiaAutenticatoException;
import mitro.exceptions.UtenteNonRegistratoException;
import mitro.model.Amministratore;
import mitro.model.Utente;
import mitro.persistenza.DAOUtente;
import mitro.persistenza.mock.MockDAOFactory;

class LoginControllerTest {
	
	private static LoginController loginController;
	private static MockPermessoLogin permessoLogin;
	private static DAOUtente daoUtente;
	private static String testUsername = "username";
	private static String testPassword = "password";
	private static Utente testUtente;
	
	@BeforeAll
	static void beforeClass() throws Exception {
		/* Crea finta persistenza */
		daoUtente = new MockDAOFactory().getDAOUtente();
		testUtente = new Amministratore();
		testUtente.setId("testId");
		daoUtente.registraUtente(testUtente);
		daoUtente.inserisciCredenziali(testUtente, testUsername, testPassword);
		
		/* Configura classe da testare */
		permessoLogin = new MockPermessoLogin();
		loginController = new LoginController(new MockLoggerOperazioni(), permessoLogin, daoUtente);
	}
	
	@Test
	void testAutentica() throws OperazioneException {
		/* Controllo argomenti */
		assertThrows(IllegalArgumentException.class, () -> loginController.autentica(null, null));
		assertThrows(IllegalArgumentException.class, () -> loginController.autentica(testUsername, null));
		assertThrows(IllegalArgumentException.class, () -> loginController.autentica(null, testPassword));
		assertThrows(IllegalArgumentException.class, () -> loginController.autentica("", ""));
		assertThrows(IllegalArgumentException.class, () -> loginController.autentica(testUsername, ""));
		assertThrows(IllegalArgumentException.class, () -> loginController.autentica("", testPassword));
		assertThrows(IllegalArgumentException.class, () -> loginController.autentica(null, null));
		
		/* Controllo credenziali sbagliate */
		assertThrows(UtenteNonRegistratoException.class, 
				() -> loginController.autentica(testUsername + "wrong", testPassword));
		permessoLogin.setSempre(false);
		
		/* Controllo permesso negato */
		assertThrows(UtenteGiaAutenticatoException.class, 
				() -> loginController.autentica(testUsername, testPassword));
		permessoLogin.setSempre(true);
		
		/* Cotrollo autenticazione corretta */
		assertTrue(loginController.autentica(testUsername, testPassword));
		assertFalse(loginController.autentica(testUsername, testPassword));
		assertTrue(loginController.getUtenteAutenticato().isPresent());
		assertEquals(loginController.getUtenteAutenticato().get(), testUtente);
		assertTrue(loginController.disconnetti());
		assertFalse(loginController.getUtenteAutenticato().isPresent());
	}

	@Test
	void testDisconnetti() throws OperazioneException {
		permessoLogin.setSempre(true);
		assertFalse(loginController.disconnetti());
		assertTrue(loginController.autentica(testUsername, testPassword));
		permessoLogin.setSempre(false);
		assertFalse(loginController.disconnetti());
		permessoLogin.setSempre(true);
		assertTrue(loginController.disconnetti());
		assertFalse(loginController.getUtenteAutenticato().isPresent());
	}
	
}
