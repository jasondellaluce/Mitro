package mitro.controller.log;

import java.util.List;

import mitro.model.VoceOperazioneLog;

public interface AnalisiOperazioni {

	public List<String> analizza(List<VoceOperazioneLog> voci);
	
}
