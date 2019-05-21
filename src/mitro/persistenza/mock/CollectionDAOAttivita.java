package mitro.persistenza.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import mitro.exceptions.PersistenzaException;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Professore;
import mitro.model.Studente;
import mitro.persistenza.DAOAttivita;

public class CollectionDAOAttivita implements DAOAttivita {

	private List<Attivita> lista = new ArrayList<>(100);
	
	@Override
	public void registraAttivita(Attivita attivita) throws PersistenzaException {
		Optional<Attivita> vecchio = lista.stream()
				.filter(o -> Objects.equals(o.getClasse(), attivita.getClasse())
						&& Objects.equals(o.getData(), attivita.getData())
						&& o.getOraInizio() == attivita.getOraInizio())
				.findAny();
		if(vecchio.isPresent())
			throw new PersistenzaException("Gia registrato");
		lista.add(attivita);
	}

	@Override
	public void modificaAttivita(Attivita attivita) throws PersistenzaException {
		Optional<Attivita> vecchio = lista.stream()
				.filter(o -> Objects.equals(o.getClasse(), attivita.getClasse())
						&& Objects.equals(o.getData(), attivita.getData())
						&& o.getOraInizio() == attivita.getOraInizio())
				.findAny();
		if(!vecchio.isPresent())
			throw new PersistenzaException("Non registrato");
		lista.set(lista.indexOf(vecchio.get()), attivita);
	}

	@Override
	public void eliminaAttivita(Attivita attivita) throws PersistenzaException {
		Optional<Attivita> vecchio = lista.stream()
				.filter(o -> Objects.equals(o.getClasse(), attivita.getClasse())
						&& Objects.equals(o.getData(), attivita.getData())
						&& o.getOraInizio() == attivita.getOraInizio())
				.findAny();
		if(!vecchio.isPresent())
			throw new PersistenzaException("Non registrato");
		lista.remove(vecchio.get());
	}

	@Override
	public List<Attivita> ottieniAttivita() throws PersistenzaException {
		return lista.stream().collect(Collectors.toList());
	}

	@Override
	public List<Attivita> ottieniAttivitaPerClasse(Classe classe) throws PersistenzaException {
		return lista.stream()
				.filter(o -> o.getClasse().equals(classe))
				.collect(Collectors.toList());
	}

	@Override
	public List<Attivita> ottieniAttivitaPerStudente(Studente studente) throws PersistenzaException {
		return new ArrayList<>();
	}

	@Override
	public List<Attivita> ottieniAttivitaPerProfessore(Professore professore) throws PersistenzaException {
		return lista.stream()
				.filter(o -> o.getProfessore().equals(professore))
				.collect(Collectors.toList());
	}

}
