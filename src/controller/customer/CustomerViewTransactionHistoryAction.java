package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import controller.main.Action;
import databean.CustomerBean;
import databean.DetailedTransactionBean;
import databean.TransactionBean;

public class CustomerViewTransactionHistoryAction extends Action {

	private static final String VIEW_HISTORY_JSP = "customer-view-transaction-history.jsp";
	private static final String NAME = "customer-view-transactions.do";
	private TransactionDAO transactionDAO;
	private Model model;

	public CustomerViewTransactionHistoryAction(Model model) {
		transactionDAO = model.getTransactionDAO();
		this.model = model;
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			CustomerBean customer = (CustomerBean) request.getSession().getAttribute(
			    "customer");

			ArrayList<TransactionBean> transactions = transactionDAO
			    .getTransactionsByCustomerId(customer.getId());

			if (transactions.size() == 0) {
				return VIEW_HISTORY_JSP;
			}

			DetailedTransactionBean[] detailedTransactionBeans = model
			    .getCustomerTransactionHistory(customer.getUserName());
			request.setAttribute("transactions", detailedTransactionBeans);
			if (detailedTransactionBeans == null
			    || detailedTransactionBeans.length == 0) {
				return VIEW_HISTORY_JSP;
			}
			request.setAttribute("transactions", detailedTransactionBeans);

			return VIEW_HISTORY_JSP;
		} catch (RollbackException e) {
			errors.add(e.toString());
			return "customer-result.jsp";
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
