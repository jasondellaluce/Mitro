package mitro.controller.log.analisi;

import java.util.ArrayList;
import java.util.List;

import mitro.controller.log.AnalisiOperazioni;
import mitro.model.VoceOperazioneLog;

public class AnalisiOperazioniSemplice implements AnalisiOperazioni {

	@Override
	public List<String> analizza(List<VoceOperazioneLog> voci) {
		List<String> lista = new ArrayList<>();
		lista.add("Esempio di anomalia operazioni 1");
		lista.add("Esempio di anomalia operazioni 2");
		lista.add("Esempio di anomalia operazioni 3");
		return lista;
	}

}
