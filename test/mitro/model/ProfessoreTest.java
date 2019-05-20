package mitro.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProfessoreTest {
	
	@Test
	protected void testVincoloRuolo() {
		Professore oggetto = new Professore();
		for(Ruolo ruolo : Ruolo.values()) {
			if(ruolo.equals(Ruolo.PROFESSORE)) {
				oggetto.setRuolo(ruolo);
				assertEquals(oggetto.getRuolo(), Ruolo.PROFESSORE);
			}
			else
				assertThrows(IllegalArgumentException.class, () -> oggetto.setRuolo(ruolo));
		}
	}

}
