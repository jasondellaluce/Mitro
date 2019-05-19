package mitro.model;

public class Utente {

	private String id;
	private Ruolo ruolo;
	
	public Utente() {
		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Ruolo getRuolo() {
		return ruolo;
	}
	
	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	@Override
	public String toString() {
		return "Utente [id=" + getId() + ", ruolo=" + getRuolo() + "]";
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Utente) {
			Utente that = (Utente) o;
			return getId().equals(that.getId())
					&& this.getRuolo().equals(that.getRuolo());
		}
		return false;
	}
	
}
