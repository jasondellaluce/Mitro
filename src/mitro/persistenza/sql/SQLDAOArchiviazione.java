package mitro.persistenza.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import mitro.exceptions.ElementoNonPersistenteException;
import mitro.exceptions.PersistenzaException;
import mitro.model.Archiviazione;
import mitro.model.Attivita;
import mitro.model.Classe;
import mitro.model.Presenza;
import mitro.model.Professore;
import mitro.model.Studente;
import mitro.model.Voto;
import mitro.persistenza.Cifratura;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOUtente;

public class SQLDAOArchiviazione extends SQLDAOAstratto implements DAOArchiviazione {

	private SQLDAOAttivita daoAttivita;
	private DAOUtente daoUtente;

	public SQLDAOArchiviazione(DataSource dataSource, Cifratura cifratura) {
		super(dataSource);
		this.daoAttivita = new SQLDAOAttivita(dataSource, cifratura);
		this.daoUtente = new SQLDAOUtente(dataSource, cifratura);
	}
	
	private String getCodiceTipo(Archiviazione a) {
		if(a instanceof Voto)
			return "VO";
		
		if(a instanceof Presenza)
			return "PR";
		
		throw new IllegalArgumentException("archiviazione");
	}
	
	private Archiviazione parseResultSet(ResultSet resultSet) throws SQLException {
		Studente studente = new Studente();
		studente.setId(String.valueOf(resultSet.getInt("IdRegistrataPer")));
		switch(resultSet.getString("Tipo")) {
			default:
				throw new IllegalArgumentException("tipo");
			case "VO":
				Voto v = new Voto();
				v.setStudente(studente);
				return v;
			case "PR":
				Presenza p = new Presenza();
				p.setStudente(studente);
				return p;
		}
	}
	
	@Override
	public void registraArchiviazione(Archiviazione archiviazione) throws PersistenzaException {
		if(!archiviazione.getAttivita().getClasse().equals(archiviazione.getStudente().getClasse()))
			throw new IllegalArgumentException("archiviazione - consistenza");
		
		if(archiviazione.getAttivita() == null
				|| daoAttivita.ottieniAttivitaPerClasse(archiviazione.getAttivita().getClasse()).stream()
						.filter(p -> p.equals(archiviazione.getAttivita()))
						.findAny()
						.isPresent())
			throw new ElementoNonPersistenteException("attivita");
		if(archiviazione.getStudente() == null
				|| daoUtente.ottieniUtentePerId(archiviazione.getStudente().getId()) == null)
			throw new ElementoNonPersistenteException("studente");
		
		if(archiviazione instanceof Voto)
			registraArchiviazione((Voto) archiviazione);
		
		if(archiviazione instanceof Presenza)
			registraArchiviazione((Presenza) archiviazione);
		
		throw new IllegalArgumentException("archiviazione");
	}

	private void registraArchiviazione(Presenza presenza) throws PersistenzaException {
		String query = "INSERT INTO ARCHIVIAZIONI (Tipo, IdRegistrataPer, IdRegistrataIn, ValorePresenza" +
				"VALUES(" + getCodiceTipo(presenza) + ", ?, ?, ?)";
		this.eseguiUpdate(query, (s) -> {
			s.setInt(1, Integer.parseInt(presenza.getStudente().getId()));
			s.setInt(2, daoAttivita.ottieniIdAttivita(presenza.getAttivita()));
			s.setBoolean(3, presenza.isValore());
			s.executeUpdate();
		});		
	}

	private void registraArchiviazione(Voto voto) throws PersistenzaException {
		String query = "INSERT INTO ARCHIVIAZIONI (Tipo, IdRegistrataPer, IdRegistrataIn, ValoreVoto" +
				"VALUES(" + getCodiceTipo(voto) + ", ?, ?, ?)";
		this.eseguiUpdate(query, (s) -> {
			s.setInt(1, Integer.parseInt(voto.getStudente().getId()));
			s.setInt(2, daoAttivita.ottieniIdAttivita(voto.getAttivita()));
			s.setDouble(3, voto.getValore());
			s.executeUpdate();
		});	
	}

