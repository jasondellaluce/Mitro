package mitro.persistenza.cifrature;

import mitro.persistenza.Cifratura;

public class CifraturaInChiaro implements Cifratura {

	@Override
	public String cifra(String valore) {
		return valore;
	}

	@Override
	public String decifra(String valore) {
		return valore;
	}

	@Override
	public String cifraHash(String valore) {
		return valore;
	}

}
