package mitro.model;

import java.time.LocalDateTime;

public class VoceOperazioneLog extends VoceLog {

	private String operazione;

	public VoceOperazioneLog(LocalDateTime dataOra, String operazione) {
		super(dataOra);
		this.operazione = operazione;
	}

	public String getOperazione() {
		return operazione;
	}

	@Override
	public String getContenuto() {
		return getOperazione();
	}
	
}
