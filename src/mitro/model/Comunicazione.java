package mitro.model;

import java.time.LocalDateTime;

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
			return this.getDestinatario().equals(that.getDestinatario())
					&& this.getDataOra().equals(that.getDataOra())
					&& this.getOggetto().equals(that.getOggetto())
					&& this.getContenuto().equals(that.getContenuto());
		}
		return false;
	}
	
}
