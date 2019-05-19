package mitro.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comunicazione {

	private Iscritto destinatario;
	private LocalDateTime dataOra;
	private String oggetto;
	private String contenuto;
	
	public Comunicazione() {
		
	}

	public Iscritto getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Iscritto destinatario) {
		this.destinatario = destinatario;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}

	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	@Override
	public String toString() {
		return "Comunicazione [destinatario=" + getDestinatario() + ", dataOra=" + getDataOra() + ", oggetto=" + getOggetto() + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Comunicazione) {
			Comunicazione that = (Comunicazione) o;
			return Objects.equals(this.getDestinatario(), that.getDestinatario())
					&& Objects.equals(this.getDataOra(), that.getDataOra())
					&& Objects.equals(this.getOggetto(), that.getOggetto())
					&& Objects.equals(this.getContenuto(), that.getContenuto());
		}
		return false;
	}
	
}
