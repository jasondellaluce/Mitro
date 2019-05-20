package mitro.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AmministratoreTest {
	
	@Test
	public void testJavaBean() {
		JavaBeanTestHelper.testAccessor(new Amministratore());
	}
	
	@Test
	public void testVincoloRuolo() {
		Amministratore oggetto = new Amministratore();
		for(Ruolo ruolo : Ruolo.values()) {
			if(ruolo.equals(Ruolo.AMMINISTRATORE)) {
				oggetto.setRuolo(ruolo);
				assertEquals(oggetto.getRuolo(), Ruolo.AMMINISTRATORE);
			}
			else
				assertThrows(IllegalArgumentException.class, () -> oggetto.setRuolo(ruolo));
		}
	}

}
