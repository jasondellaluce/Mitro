package mitro.model;

public class Professore extends Iscritto {

	public Professore() {
		setRuolo(Ruolo.PROFESSORE);
	}
	
	@Override
	public void setRuolo(Ruolo ruolo) {
		if(!ruolo.equals(Ruolo.PROFESSORE))
			throw new IllegalArgumentException("Ruolo inacettabile");
		super.setRuolo(ruolo);
	}
	
	@Override
	public String toString() {
		return "Professore [nome=" + getNome() + ", cognome=" + getCognome() + ", email=" + getEmail()
				+ ", indirizzoResidenza=" + getIndirizzoResidenza() + ", telefono=" + getTelefono()
				+ ", dataNascita=" + getDataNascita() + ", id=" + getId() + ", ruolo=" + getRuolo()
				+ "]";
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Professore) {
			return super.equals(o);
		}
		return false;
	}
	
}
