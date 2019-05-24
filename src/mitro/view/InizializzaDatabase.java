package mitro.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sqlite.SQLiteDataSource;

import mitro.controller.deployment.Configurazione;
import mitro.persistenza.cifrature.TestoInChiaro;
import mitro.persistenza.sql.SQLGestoreTabelle;

public class InizializzaDatabase extends ViewAstratta {

	private static final long serialVersionUID = -1011037578517892004L;
	private final static String nomeFileDatabase = "database.db";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + Configurazione.PATH_RELATIVO + "/" + nomeFileDatabase); 
        SQLGestoreTabelle gestore = new SQLGestoreTabelle(ds, new TestoInChiaro());
        
        try {
			resp.getWriter().write("Eliminazione DataBase SQLite pre-esistente...\n");
			gestore.eliminaTabelle();
			
			resp.getWriter().write("Creazione nuovo DataBase SQLite...\n");
			gestore.creaTabelle();
			
			resp.getWriter().write("Inizializzazione informazioni...\n");
			gestore.creaInformazioniIniziali();
			
			resp.getWriter().write("Database Inizializzato\n");
        }
        catch(Exception e) {
        	resp.getWriter().write(e.getMessage());
        }
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
