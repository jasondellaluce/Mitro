package mitro.model;

import java.util.Objects;

public class Classe {

	private String id;
	private String nome;
	private String annoScolastico;
	private String descrizione;
	
	public Classe() {
		
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
	
	public String getAnnoScolastico() {
		return annoScolastico;
	}
	
	public void setAnnoScolastico(String annoScolastico) {
		this.annoScolastico = annoScolastico;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "Classe [id=" + id + ", nome=" + nome + ", annoScolastico=" + annoScolastico + ", descrizione="
				+ descrizione + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Classe) {
			Classe that = (Classe) o;
			return Objects.equals(this.getId(), that.getId())
					&& Objects.equals(this.getNome(), that.getNome())
					&& Objects.equals(this.getAnnoScolastico(), that.getAnnoScolastico())
					&& Objects.equals(this.getDescrizione(), that.getDescrizione());
		}
		return false;
	}
	
}
