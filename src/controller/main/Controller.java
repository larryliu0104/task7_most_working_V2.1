package controller.main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

import org.genericdao.DAOException;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Log;
import controller.customer.ChangeCustomerPasswordAction;
import controller.customer.CustomerBuyFundAction;
import controller.customer.CustomerRequestCheckAction;
import controller.customer.CustomerResearchFundAction;
import controller.customer.CustomerSearchFundAction;
import controller.customer.CustomerSellFundAction;
import controller.customer.CustomerViewAccountAction;
import controller.customer.CustomerViewTransactionHistoryAction;
import controller.employee.CreateCustomerAccountAction;
import controller.employee.CreateEmployeeAccountAction;
import controller.employee.CreateFundAction;
import controller.employee.DepositCheckAction;
import controller.employee.EmployeeViewCustomerProfileAction;
import controller.employee.ResetCustomerPwdAction;
import controller.employee.ResetEmployeePwdAction;
import controller.employee.SearchCustomerAction;
import controller.employee.TransactionDayAction;
import controller.employee.ViewCustomerTransactionHistoryAction;
import databean.CustomerBean;
import databean.EmployeeBean;

public class Controller extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1561542386870494042L;
	private static final String TAG = "Controller";

	public void init() throws ServletException {

		Model model;
		try {
			model = new Model(getServletConfig());

			try {
				if (model.getEmployeeDAO().getCount() == 0) {
					createAdminAccount(model);
				}
			} catch (RollbackException e) {
				e.printStackTrace();
			} finally {
				if (Transaction.isActive()) {
					Transaction.rollback();
				}
			}

			// admin
			Action.add(new ResetEmployeePwdAction(model));
			Action.add(new ResetCustomerPwdAction(model));
			Action.add(new CreateCustomerAccountAction(model));
			Action.add(new CreateEmployeeAccountAction(model));
			Action.add(new CreateFundAction(model));
			Action.add(new SearchCustomerAction(model));
			Action.add(new DepositCheckAction(model));
			Action.add(new ViewCustomerTransactionHistoryAction(model));
			Action.add(new EmployeeViewCustomerProfileAction(model));

			// customer
			Action.add(new CustomerSearchFundAction(model));
			Action.add(new CustomerSellFundAction(model));
			Action.add(new CustomerViewAccountAction(model));
			Action.add(new CustomerViewTransactionHistoryAction(model));
			Action.add(new TransactionDayAction(model));
			Action.add(new CustomerRequestCheckAction(model));
			Action.add(new CustomerBuyFundAction(model));
			Action.add(new ChangeCustomerPasswordAction(model));
			Action.add(new CustomerResearchFundAction(model));

			// logout
			Action.add(new LoginAction(model));
			Action.add(new LogoutAction());
		} catch (DAOException e) {

			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

		String servletPath = request.getServletPath();
		String action = getActionName(servletPath);
		Log.i("perform action ", action);

		HttpSession session = request.getSession(true);
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		String nextPage = "";
		if (action.startsWith("customer")) {
			if (customer == null) {
				nextPage = "login.jsp";
				Log.i("no customer in session ", nextPage);
			} else {
				nextPage = Action.perform(action, request);
				Log.i("valid customer is session ", nextPage);
			}
		} else if (action.startsWith("employee")) {
			if (employee == null) {
				Log.i("no employee is in session ", nextPage);
				nextPage = "login.jsp";
			} else {
				Log.i("valid employee is in session " + employee.getUserName(),
				    nextPage);
				nextPage = Action.perform(action, request);
			}
		} else {
			Log.i("action doesn't begin with employee or customer ", nextPage);
			nextPage = Action.perform(action, request);
		}
		Log.i("next page ", nextPage);
		sendToNextPage(nextPage, request, response);

	}

	private void sendToNextPage(String nextPage, HttpServletRequest request,
	    HttpServletResponse response) throws IOException, ServletException {
		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
			    request.getServletPath());
			return;
		}
		if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
		}
		if (nextPage.endsWith(".jsp")) {
			RequestDispatcher d = request.getRequestDispatcher("WEB-INF/" + nextPage);
			d.forward(request, response);
			return;
		}
		if (nextPage.startsWith("http://")) {
			response.sendRedirect(nextPage);
			return;
		}

		Log.e(TAG, "next page is " + nextPage);
		// RequestDispatcher d = request.getRequestDispatcher("WEB-INF/" +
		// nextPage);
		// d.forward(request, response);
		throw new ServletException(Controller.class.getName()
		    + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");

	}

	private String getActionName(String path) {
		int slash = path.lastIndexOf('/');
		return path.substring(slash + 1);
	}

	private void createAdminAccount(Model model) throws RollbackException {
		EmployeeBean admin = new EmployeeBean();
		admin.setUserName("admin");
		admin.setFirstName("Cindy");
		admin.setLastName("Li");
		admin.setPassword("hi");
		model.getEmployeeDAO().create(admin);
		CustomerBean custom = new CustomerBean();
		custom.setUserName("larry");
		custom.setFirstName("larry");
		custom.setLastName("Liu");
		custom.setPassword("233333");
		model.getCustomerDAO().create(custom);
	}

}
