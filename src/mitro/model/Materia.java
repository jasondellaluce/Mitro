package mitro.model;

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
			return getId().equals(that.getId())
					&& this.getNome().equals(that.getNome())
					&& this.getDescrizione().equals(that.getDescrizione());
		}
		return false;
	}
	
}
