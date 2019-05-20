package mitro.controller.login;

import java.util.HashMap;
import java.util.Map;

import mitro.model.Utente;

public class MapPermessoLogin implements PermessoLogin {
	
	private Map<String, Login> proprietari;
	
	public MapPermessoLogin() {
		this.proprietari = new HashMap<>();
	}
	
	@Override
	public synchronized boolean ottieniPermesso(Utente utente, Login richiedente) {
		if(utente == null || utente.getId() == null || utente.getId().length() == 0)
			throw new IllegalArgumentException("utente");
		if(richiedente == null)
			throw new IllegalArgumentException("richiedente");
		
		if(proprietari.containsKey(utente.getId()))
			return false;
		
		proprietari.put(utente.getId(), richiedente);
		return true;
	}
	
	@Override
	public synchronized boolean rilasciaPermesso(Utente utente, Login richiedente) {
		if(utente == null || utente.getId() == null || utente.getId().length() == 0)
			throw new IllegalArgumentException("utente");
		if(richiedente == null)
			throw new IllegalArgumentException("richiedente");
		
		if(!proprietari.containsKey(utente.getId()))
			return false;
		
		if(!proprietari.get(utente.getId()).equals(richiedente))
			return false;
		
		proprietari.remove(utente.getId());
		return true;
	}
	
}
