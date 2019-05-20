package mitro.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.log.LoggerMessaggi;

public abstract class ViewAstratta extends HttpServlet {

	private static final long serialVersionUID = -2194665168250919145L;
	private LoggerMessaggi logger;
	
	public ViewAstratta() {
		this.logger = ControllerFactory.getInstance().getLoggerMessaggi();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	protected void eseguiLogMessaggio(boolean ricezione, String messaggio) {
		logger.scrivi(messaggio);
	}
	
}
