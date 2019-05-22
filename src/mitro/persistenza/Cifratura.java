package mitro.persistenza;

public interface Cifratura {

	public String cifra(String valore);
	public String cifraHash(String valore);
	public String decifra(String valore);
	
}
