package mitro.persistenza.mock;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import mitro.exceptions.PersistenzaException;
import mitro.model.Iscritto;
import mitro.model.Ruolo;
import mitro.model.Utente;
import mitro.persistenza.DAOUtente;

public class CollectionDAOUtente implements DAOUtente {

	private int lastId = 0;
	private Collection<Utente> utenti;
	private Map<String, Utente> credenziali = new HashMap<>();
	
	public CollectionDAOUtente(Collection<Utente> utenti) {
		this.utenti = utenti;
	}
	
	@Override
	public void registraUtente(Utente utente) throws PersistenzaException {
		utente.setId(Integer.toString(lastId++));
		utenti.add(utente);
	}

	@Override
	public void modificaUtente(Utente utente) throws PersistenzaException {
		Optional<Utente> vecchio = utenti.stream()
				.filter(o -> Objects.equals(o.getId(), utente.getId()))
				.findAny();
		if(vecchio.isPresent()) {
			utenti.remove(vecchio.get());
			utenti.add(utente);
		}
		else
			throw new PersistenzaException("Non registrato");
	}

	@Override
	public void inserisciCredenziali(Utente utente, String username, String password) throws PersistenzaException {
		if(ottieniUtentePerId(utente.getId()) == null)
			return;
		
		String oldUsername = "";
		for(String u : credenziali.keySet())
			if(credenziali.get(u).getId().equals(utente.getId()))
				oldUsername = u;
		
		if(oldUsername.length() > 0)
			credenziali.remove(oldUsername);
		
		credenziali.put(username, utente);
	}

	@Override
	public void eliminaUtente(Utente utente) throws PersistenzaException {
		Optional<Utente> vecchio = utenti.stream()
				.filter(o -> Objects.equals(o.getId(), utente.getId()))
				.findAny();
		if(!vecchio.isPresent())
			throw new PersistenzaException("Non registrato");
		utenti.remove(vecchio.get());
	}

	@Override
	public Utente ottieniUtentePerId(String id) throws PersistenzaException {
		return utenti.stream()
				.filter(o -> Objects.equals(o.getId(), id))
				.findAny()
				.orElse(null);
	}

	@Override
	public Utente ottieniUtentePerCredenziali(String username, String password) throws PersistenzaException {
		return credenziali.get(username);
	}

	@Override
	public List<Utente> ottieniUtenti() throws PersistenzaException {
		return utenti.stream().collect(Collectors.toList());
	}

	@Override
	public List<Iscritto> ottieniIscrittiPerNomeOCognome(String filtro) throws PersistenzaException {
		return utenti.stream()
				.filter(o -> o instanceof Iscritto)
				.map(o -> (Iscritto) o)
				.filter(o -> o.getNome().contains(filtro) || o.getCognome().contains(filtro))
				.collect(Collectors.toList());
	}

	@Override
	public List<Utente> ottieniUtentiPerRuolo(Ruolo ruolo) throws PersistenzaException {
		return utenti.stream()
				.filter(u -> Objects.equals(u.getRuolo(), ruolo))
				.collect(Collectors.toList());
	}

}
