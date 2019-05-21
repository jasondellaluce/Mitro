package mitro.controller.log.analisi;

import java.util.ArrayList;
import java.util.List;

import mitro.controller.log.AnalisiMessaggi;
import mitro.model.VoceMessaggioLog;

public class AnalisiMessaggiSemplice implements AnalisiMessaggi {

	@Override
	public List<String> analizza(List<VoceMessaggioLog> voci) {
		return new ArrayList<>();
	}

}
