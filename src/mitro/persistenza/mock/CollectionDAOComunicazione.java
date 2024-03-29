package mitro.persistenza.mock;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import mitro.exceptions.PersistenzaException;
import mitro.model.Comunicazione;
import mitro.model.Iscritto;
import mitro.persistenza.DAOComunicazione;

public class CollectionDAOComunicazione implements DAOComunicazione {

	private Collection<Comunicazione> comunicazioni;
	
	public CollectionDAOComunicazione(Collection<Comunicazione> comunicazioni) {
		this.comunicazioni = comunicazioni;
	}
	
	@Override
	public void registraComunicazione(Comunicazione comunicazione) throws PersistenzaException {
		Optional<Comunicazione> vecchio = comunicazioni.stream()
				.filter(o -> Objects.equals(o.getDestinatario(), comunicazione.getDestinatario())
						&& Objects.equals(o.getDataOra(), comunicazione.getDataOra()))
				.findAny();
		if(vecchio.isPresent())
			throw new PersistenzaException("Gia registrato");
		comunicazioni.add(comunicazione);
	}

	@Override
	public void modificaComunicazione(Comunicazione comunicazione) throws PersistenzaException {
		Optional<Comunicazione> vecchio = comunicazioni.stream()
				.filter(o -> Objects.equals(o.getDestinatario(), comunicazione.getDestinatario())
						&& Objects.equals(o.getDataOra(), comunicazione.getDataOra()))
				.findAny();
		if(!vecchio.isPresent())
			throw new PersistenzaException("Non registrato");
		comunicazioni.remove(vecchio.get());
		comunicazioni.add(comunicazione);
	}

	@Override
	public void eliminaComunicazione(Comunicazione comunicazione) throws PersistenzaException {
		Optional<Comunicazione> vecchio = comunicazioni.stream()
				.filter(o -> Objects.equals(o.getDestinatario(), comunicazione.getDestinatario())
						&& Objects.equals(o.getDataOra(), comunicazione.getDataOra()))
				.findAny();
		if(!vecchio.isPresent())
			throw new PersistenzaException("Non registrato");
		comunicazioni.remove(vecchio.get());
	}

	@Override
	public List<Comunicazione> ottieniComunicazioni() throws PersistenzaException {
		return comunicazioni.stream().collect(Collectors.toList());
	}

	@Override
	public List<Comunicazione> ottieniComunicazioniPerDestinatario(Iscritto destinatario) throws PersistenzaException {
		return comunicazioni.stream()
				.filter(o -> o.getDestinatario().equals(destinatario))
				.collect(Collectors.toList());
	}

	@Override
	public List<Comunicazione> ottieniComunicazioniPerData(LocalDate from, LocalDate to) throws PersistenzaException {
		return comunicazioni.stream()
				.filter(o -> from.isBefore(o.getDataOra().toLocalDate()) && to.isAfter(o.getDataOra().toLocalDate()))
				.collect(Collectors.toList());
	}

}
