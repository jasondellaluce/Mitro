package mitro.persistenza;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import mitro.persistenza.Cifratura;
import mitro.persistenza.cifrature.MockCifratura;
import mitro.persistenza.cifrature.TestoInChiaro;

class CifraturaTest {
	
	@Test
	void test() {
		String test = "Stringa esempio";
		List<Cifratura> impl = Arrays.asList(new MockCifratura(), new TestoInChiaro());
		
		for(Cifratura c : impl) {
			/* Test consistenza */
			assertEquals(test, c.decifra(c.cifra(test)), c.getClass().getName());
			assertEquals(null, c.decifra(c.cifra(null)), c.getClass().getName());
			assertEquals(null, c.decifra(c.cifraHash(null)), c.getClass().getName());
			
			/* Test univocità */
			String ultimo = c.cifra(test);
			String ultimoHash = c.cifraHash(test);
			for(int i = 0; i < 10; i++) {
				String nuovo = c.cifra(test);
				assertEquals(nuovo, ultimo);
				ultimo = nuovo;
				
				nuovo = c.cifraHash(test);
				assertEquals(nuovo, ultimoHash);
				ultimoHash = nuovo;
			}
		}
	}

}
