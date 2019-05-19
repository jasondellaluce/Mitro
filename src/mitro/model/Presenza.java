package mitro.model;

public class Presenza extends Archiviazione {

	private boolean valore;
	
	public Presenza() {
		
	}

	public boolean isValore() {
		return valore;
	}

	public void setValore(boolean valore) {
		this.valore = valore;
	}

	@Override
	public String toString() {
		return "Presenza [valore=" + valore + ", studente=" + getStudente() + ", attivita=" + getAttivita() + "]";
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Presenza) {
			Presenza that = (Presenza) o;
			return super.equals(that)
					&& this.isValore() == that.isValore();
		}
		return false;
	}
	
}
