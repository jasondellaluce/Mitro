package mitro.persistenza.mock;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import mitro.exceptions.PersistenzaException;
import mitro.model.Archiviazione;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Professore;
import mitro.model.Studente;
import mitro.persistenza.DAOArchiviazione;

public class CollectionDAOArchiviazione implements DAOArchiviazione {

	private Collection<Archiviazione> archiviazioni;
	
	public CollectionDAOArchiviazione(Collection<Archiviazione> archiviazioni) {
		this.archiviazioni = archiviazioni;
	}

	@Override
	public void registraArchiviazione(Archiviazione archiviazione) throws PersistenzaException {
		Optional<Archiviazione> vecchio = archiviazioni.stream()
				.filter(o -> o.getClass().equals(archiviazione.getClass()))
				.filter(o -> Objects.equals(o.getStudente(), archiviazione.getStudente())
						&& Objects.equals(o.getAttivita(), archiviazione.getAttivita()))
				.findAny();
		if(vecchio.isPresent())
			throw new PersistenzaException("Gia registrato");
		archiviazioni.add(archiviazione);
	}

	@Override
	public void modificaArchiviazione(Archiviazione archiviazione) throws PersistenzaException {
		Optional<Archiviazione> vecchio = archiviazioni.stream()
				.filter(o -> o.getClass().equals(archiviazione.getClass()))
				.filter(o -> Objects.equals(o.getStudente(), archiviazione.getStudente())
						&& Objects.equals(o.getAttivita(), archiviazione.getAttivita()))
				.findAny();
		if(!vecchio.isPresent())
			throw new PersistenzaException("Non registrato");
		archiviazioni.remove(vecchio.get());
		archiviazioni.add(archiviazione);
	}

	@Override
	public void eliminaArchiviazione(Archiviazione archiviazione) throws PersistenzaException {
		Optional<Archiviazione> vecchio = archiviazioni.stream()
				.filter(o -> o.getClass().equals(archiviazione.getClass()))
				.filter(o -> Objects.equals(o.getStudente(), archiviazione.getStudente())
						&& Objects.equals(o.getAttivita(), archiviazione.getAttivita()))
				.findAny();
		if(!vecchio.isPresent())
			throw new PersistenzaException("Non registrato");
		archiviazioni.remove(vecchio.get());
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioni() throws PersistenzaException {
		return archiviazioni.stream().collect(Collectors.toList());
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioniPerStudente(Studente studente) throws PersistenzaException {
		return archiviazioni.stream().filter(o -> o.getStudente().equals(studente)).collect(Collectors.toList());
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioniPerProfessore(Professore professore) throws PersistenzaException {
		return archiviazioni.stream().filter(o -> o.getAttivita().getProfessore().equals(professore)).collect(Collectors.toList());
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioniPerClasse(Classe classe) throws PersistenzaException {
		return archiviazioni.stream().filter(o -> o.getAttivita().getClasse().equals(classe)).collect(Collectors.toList());
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioniPerAttivita(Attivita attivita) throws PersistenzaException {
		return archiviazioni.stream().filter(o -> o.getAttivita().equals(attivita)).collect(Collectors.toList());
	}

}
