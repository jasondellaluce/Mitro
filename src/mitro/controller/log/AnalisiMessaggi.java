package mitro.controller.log;

import java.util.List;

import mitro.model.VoceMessaggioLog;

public interface AnalisiMessaggi {

	public List<String> analizza(List<VoceMessaggioLog> voci);
	
}
