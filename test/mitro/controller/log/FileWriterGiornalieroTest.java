package mitro.controller.log;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import mitro.controller.log.FileWriterGiornaliero;

class FileWriterGiornalieroTest {

	@Test
	void testDataGenerale() throws IOException {
		String prefisso = "log/file-";
		String suffisso = "log/.log";
		FileWriterGiornaliero writer = new FileWriterGiornaliero(prefisso, suffisso, true);
		assertEquals(writer.getNomeWriterAttuale(), prefisso + LocalDate.now() + suffisso);
		
		writer.close();
		assertThrows(IOException.class, () -> writer.write(" "));
		assertThrows(IOException.class, () -> writer.flush());
		writer.close();
	}

}
