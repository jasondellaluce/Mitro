package mitro.demo;

import mitro.model.*;
import mitro.persistenza.DAOArchiviazione;
import mitro.persistenza.DAOAttivita;
import mitro.persistenza.DAOClasse;
import mitro.persistenza.DAOFactory;
import mitro.persistenza.DAOUtente;

public class Test {

	public static void main(String[] args) throws Exception {
		DAOUtente daoUtente = DAOFactory.getInstance().getDAOUtente();
		DAOClasse daoClasse = DAOFactory.getInstance().getDAOClasse();
		DAOAttivita daoAttivita = DAOFactory.getInstance().getDAOAttivita();
		DAOArchiviazione daoArch = DAOFactory.getInstance().getDAOArchiviazione();
		
		Classe cla = new Classe();
		cla.setNome("4A");
		cla.setAnnoScolastico("18");
		cla.setDescrizione("uuuu");
		daoClasse.registraClasse(cla);
		
		Studente stu = new Studente();
		stu.setNome("Jason");
		stu.setCognome("Dellaluce");
		stu.setClasse(cla);
		daoUtente.registraUtente(stu);

	}

}
