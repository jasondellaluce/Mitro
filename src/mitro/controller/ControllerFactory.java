package mitro.controller;

import java.io.OutputStreamWriter;

import mitro.controller.amministratore.AmministrazioneClassi;
import mitro.controller.amministratore.AmministrazioneIscritti;
import mitro.controller.log.GestioneLog;
import mitro.controller.log.LoggerMessaggi;
import mitro.controller.log.LoggerOperazioni;
import mitro.controller.log.file.WriterLoggerMessaggi;
import mitro.controller.log.file.WriterLoggerOperazioni;
import mitro.controller.login.MapPermessoLogin;
import mitro.controller.login.PermessoLogin;
import mitro.controller.login.Login;
import mitro.controller.login.LoginController;
import mitro.controller.professore.GestioneClasse;
import mitro.controller.professore.GestioneProfessore;
import mitro.controller.studente.GestioneStudente;
import mitro.persistenza.DAOFactory;

public class ControllerFactory {

	private static ControllerFactory instance;
	private LoggerOperazioni loggerOperazioniCondiviso;
	private LoggerMessaggi loggerMessaggiCondiviso;
	private PermessoLogin permessoLoginCondiviso;
	
	private ControllerFactory() {
		this.loggerOperazioniCondiviso = new WriterLoggerOperazioni(new OutputStreamWriter(System.out));
		this.loggerMessaggiCondiviso = new WriterLoggerMessaggi(new OutputStreamWriter(System.out));
		this.permessoLoginCondiviso = new MapPermessoLogin();
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
	
	public GestioneStudente getGestioneStudente() {
		return null;
	}
	
	public GestioneProfessore getGestioneProfessore() {
		return null;
	}
	
	public GestioneClasse getGestioneClasse() {
		return null;
	}
	
	public AmministrazioneIscritti getAmministrazioneIscritti() {
		return null;
	}
	
	public AmministrazioneClassi getAmministrazioneClassi() {
		return null;
	}
	
	public GestioneLog getGestioneLog() {
		return null;
	}
	
}
