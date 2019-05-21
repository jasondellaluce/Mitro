package mitro.controller.log.analisi;

import java.util.ArrayList;
import java.util.List;

import mitro.controller.log.AnalisiOperazioni;
import mitro.model.VoceOperazioneLog;

public class AnalisiOperazioniSemplice implements AnalisiOperazioni {

	@Override
	public List<String> analizza(List<VoceOperazioneLog> voci) {
		return new ArrayList<>();
	}

}
