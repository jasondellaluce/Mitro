package mitro.model;

import java.util.Objects;

public class Materia {

	private String id;
	private String nome;
	private String descrizione;
	
	public Materia() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "Materia [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Materia) {
			Materia that = (Materia) o;
			return Objects.equals(this.getId(), that.getId())
					&& Objects.equals(this.getNome(), that.getNome())
					&& Objects.equals(this.getDescrizione(), that.getDescrizione());
		}
		return false;
	}
	
}
