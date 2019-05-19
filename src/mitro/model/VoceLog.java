package mitro.model;

import java.time.LocalDateTime;

public abstract class VoceLog {

	private LocalDateTime dataOra;
	
	public VoceLog(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}
		
}
