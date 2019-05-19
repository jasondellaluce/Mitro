package mitro.model;

import java.time.LocalDate;
import java.util.Objects;

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
			return Objects.equals(getProfessore(), that.getProfessore())
					&& Objects.equals(this.getData(), that.getData())
					&& this.getOraInizio() == that.getOraInizio()
					&& Objects.equals(this.getMateria(), that.getMateria())
					&& Objects.equals(this.getAnnotazione(), that.getAnnotazione())
					&& Objects.equals(this.getClasse(), that.getClasse());
		}
		return false;
	}
	
}
