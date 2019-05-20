package mitro.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudenteTest {

	@Test
	public void testJavaBean() {
		JavaBeanTestHelper.testAccessor(new Studente());
	}
	
	@Test
	protected void testVincoloRuolo() {
		Studente oggetto = new Studente();
		for(Ruolo ruolo : Ruolo.values()) {
			if(ruolo.equals(Ruolo.STUDENTE)) {
				oggetto.setRuolo(ruolo);
				assertEquals(oggetto.getRuolo(), Ruolo.STUDENTE);
			}
			else
				assertThrows(IllegalArgumentException.class, () -> oggetto.setRuolo(ruolo));
		}
	}

}
