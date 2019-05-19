package mitro.model;

public class Voto extends Archiviazione {

	private double valore;
	
	public Voto() {
		
	}

	public double getValore() {
		return valore;
	}

	public void setValore(double valore) {
		this.valore = valore;
	}
	
	@Override
	public String toString() {
		return "Voto [valore=" + valore + ", studente=" + getStudente() + ", attivita=" + getAttivita() + "]";
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Voto) {
			Voto that = (Voto) o;
			return super.equals(that)
					&& this.getValore() == that.getValore();
		}
		return false;
	}
	
}
