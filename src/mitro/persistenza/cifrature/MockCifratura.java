package mitro.persistenza.cifrature;

import mitro.persistenza.Cifratura;

public class MockCifratura implements Cifratura {

	@Override
	public String cifra(String valore) {
		if(valore == null)
			return valore;
		return "AAA" + valore;
	}

	@Override
	public String cifraHash(String valore) {
		if(valore == null)
			return valore;
		return "B" + valore;
	}

	@Override
	public String decifra(String valore) {
		if(valore == null)
			return valore;
		return valore.substring(3);
	}

}
