package mitro.view.log;

import java.io.Writer;

public class WriterLoggerMessaggi implements LoggerMessaggi {

	private Writer writer;

	public WriterLoggerMessaggi(Writer writer) {
		this.writer = writer;
	}
	
	@Override
	public void scrivi(String messaggio) {
		
	}

}
