package mitro.view.log;

import java.io.Writer;

public class WriterLoggerOperazioni implements LoggerOperazioni {

	private Writer writer;

	public WriterLoggerOperazioni(Writer writer) {
		this.writer = writer;
	}
	
	@Override
	public void scrivi(String operazione) {
		
	}

}
