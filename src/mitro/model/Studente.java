package mitro.model;

import java.util.Objects;

public class Studente extends Iscritto {

	private Classe classe;
	
	public Studente() {
		setRuolo(Ruolo.STUDENTE);
	}
	
	@Override
	public void setRuolo(Ruolo ruolo) {
		if(!ruolo.equals(Ruolo.STUDENTE))
			throw new IllegalArgumentException("Ruolo inacettabile");
		super.setRuolo(ruolo);
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}
	
	@Override
	public String toString() {
		return "Studente [classe=" + classe + ", nome=" + getNome() + ", cognome=" + getCognome()
				+ ", email=" + getEmail() + ", indirizzoResidenza=" + getIndirizzoResidenza()
				+ ", telefono=" + getTelefono() + ", dataNascita=" + getDataNascita() + ", id=" + getId()
				+ ", ruolo=" + getRuolo() + "]";
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Studente) {
			Studente that = (Studente) o;
			return super.equals(that)
					&& Objects.equals(this.getClasse(), that.getClasse());
		}
		return false;
	}
	
}
