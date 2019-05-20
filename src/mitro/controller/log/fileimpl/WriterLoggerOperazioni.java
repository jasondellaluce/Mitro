package mitro.controller.log.fileimpl;

import java.io.IOException;
import java.io.Writer;

import mitro.controller.log.LoggerOperazioni;

public class WriterLoggerOperazioni implements LoggerOperazioni {

	private Writer writer;

	public WriterLoggerOperazioni(Writer writer) {
		this.writer = writer;
	}
	
	@Override
	public void scrivi(String operazione) {
		try {
			writer.write(operazione + "\n");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
