package mitro.controller.log.analisi;

import java.util.ArrayList;
import java.util.List;

import mitro.controller.log.AnalisiMessaggi;
import mitro.model.VoceMessaggioLog;

public class AnalisiMessaggiSemplice implements AnalisiMessaggi {

	@Override
	public List<String> analizza(List<VoceMessaggioLog> voci) {
		List<String> lista = new ArrayList<>();
		lista.add("Esempio di anomalia messaggi 1");
		lista.add("Esempio di anomalia messaggi 2");
		lista.add("Esempio di anomalia messaggi 3");
		return lista;
	}

}
