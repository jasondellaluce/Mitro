package mitro.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Log {
	
	private List<VoceLog> voci;

	public Log(List<VoceLog> voci) {
		this.voci = voci;
	}
	
	public List<VoceLog> getListaVoci(LocalDateTime from, LocalDateTime to) {
		return voci.stream()
				.filter(v -> v.getDataOra().isAfter(from) && v.getDataOra().isBefore(to))
				.collect(Collectors.toList());
	}
	
}
