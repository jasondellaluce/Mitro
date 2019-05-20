package mitro.model;

import org.junit.jupiter.api.Test;

class IscrittoTest {

	@Test
	public void testJavaBean() {
		JavaBeanTestHelper.testAccessor(Iscritto.class, new Studente());
	}

}
