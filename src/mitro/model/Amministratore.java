package mitro.model;

public class Amministratore extends Utente {

	public Amministratore() {
		setRuolo(Ruolo.AMMINISTRATORE);
	}
	
	@Override
	public void setRuolo(Ruolo ruolo) {
		if(!ruolo.equals(Ruolo.AMMINISTRATORE))
			throw new IllegalArgumentException("Ruolo inacettabile");
		super.setRuolo(ruolo);
	}
	
	@Override
	public String toString() {
		return "Amministratore [id=" + getId() + ", ruolo=" + getRuolo() + "]";
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Amministratore) {
			return super.equals(o);
		}
		return false;
	}
	
}
