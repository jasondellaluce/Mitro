package mitro.persistenza.mock;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import mitro.exceptions.PersistenzaException;
import mitro.model.Classe;
import mitro.persistenza.DAOClasse;

public class CollectionDAOClasse implements DAOClasse {

	private int lastId = 0;
	private Collection<Classe> classi;
	
	public CollectionDAOClasse(Collection<Classe> classi) {
		this.classi = classi;
	}
	
	@Override
	public void registraClasse(Classe classe) throws PersistenzaException {
		classe.setId(Integer.toString(lastId++));
		classi.add(classe);
	}

	@Override
	public void modificaClasse(Classe classe) throws PersistenzaException {
		Optional<Classe> vecchio = classi.stream()
				.filter(o -> Objects.equals(o.getId(), classe.getId()))
				.findAny();
		if(vecchio.isPresent()) {
			classi.remove(vecchio.get());
			classi.add(classe);
		}
		else
			throw new PersistenzaException("Non registrato");
	}

	@Override
	public void eliminaClasse(Classe classe) throws PersistenzaException {
		Optional<Classe> vecchio = classi.stream()
				.filter(o -> Objects.equals(o.getId(), classe.getId()))
				.findAny();
		if(!vecchio.isPresent())
			throw new PersistenzaException("Non registrato");
		classi.remove(vecchio.get());
	}

	@Override
	public Classe ottieniClassePerId(String id) throws PersistenzaException {
		return classi.stream()
				.filter(o -> Objects.equals(o.getId(), id))
				.findAny()
				.orElse(null);
	}

	@Override
	public List<Classe> ottieniClassi() throws PersistenzaException {
		return classi.stream().collect(Collectors.toList());
	}

	@Override
	public List<Classe> ottieniClassiPerNome(String nome) throws PersistenzaException {
		return classi.stream()
				.filter(o -> o.getNome().contains(nome))
				.collect(Collectors.toList());
	}

	@Override
	public List<Classe> ottieniClassiPerAnnoScolastico(String annoScolastico) throws PersistenzaException {
		return classi.stream()
				.filter(o -> o.getAnnoScolastico().contains(annoScolastico))
				.collect(Collectors.toList());
	}

}
