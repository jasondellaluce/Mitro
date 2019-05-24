package mitro.controller.deployment;

import java.time.ZoneId;

public class Configurazione {

	public static final String APP_NOME = "Mitro";
	public static final int APP_VER_MAJOR = 1;
	public static final int APP_VER_MINOR = 0;
	public static final int APP_VER_REV = 0;
	public static final String APP_VERSIONE = APP_NOME + " v." 
			+ APP_VER_MAJOR + "."
			+ APP_VER_MINOR + "."
			+ APP_VER_REV;
	public static String PATH_RELATIVO = "";
	public static final ZoneId ZONE_ID = ZoneId.of("Europe/Rome");
	
}
