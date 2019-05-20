package mitro.model;

import org.junit.jupiter.api.Test;

class ArchiviazioneTest {

	@Test
	public void testJavaBean() {
		JavaBeanTestHelper.testAccessor(Archiviazione.class, new Voto());
	}

}
