package mitro.model;

import java.time.LocalDate;

public class Attivita {

	private Professore professore;
	private LocalDate data;
	private int oraInizio;
	private Materia materia;
	private String annotazione;
	private Classe classe;
	
	public Attivita() {
		
	}

	public Professore getProfessore() {
		return professore;
	}

	public void setProfessore(Professore professore) {
		this.professore = professore;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(int oraInizio) {
		this.oraInizio = oraInizio;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public String getAnnotazione() {
		return annotazione;
	}

	public void setAnnotazione(String annotazione) {
		this.annotazione = annotazione;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	@Override
	public String toString() {
		return "Attivita [professore=" + professore + ", data=" + data + ", oraInizio=" + oraInizio + ", materia="
				+ materia + ", annotazione=" + annotazione + ", classe=" + classe + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Attivita) {
			Attivita that = (Attivita) o;
			return this.getProfessore().equals(that.getProfessore())
					&& this.getData().equals(that.getData())
					&& this.getOraInizio() == that.getOraInizio()
					&& this.getMateria().equals(that.getMateria())
					&& this.getAnnotazione().equals(that.getAnnotazione())
					&& this.getClasse().equals(that.getClasse());
		}
		return false;
	}
	
}
