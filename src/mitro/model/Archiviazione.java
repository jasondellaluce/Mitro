package mitro.model;

import java.util.Objects;

public abstract class Archiviazione {

	private Studente studente;
	private Attivita attivita;
	
	public Archiviazione() {
		
	}

	public Studente getStudente() {
		return studente;
	}

	public void setStudente(Studente studente) {
		this.studente = studente;
	}

	public Attivita getAttivita() {
		return attivita;
	}

	public void setAttivita(Attivita attivita) {
		this.attivita = attivita;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Archiviazione) {
			Archiviazione that = (Archiviazione) o;
			return Objects.equals(getStudente(), that.getStudente())
					&& Objects.equals(this.getAttivita(), that.getAttivita());
		}
		return false;
	}
	
}
