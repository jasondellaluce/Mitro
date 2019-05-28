package mitro.deployment;

import java.time.ZoneId;

public class ConfigurazioneSemplice extends Configurazione {

	private String percorso = "";
	
	@Override
	public int getMajorApplicazione() {
		return 1;
	}

	@Override
	public int getMinorApplicazione() {
		return 0;
	}

	@Override
	public int getRevApplicazione() {
		return 0;
	}

	@Override
	public String getNomeApplicazione() {
		return "Mitro";
	}

	@Override
	public String getDescrizioneApplicazione() {
		return getNomeApplicazione() + " v." 
				+ getMajorApplicazione() + "."
				+ getMinorApplicazione() + "."
				+ getRevApplicazione();
	}

	@Override
	public ZoneId getZoneId() {
		return ZoneId.of("Europe/Rome");
	}

	@Override
	public String getPercorsoEsecuzione() {
		return percorso;
	}

	@Override
	public void setPercorsoEsecuzione(String percorso) {
		this.percorso = percorso;
	}

}
