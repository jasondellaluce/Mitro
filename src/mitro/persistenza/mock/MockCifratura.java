package mitro.persistenza.mock;

import mitro.persistenza.Cifratura;

public class MockCifratura implements Cifratura {

	@Override
	public String cifra(String valore) {
		return valore;
	}

	@Override
	public String decifra(String valore) {
		return valore;
	}

}
