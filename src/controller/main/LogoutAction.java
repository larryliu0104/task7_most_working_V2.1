package controller.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutAction extends Action {

	private static final String NAME = "logout.do";
	private static final String PERFORM_PAGE = "login.jsp";

	public LogoutAction() {
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		return PERFORM_PAGE;
	}
}