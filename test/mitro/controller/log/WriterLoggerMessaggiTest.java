package mitro.controller.log;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringWriter;

import org.junit.jupiter.api.Test;

class WriterLoggerMessaggiTest {

	@Test
	void testScrivi() {
		String test = "Esempio voce Log";
		StringWriter writer = new StringWriter();
		WriterLoggerMessaggi logger = new WriterLoggerMessaggi(writer);
		
		logger.scrivi(test);
		assertEquals(writer.toString(), test + "\n");
	}

}
