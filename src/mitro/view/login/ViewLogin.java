package mitro.view.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mitro.controller.ControllerFactory;
import mitro.controller.login.Login;
import mitro.exceptions.OperazioneException;
import mitro.view.ViewAstratta;

public class ViewLogin extends ViewAstratta {

	private static final long serialVersionUID = -7227115979946767495L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!apriHomeCorretta(req, resp))
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if(username == null || username.length() == 0) {
			req.setAttribute("error", "Username non valido");
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}
		if(password == null || password.length() == 0) {
			req.setAttribute("error", "Password non valida");
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}
		
		if(apriHomeCorretta(req, resp))
			return;
		Login login = ControllerFactory.getInstance().getLogin();
		try {
			boolean result = login.autentica(username.trim(), password.trim());
			if(!result || !login.getUtenteAutenticato().isPresent()) {
				req.setAttribute("error", "Autenticazione falita");
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
				return;
			}
			req.getSession().setAttribute(nomeAttributoLogin, login);
			apriHomeCorretta(req, resp);
		}
		catch (OperazioneException e) {
			req.setAttribute("error", String.valueOf(e));
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}
	}
	
	private boolean apriHomeCorretta(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(req.getSession().getAttribute(nomeAttributoLogin) != null) {
			Login login = (Login) req.getSession().getAttribute(nomeAttributoLogin);
			try {
				if(login.getUtenteAutenticato().isPresent()) {
					switch(login.getUtenteAutenticato().get().getRuolo()) {
						case AMMINISTRATORE:
							resp.sendRedirect("/Mitro/amministratore");
							return true;
						case GESTORESICUREZZA:
							resp.sendRedirect("/Mitro/log");
							return true;
						case PROFESSORE:
							resp.sendRedirect("/Mitro/professore");
							return true;
						case STUDENTE:
							resp.sendRedirect("/Mitro/studente");
							return true;
						default:
							throw new OperazioneException("Ruolo sconosciuto");
					}
				}
			}
			catch (OperazioneException e) {
				req.setAttribute("error", String.valueOf(e));
			}
		}
		return false;
	}

}
