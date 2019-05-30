package mitro.controller;

import java.io.FileWriter;
import mitro.controller.amministratore.AmministrazioneClassi;
import mitro.controller.amministratore.AmministrazioneClassiController;
import mitro.controller.amministratore.AmministrazioneIscritti;
import mitro.controller.amministratore.AmministrazioneIscrittiController;
import mitro.controller.log.GestioneLog;
import mitro.controller.log.GestioneLogController;
import mitro.controller.log.LoggerMessaggi;
import mitro.controller.log.LoggerOperazioni;
import mitro.controller.log.WriterLoggerMessaggi;
import mitro.controller.log.WriterLoggerOperazioni;
import mitro.controller.log.analisi.AnalisiMessaggiSemplice;
import mitro.controller.log.analisi.AnalisiOperazioniSemplice;
import mitro.controller.login.MapPermessoLogin;
import mitro.controller.login.PermessoLogin;
import mitro.controller.login.Login;
import mitro.controller.login.LoginController;
import mitro.controller.professore.GestioneClasse;
import mitro.controller.professore.GestioneClasseController;
import mitro.controller.professore.GestioneProfessore;
import mitro.controller.professore.GestioneProfessoreController;
import mitro.controller.studente.GestioneStudente;
import mitro.controller.studente.GestioneStudenteController;
import mitro.model.Classe;
import mitro.model.Professore;
import mitro.model.Studente;
import mitro.persistenza.DAOFactory;

public class ControllerFactory {

	private static String nomeFileLogOperazioni = "LogOperazioni.log";
	private static String nomeFileLogMessaggi ="LogMessaggi.log";
	private static ControllerFactory instance;
	private LoggerOperazioni loggerOperazioniCondiviso;
	private LoggerMessaggi loggerMessaggiCondiviso;
	private PermessoLogin permessoLoginCondiviso;
	
	private ControllerFactory() {
		try {
			this.loggerOperazioniCondiviso = new WriterLoggerOperazioni(new FileWriter(nomeFileLogOperazioni, true));
			this.loggerMessaggiCondiviso = new WriterLoggerMessaggi(new FileWriter(nomeFileLogMessaggi, true));
			this.permessoLoginCondiviso = new MapPermessoLogin();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static ControllerFactory getInstance() {
		if(instance == null)
			instance = new ControllerFactory();
		return instance;
	}
	
	public LoggerOperazioni getLoggerOperazioni() {
		return loggerOperazioniCondiviso;
	}
	
	public LoggerMessaggi getLoggerMessaggi() {
		return loggerMessaggiCondiviso;
	}
	
	public Login getLogin() {
		return new LoginController(getLoggerOperazioni(),
				permessoLoginCondiviso,
				DAOFactory.getInstance().getDAOUtente());
	}
	
	public GestioneStudente getGestioneStudente(Studente studente) {
		return new GestioneStudenteController(getLoggerOperazioni(),
				DAOFactory.getInstance().getDAOComunicazione(),
				DAOFactory.getInstance().getDAOArchiviazione(),
				DAOFactory.getInstance().getDAOAttivita(),
				studente);
	}
	
	public GestioneProfessore getGestioneProfessore(Professore professore) {
		return new GestioneProfessoreController(getLoggerOperazioni(),
				DAOFactory.getInstance().getDAOComunicazione(),
				DAOFactory.getInstance().getDAOAttivita(),
				professore);
	}
	
	public GestioneClasse getGestioneClasse(Classe classe) {
		return new GestioneClasseController(getLoggerOperazioni(),
				DAOFactory.getInstance().getDAOArchiviazione(),
				DAOFactory.getInstance().getDAOAttivita(),
				DAOFactory.getInstance().getDAOUtente(),
				classe);
				
	}
	
	public AmministrazioneIscritti getAmministrazioneIscritti() {
		return new AmministrazioneIscrittiController(getLoggerOperazioni(),
				DAOFactory.getInstance().getDAOUtente(),
				DAOFactory.getInstance().getDAOComunicazione());
	}
	
	public AmministrazioneClassi getAmministrazioneClassi() {
		return new AmministrazioneClassiController(getLoggerOperazioni(),
				DAOFactory.getInstance().getDAOClasse(),
				DAOFactory.getInstance().getDAOArchiviazione(),
				DAOFactory.getInstance().getDAOAttivita());
	}
	
	public GestioneLog getGestioneLog() {
		return new GestioneLogController(getLoggerOperazioni(),
				nomeFileLogOperazioni,
				nomeFileLogMessaggi,
				new AnalisiOperazioniSemplice(),
				new AnalisiMessaggiSemplice());
	}
	
}
