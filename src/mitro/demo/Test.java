package mitro.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import mitro.model.Comunicazione;
import mitro.model.Iscritto;
import mitro.persistenza.DAOComunicazione;
import mitro.persistenza.DAOFactory;
import mitro.persistenza.DAOUtente;

public class Test {

	public static void main(String[] args) throws Exception {
		DAOUtente dao = DAOFactory.getInstance().getDAOUtente();
		DAOComunicazione daoC = DAOFactory.getInstance().getDAOComunicazione();
		Iscritto dest = dao.ottieniIscrittiPerNomeOCognome("Jason").get(0);
		System.out.println(dest);
		/*Comunicazione c = new Comunicazione();
		c.setOggetto("Ciao");
		c.setContenuto("Conteniuto");
		c.setDataOra(LocalDateTime.now());
		c.setDestinatario(dest);
		daoC.registraComunicazione(c);*/
		System.out.println(daoC.ottieniComunicazioniPerData(LocalDate.now().plusDays(1), LocalDate.now().plusDays(1)));
	}

}
