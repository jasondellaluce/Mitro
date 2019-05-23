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
		List<Cifratura> impl = Arrays.asList(new MockCifratura(), new TestoInChiaro());
		for(Cifratura c : impl) {
			assertEquals("Test", c.decifra(c.cifra("Test")), c.getClass().getName());
			assertEquals(null, c.decifra(c.cifra(null)), c.getClass().getName());
		}
	}

}
