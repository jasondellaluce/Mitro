package mitro.controller;

import mitro.controller.amministratore.AmministrazioneClassi;
import mitro.controller.amministratore.AmministrazioneIscritti;
import mitro.controller.log.GestioneLog;
import mitro.controller.log.LoggerMessaggi;
import mitro.controller.log.LoggerOperazioni;
import mitro.controller.login.Login;
import mitro.controller.professore.GestioneClasse;
import mitro.controller.professore.GestioneProfessore;
import mitro.controller.studente.GestioneStudente;

public class ControllerFactory {

	private static ControllerFactory instance;
	
	private ControllerFactory() {
		
	}
	
	public static ControllerFactory getInstance() {
		if(instance == null)
			instance = new ControllerFactory();
		return instance;
	}
	
	public Login getLogin() {
		return null;
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
	
	public LoggerOperazioni getLoggerOperazioni() {
		return null;
	}
	
	public LoggerMessaggi getLoggerMessaggi() {
		return null;
	}
	
}