	@Override
	public void modificaArchiviazione(Archiviazione archiviazione) throws PersistenzaException {
		if(!archiviazione.getAttivita().getClasse().equals(archiviazione.getStudente().getClasse()))
			throw new IllegalArgumentException("archiviazione - consistenza");
		
		if(archiviazione.getAttivita() == null
				|| daoAttivita.ottieniAttivitaPerClasse(archiviazione.getAttivita().getClasse()).stream()
						.filter(p -> p.equals(archiviazione.getAttivita()))
						.findAny()
						.isPresent())
			throw new ElementoNonPersistenteException("attivita");
		if(archiviazione.getStudente() == null
				|| daoUtente.ottieniUtentePerId(archiviazione.getStudente().getId()) == null)
			throw new ElementoNonPersistenteException("studente");
		
		if(archiviazione instanceof Voto)
			modificaArchiviazione((Voto) archiviazione);
		
		if(archiviazione instanceof Presenza)
			modificaArchiviazione((Presenza) archiviazione);
		
		throw new IllegalArgumentException("archiviazione");
	}
	
	private void modificaArchiviazione(Presenza presenza) throws PersistenzaException {
		String query = "UPDATE ARCHIVIAZIONI SET ValorePresenza=? WHERE Tipo=" + getCodiceTipo(presenza)
				+ " and IdRegistrataPer=? and IdRegistrataIn=?";
		this.eseguiUpdate(query, (s) -> {
			s.setBoolean(1, presenza.isValore());
			s.setInt(2, Integer.parseInt(presenza.getStudente().getId()));
			s.setInt(3, daoAttivita.ottieniIdAttivita(presenza.getAttivita()));
			s.executeUpdate();
		});		
	}

	private void modificaArchiviazione(Voto voto) throws PersistenzaException {
		String query = "UPDATE ARCHIVIAZIONI SET ValoreVoto=? WHERE Tipo=" + getCodiceTipo(voto)
				+ " and IdRegistrataPer=? and IdRegistrataIn=?";
		this.eseguiUpdate(query, (s) -> {
			s.setDouble(1, voto.getValore());
			s.setInt(2, Integer.parseInt(voto.getStudente().getId()));
			s.setInt(3, daoAttivita.ottieniIdAttivita(voto.getAttivita()));
			s.executeUpdate();
		});		
	}

	@Override
	public void eliminaArchiviazione(Archiviazione archiviazione) throws PersistenzaException {
		String query = "DELETE FROM ARCHIVIAZIONI WHERE Tipo=" + getCodiceTipo(archiviazione)
				+ " and IdRegistrataPer=? and IdRegistrataIn=?";
		this.eseguiUpdate(query, (s) -> {
			s.setInt(2, Integer.parseInt(archiviazione.getStudente().getId()));
			s.setInt(3, daoAttivita.ottieniIdAttivita(archiviazione.getAttivita()));
			if(s.executeUpdate() != 1)
				throw new ElementoNonPersistenteException();
		});
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioni() throws PersistenzaException {
		List<Archiviazione> result = new ArrayList<>();
		List<Integer> attivitaId = new ArrayList<>();
		String query = "SELECT * FROM ARCHIVIAZIONE";
		this.eseguiQuery(query, (s) -> {
			ResultSet resultSet = s.executeQuery();
			while(resultSet.next()) {
				result.add(parseResultSet(resultSet));
				attivitaId.add(resultSet.getInt("IdRegistrataIn"));
			}
			return result;
		});
		for(int i = 0; i < result.size(); i++) {
			result.get(i).setAttivita(daoAttivita.ottieniAttivitaPerId(attivitaId.get(i)));
			result.get(i).setStudente((Studente) daoUtente.ottieniUtentePerId(result.get(i).getStudente().getId()));
		}
		return result;
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioniPerStudente(Studente studente) throws PersistenzaException {
		return ottieniArchiviazioni().stream()
				.filter(a -> a.getStudente().equals(studente))
				.collect(Collectors.toList());
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioniPerProfessore(Professore professore) throws PersistenzaException {
		return ottieniArchiviazioni().stream()
				.filter(a -> a.getAttivita().getProfessore().equals(professore))
				.collect(Collectors.toList());
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioniPerClasse(Classe classe) throws PersistenzaException {
		return ottieniArchiviazioni().stream()
				.filter(a -> a.getAttivita().getClasse().equals(classe))
				.collect(Collectors.toList());
	}

	@Override
	public List<Archiviazione> ottieniArchiviazioniPerAttivita(Attivita attivita) throws PersistenzaException {
		return ottieniArchiviazioni().stream()
				.filter(a -> a.getAttivita().equals(attivita))
				.collect(Collectors.toList());
	}

}
