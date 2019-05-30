package mitro.controller.log;

import java.io.IOException;
import java.io.Writer;

public class WriterLoggerMessaggi implements LoggerMessaggi {

	private Writer writer;

	public WriterLoggerMessaggi(Writer writer) {
		this.writer = writer;
	}
	
	@Override
	public void scrivi(String messaggio) {
		try {
			writer.write(messaggio + "\n");
			writer.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
