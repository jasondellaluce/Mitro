package mitro.persistenza.cifrature;

import mitro.persistenza.Cifratura;

public class CifraturaXor implements Cifratura {

	private String cifraDecifra(String valore) {
		if(valore == null)
			return null;
        char xorKey = 'P'; 
        String outputString = ""; 
        int len = valore.length(); 
        for (int i = 0; i < len; i++) { 
            outputString = outputString +  
            Character.toString((char) (valore.charAt(i) ^ xorKey)); 
        } 
        return outputString; 
	}
	
	@Override
	public String cifra(String valore) {
		if(valore == null)
			return null;
        return cifraDecifra(valore);
	}

	@Override
	public String cifraHash(String valore) {
		if(valore == null)
			return null;
		return cifraDecifra(valore + "AAAAAAAAAA");
	}

	@Override
	public String decifra(String valore) {
		if(valore == null)
			return null;
		return cifraDecifra(valore);
	}

}
