package mitro.model;

import java.time.LocalDate;

public abstract class Iscritto extends Utente {

	private String nome;
	private String cognome;
	private String email;
	private String indirizzoResidenza;
	private String telefono;
	private LocalDate dataNascita;
	
	public Iscritto() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Iscritto) {
			Iscritto that = (Iscritto) o;
			return super.equals(that)
					&& this.getNome().equals(that.getNome())
					&& this.getCognome().equals(that.getCognome())
					&& this.getEmail().equals(that.getEmail())
					&& this.getIndirizzoResidenza().equals(that.getIndirizzoResidenza())
					&& this.getTelefono().equals(that.getTelefono())
					&& this.getDataNascita().equals(that.getDataNascita());
		}
		return false;
	}
}
