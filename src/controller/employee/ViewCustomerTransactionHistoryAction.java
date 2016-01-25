package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Log;
import controller.main.Action;
import databean.CustomerBean;
import databean.DetailedTransactionBean;

public class ViewCustomerTransactionHistoryAction extends Action {
	private static final String VIEW_HISTORY_JSP = "emp-view-transaction-history.jsp";
	private static final String NAME = "employee-view-transactions.do";
	private static final String TAG = "View customer trans history";
	private CustomerDAO customerDAO;
	private Model model;

	public ViewCustomerTransactionHistoryAction(Model model) {
		customerDAO = model.getCustomerDAO();
		this.model = model;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			String userName = request.getParameter("userName");
			if (userName == null) {
				errors.add("Missing user name");
				return "employee-result.jsp";
			}

			CustomerBean customer = customerDAO.getCustomerByUserName(userName);
			if (customer == null) {
				errors.add("Didn't find customer with userName" + userName);
				return "employee-result.jsp";
			}
			Log.i(TAG, "found customer");
			request.setAttribute("customer", customer);

			DetailedTransactionBean[] detailedTransactionBeans = model
			    .getCustomerTransactionHistory(userName);
			request.setAttribute("transactions", detailedTransactionBeans);
			if (detailedTransactionBeans == null
			    || detailedTransactionBeans.length == 0) {

				return VIEW_HISTORY_JSP;
			}

			return VIEW_HISTORY_JSP;
		} catch (RollbackException e) {
			errors.add(e.toString());
			return "employee-result.jsp";
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
