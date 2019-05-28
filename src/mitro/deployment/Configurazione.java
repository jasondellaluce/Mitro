package mitro.deployment;

import java.time.ZoneId;

public abstract class Configurazione {
	
	public static Configurazione instance;
	
	public static Configurazione getInstance() {
		if(instance == null)
			instance = new ConfigurazioneSemplice();
		return instance;
	}
	
	public abstract int getMajorApplicazione();
	public abstract int getMinorApplicazione();
	public abstract int getRevApplicazione();
	public abstract String getNomeApplicazione();
	public abstract String getDescrizioneApplicazione();
	public abstract ZoneId getZoneId();
	public abstract String getPercorsoEsecuzione();
	public abstract void setPercorsoEsecuzione(String percorso);
	
}
