package mitro.model;

import java.time.LocalDateTime;

public class VoceMessaggioLog extends VoceLog {

	private String messaggio;

	public VoceMessaggioLog(LocalDateTime dataOra, String messaggio) {
		super(dataOra);
		this.messaggio = messaggio;
	}

	public String getMessaggio() {
		return messaggio;
	}

	@Override
	public String getContenuto() {
		return getMessaggio();
	}

}
