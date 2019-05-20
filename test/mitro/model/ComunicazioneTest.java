package mitro.model;

import org.junit.jupiter.api.Test;

class ComunicazioneTest {

	@Test
	public void testJavaBean() {
		JavaBeanTestHelper.testAccessor(new Comunicazione(), "destinatario");
		JavaBeanTestHelper.testAccessorAttributo(new Comunicazione(), "destinatario", new Studente());
	}

}
