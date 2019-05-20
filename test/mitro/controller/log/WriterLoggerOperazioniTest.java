package mitro.controller.log;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringWriter;

import org.junit.jupiter.api.Test;

class WriterLoggerOperazioniTest {

	@Test
	void testScrivi() {
		String test = "Esempio voce Log";
		StringWriter writer = new StringWriter();
		WriterLoggerOperazioni logger = new WriterLoggerOperazioni(writer);
		
		logger.scrivi(test);
		assertEquals(writer.toString(), test + "\n");
	}

}
