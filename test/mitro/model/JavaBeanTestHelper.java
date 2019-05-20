package mitro.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JavaBeanTestHelper {
	
	public static void testAccessor(Object oggetto, String... salta) {
		if(oggetto == null)
			throw new IllegalArgumentException("oggetto");
		
		testAccessor(oggetto.getClass(), oggetto, salta);
	}
	
	public static void testAccessor(Class<?> classeOggetto, Object oggetto, String... salta) {
		if(oggetto == null)
			throw new IllegalArgumentException("oggetto");
		if(classeOggetto == null)
			throw new IllegalArgumentException("classeOggetto");
		
		for(Field attributo : classeOggetto.getDeclaredFields()) {
			boolean daSaltare = false;
			for(String s : salta)
				if(attributo.getName().equals(s))
					daSaltare = true;
			
			if(!daSaltare)
				testAccessorAttributo(classeOggetto, oggetto, attributo.getName(),
						nuovaIstanzaDi(attributo.getType()));
		}
	}
	
	public static void testAccessorAttributo(Object oggetto, String attributo, Object valore) {
		if(oggetto == null)
			throw new IllegalArgumentException("oggetto");
		testAccessorAttributo(oggetto.getClass(), oggetto, attributo, valore);
	}
	
	public static void testAccessorAttributo(Class<?> classeOggetto, Object oggetto, 
			String attributo, Object valore) {
		if(oggetto == null)
			throw new IllegalArgumentException("oggetto");
		if(attributo == null || attributo.length() == 0)
			throw new IllegalArgumentException("attributo");
		if(classeOggetto == null)
			throw new IllegalArgumentException("classeOggetto");
		
		/* Controlla convenzione del costruttore senza argomenti */
		try {
			if(!Modifier.isAbstract(classeOggetto.getModifiers()))
					classeOggetto.newInstance();
		}
		catch (Exception e) {
			fail("Costruttore senza argomenti mancante");
		}
		
		/* Cerca attributo */
		Field attrField = null;
		try {
			attrField = classeOggetto.getDeclaredField(attributo);
			assertNotNull(attrField);
		}
		catch (Exception e) {
			fail("Attributo non presente: " + attributo);
		}
		
		/* Cerca accessor */
		String suffisso = attributo.substring(0, 1).toUpperCase() + attributo.substring(1);
		String prefissoGet = attrField.getType().equals(boolean.class) ? "is" : "get";
		Method getter = null;
		Method setter = null;
		try {
			getter = classeOggetto.getMethod(prefissoGet + suffisso);
			setter = classeOggetto.getMethod("set" + suffisso, attrField.getType());
		}
		catch (Exception e) {
			fail("Accessor non presenti per attributo: " + attributo);
		}
		
		/* Controlla consistenza accessor */
		try {
			System.out.println(attributo);
			setter.invoke(oggetto, valore);
			assertEquals(getter.invoke(oggetto), valore);
		}
		catch (Exception e) {
			fail(e);
		}
		
	}

	private static Object nuovaIstanzaDi(Class<?> tipo) {
		if(tipo.isEnum())
			return tipo.getEnumConstants()[0];
		
		if(tipo.equals(boolean.class))
			return true;
		
		if(tipo.equals(int.class))
			return 100;
		
		if(tipo.equals(double.class))
			return 1.2;
		
		if(tipo.equals(float.class))
			return 2.0f;
		
		if(tipo.equals(long.class))
			return 5L;
		
		if(tipo.equals(byte.class))
			return 1;
		
		if(tipo.equals(char.class))
			return 'a';
		
		if(tipo.equals(String.class))
			return "example";
		
		if(tipo.equals(LocalDateTime.class))
			return LocalDateTime.now();
		
		if(tipo.equals(LocalDate.class))
			return LocalDate.now();
		
		try {
			return tipo.newInstance();
		}
		catch (Exception e) {
			fail("Impossibile creare istanza di tipo: " + tipo.getName());
		}
		return null;
	}
	
}
