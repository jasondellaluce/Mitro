package mitro.demo;

import mitro.model.Professore;
import mitro.model.Ruolo;
import mitro.persistenza.DAOFactory;
import mitro.persistenza.DAOUtente;

public class Test {

	public static void main(String[] args) throws Exception {
		DAOUtente dao = DAOFactory.getInstance().getDAOUtente();
		Professore u = new Professore();
		u.setNome("Marco");
		u.setCognome("Berti");
		dao.registraUtente(u);
		System.out.println(dao.ottieniUtentiPerRuolo(Ruolo.PROFESSORE));
	}

}
