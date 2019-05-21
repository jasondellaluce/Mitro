package mitro.model;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Iscritto extends Utente {

	private String nome;
	private String cognome;
	private String email;
	private String indirizzoResidenza;
	private String telefono;
	private LocalDate dataNascita;
	
	public Iscritto() {
		
	}
	
	public Iscritto(String nome, String cognome, String email,
			String indirizzoResidenza, String telefono, LocalDate dataNascita) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.indirizzoResidenza = indirizzoResidenza;
		this.telefono = telefono;
		this.dataNascita = dataNascita;
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
					&& Objects.equals(this.getNome(), that.getNome())
					&& Objects.equals(this.getCognome(), that.getCognome())
					&& Objects.equals(this.getEmail(), that.getEmail())
					&& Objects.equals(this.getIndirizzoResidenza(), that.getIndirizzoResidenza())
					&& Objects.equals(this.getTelefono(), that.getTelefono())
					&& Objects.equals(this.getDataNascita(), that.getDataNascita());
		}
		return false;
	}
}
