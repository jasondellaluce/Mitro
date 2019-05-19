package mitro.controller.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;

public class FileWriterGiornaliero extends Writer {

	private Writer writer;
	private LocalDate dataAttuale;
	private String prefisso;
	private String suffisso;
	private boolean chiuso;
	private boolean appendi;
	
	public FileWriterGiornaliero(String prefisso, String suffisso, boolean appendi) {
		this.chiuso = false;
		this.appendi = appendi;
		this.prefisso = prefisso;
		this.suffisso = suffisso;
		this.dataAttuale = LocalDate.now();
	}
	
	public String getNomeWriterAttuale() {
		return prefisso + dataAttuale + suffisso;
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		if(chiuso)
			throw new IOException("Writer chiuso");
		if(writer == null || LocalDate.now().isAfter(dataAttuale))
			apriNuovoWriter();
		writer.write(cbuf, off, len);
	}

	@Override
	public void flush() throws IOException {
		if(chiuso)
			throw new IOException("Writer chiuso");
		if(writer == null)
			apriNuovoWriter();
		else
			writer.flush();
	}

	@Override
	public void close() throws IOException {
		if(writer != null && !chiuso)
			writer.close();
		chiuso = true;
	}

	private void apriNuovoWriter() throws IOException {
		if(writer != null) {
			writer.flush();
			writer.close();
		}
		dataAttuale = LocalDate.now();
		writer = new BufferedWriter(new FileWriter(getNomeWriterAttuale(), appendi));
	}
	
}
