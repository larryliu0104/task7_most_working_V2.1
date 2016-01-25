package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.main.Action;
import databean.CustomerBean;
import formbean.DepositCheckForm;

public class DepositCheckAction extends Action {

	private static final String FUND_TRANSACTION_JSP = "employee-deposit-check.jsp";
	public static final String NAME = "employee-deposit-check.do";
	private FormBeanFactory<DepositCheckForm> formBeanFactory;
	private CustomerDAO customerDAO;
	private Model model;

	public DepositCheckAction(Model model) {
		customerDAO = model.getCustomerDAO();
		formBeanFactory = FormBeanFactory.getInstance(DepositCheckForm.class);
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
				errors.add("Didn't find customer with user name " + userName);
				return "employee-result.jsp";
			}
			request.getSession().setAttribute("customer", customer);
			DepositCheckForm form = formBeanFactory.create(request);
			if (!form.isPresent()) {
				return FUND_TRANSACTION_JSP;
			}
			errors.addAll(form.getValidationErrors());
			if (Double.parseDouble(form.getAmount()) < 0) {
				errors.add("please input positive number");
			}
			if (errors.size() != 0) {
				return FUND_TRANSACTION_JSP;
			}

			model.commitDepositCheckTransaction(userName, form.getAmount());
			request.setAttribute("message", "You have deposit $" + form.getAmount()
			    + " to user " + userName);
			return "employee-result.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());

			return FUND_TRANSACTION_JSP;
		} catch (RollbackException e) {

			errors.add(e.toString());
			return FUND_TRANSACTION_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
