package mitro.controller;

import java.io.OutputStreamWriter;

import mitro.controller.amministratore.AmministrazioneClassi;
import mitro.controller.amministratore.AmministrazioneIscritti;
import mitro.controller.log.GestioneLog;
import mitro.controller.log.LoggerMessaggi;
import mitro.controller.log.LoggerOperazioni;
import mitro.controller.log.file.WriterLoggerMessaggi;
import mitro.controller.log.file.WriterLoggerOperazioni;
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
		return new WriterLoggerOperazioni(new OutputStreamWriter(System.out));
	}
	
	public LoggerMessaggi getLoggerMessaggi() {
		return new WriterLoggerMessaggi(new OutputStreamWriter(System.out));
	}
	
}
