package mitro.controller.login;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mitro.exceptions.OperazioneException;
import mitro.model.Ruolo;
import mitro.model.Utente;

class MapPermessoLoginTest {

	@Test
	void testConsistenza() throws OperazioneException {
		/* Creazione oggetti di test */
		MapPermessoLogin test = new MapPermessoLogin();
		Login primoLogin = new MockLogin();
		Login secondoLogin = new MockLogin();
		Utente primoUtente = new Utente();
		Utente secondoUtente = new Utente();
		primoUtente.setId("primaID");
		primoUtente.setRuolo(Ruolo.AMMINISTRATORE);
		secondoUtente.setId("secondoID");
		secondoUtente.setRuolo(Ruolo.AMMINISTRATORE);
		
		/* Controlla rilascio prematuro */
		assertFalse(test.rilasciaPermesso(primoUtente, primoLogin));
		assertFalse(test.rilasciaPermesso(secondoUtente, secondoLogin));
		
		/* Controlla ottenimento ripetuto */
		assertTrue(test.ottieniPermesso(primoUtente, primoLogin));
		assertTrue(test.ottieniPermesso(secondoUtente, secondoLogin));
		assertFalse(test.ottieniPermesso(primoUtente, primoLogin));
		assertFalse(test.ottieniPermesso(secondoUtente, secondoLogin));
		
		/* Controlla rilascio illecito */
		assertFalse(test.rilasciaPermesso(primoUtente, secondoLogin));
		assertFalse(test.rilasciaPermesso(secondoUtente, primoLogin));
		
		/* Controlla rilascio ripetuto */
		assertTrue(test.rilasciaPermesso(primoUtente, primoLogin));
		assertTrue(test.rilasciaPermesso(secondoUtente, secondoLogin));
		assertFalse(test.rilasciaPermesso(primoUtente, primoLogin));
		assertFalse(test.rilasciaPermesso(secondoUtente, secondoLogin));
	}

}
