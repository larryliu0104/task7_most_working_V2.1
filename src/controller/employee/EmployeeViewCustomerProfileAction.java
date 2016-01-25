package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanFactory;

import controller.main.Action;
import databean.CustomerBean;
import formbean.SearchCustomerForm;

public class EmployeeViewCustomerProfileAction extends Action {
	private static final String ACTION_NAME = "employee_view_customer_profile.do";
	private static final String VIEW_PROFILE_JSP_NAME = "employee_view_customer_profile.jsp";
	private CustomerDAO customerDAO;
	private FormBeanFactory<SearchCustomerForm> formBeanFactory;

	// private static final String TAG = "EmployeeViewCustomerProfileAction";

	public EmployeeViewCustomerProfileAction(Model model) {
		customerDAO = model.getCustomerDAO();
		// formBeanFactory = FormBeanFactory.getInstance(SearchCustomerForm.class);
	}

	@Override
	public String getName() {
		return ACTION_NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		// SearchCustomerForm form;
		try {
			String userName = request.getParameter("userName");
			if (userName == null) {
				errors.add("Missing user name");
				return "employee-result.jsp";
			}
			// if (!form.isPresent()) {
			// return VIEW_PROFILE_JSP_NAME;
			// }
			CustomerBean customer = customerDAO.getCustomerByUserName(userName);
			if (customer == null) {
				errors.add("Didn't find customer with userName" + userName);
				return "employee-result.jsp";
			}
			// Log.i(TAG, "found customer");
			request.setAttribute("customer", customer);

			return VIEW_PROFILE_JSP_NAME;
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
