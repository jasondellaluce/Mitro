package mitro.model;

import org.junit.jupiter.api.Test;

class UtenteTest{

	@Test
	public void testJavaBean() {
		JavaBeanTestHelper.testAccessor(Utente.class, new Amministratore(), "ruolo");
		JavaBeanTestHelper.testAccessorAttributo(Utente.class, new Amministratore(), "ruolo", Ruolo.AMMINISTRATORE);
	}

}
