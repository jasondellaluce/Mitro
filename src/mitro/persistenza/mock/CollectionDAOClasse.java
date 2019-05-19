package mitro.persistenza.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import mitro.model.Classe;
import mitro.persistenza.DAOClasse;
import mitro.persistenza.PersistenzaException;

public class CollectionDAOClasse implements DAOClasse {

	private int lastId = 0;
	private List<Classe> lista = new ArrayList<>(100);
	
	@Override
	public void registraClasse(Classe classe) throws PersistenzaException {
		classe.setId(Integer.toString(lastId++));
		lista.add(classe);
	}

	@Override
	public void modificaClasse(Classe classe) throws PersistenzaException {
		Optional<Classe> vecchio = lista.stream()
				.filter(o -> Objects.equals(o.getId(), classe.getId()))
				.findAny();
		if(vecchio.isPresent())
			lista.set(lista.indexOf(vecchio.get()), classe);
		else
			throw new PersistenzaException("Non registrato");
	}

	@Override
	public void eliminaClasse(Classe classe) throws PersistenzaException {
		Optional<Classe> vecchio = lista.stream()
				.filter(o -> Objects.equals(o.getId(), classe.getId()))
				.findAny();
		if(!vecchio.isPresent())
			throw new PersistenzaException("Non registrato");
		lista.remove(vecchio.get());
	}

	@Override
	public Classe ottieniClassePerId(String id) throws PersistenzaException {
		return lista.stream()
				.filter(o -> Objects.equals(o.getId(), id))
				.findAny()
				.orElse(null);
	}

	@Override
	public List<Classe> ottieniClassi() throws PersistenzaException {
		return lista.stream().collect(Collectors.toList());
	}

	@Override
	public List<Classe> ottieniClassiPerNome(String nome) throws PersistenzaException {
		return lista.stream()
				.filter(o -> o.getNome().contains(nome))
				.collect(Collectors.toList());
	}

	@Override
	public List<Classe> ottieniClassiPerAnnoScolastico(String annoScolastico) throws PersistenzaException {
		return lista.stream()
				.filter(o -> o.getAnnoScolastico().contains(annoScolastico))
				.collect(Collectors.toList());
	}

}
