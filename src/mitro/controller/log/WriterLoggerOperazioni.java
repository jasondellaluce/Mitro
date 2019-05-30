package mitro.controller.log;

import java.io.IOException;
import java.io.Writer;

public class WriterLoggerOperazioni implements LoggerOperazioni {

	private Writer writer;

	public WriterLoggerOperazioni(Writer writer) {
		this.writer = writer;
	}
	
	@Override
	public void scrivi(String operazione) {
		try {
			writer.write(operazione + "\n");
			writer.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
