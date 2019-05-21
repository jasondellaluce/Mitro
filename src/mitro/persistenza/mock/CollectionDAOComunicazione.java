package mitro.persistenza.collection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import mitro.exceptions.PersistenzaException;
import mitro.model.Comunicazione;
import mitro.model.Iscritto;
import mitro.persistenza.DAOComunicazione;

public class CollectionDAOComunicazione implements DAOComunicazione {

	private List<Comunicazione> lista = new ArrayList<>(100);
	
	@Override
	public void registraComunicazione(Comunicazione comunicazione) throws PersistenzaException {
		Optional<Comunicazione> vecchio = lista.stream()
				.filter(o -> Objects.equals(o.getDestinatario(), comunicazione.getDestinatario())
						&& Objects.equals(o.getDataOra(), comunicazione.getDataOra()))
				.findAny();
		if(vecchio.isPresent())
			throw new PersistenzaException("Gia registrato");
		lista.add(comunicazione);
	}

	@Override
	public void modificaComunicazione(Comunicazione comunicazione) throws PersistenzaException {
		Optional<Comunicazione> vecchio = lista.stream()
				.filter(o -> Objects.equals(o.getDestinatario(), comunicazione.getDestinatario())
						&& Objects.equals(o.getDataOra(), comunicazione.getDataOra()))
				.findAny();
		if(!vecchio.isPresent())
			throw new PersistenzaException("Non registrato");
		lista.set(lista.indexOf(vecchio.get()), comunicazione);
	}

	@Override
	public void eliminaComunicazione(Comunicazione comunicazione) throws PersistenzaException {
		Optional<Comunicazione> vecchio = lista.stream()
				.filter(o -> Objects.equals(o.getDestinatario(), comunicazione.getDestinatario())
						&& Objects.equals(o.getDataOra(), comunicazione.getDataOra()))
				.findAny();
		if(!vecchio.isPresent())
			throw new PersistenzaException("Non registrato");
		lista.remove(vecchio.get());
	}

	@Override
	public List<Comunicazione> ottieniComunicazioni() throws PersistenzaException {
		return lista.stream().collect(Collectors.toList());
	}

	@Override
	public List<Comunicazione> ottieniComunicazioniPerDestinatario(Iscritto destinatario) throws PersistenzaException {
		return lista.stream()
				.filter(o -> o.getDestinatario().equals(destinatario))
				.collect(Collectors.toList());
	}

	@Override
	public List<Comunicazione> ottieniComunicazioniPerData(LocalDate from, LocalDate to) throws PersistenzaException {
		return lista.stream()
				.filter(o -> from.isBefore(o.getDataOra().toLocalDate()) && to.isAfter(o.getDataOra().toLocalDate()))
				.collect(Collectors.toList());
	}

}
