package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.main.Action;
import databean.CustomerBean;
import formbean.ChangePwdForm;

public class ChangeCustomerPasswordAction extends Action {
	private static final String ACTION_NAME = "customer_change_password.do";
	private static final String CHANGE_CUSTOMER_PWD_JSP_NAME = "customerchangepwd.jsp";
	private static final String SUCCESS_JSP_NAME = "customer_success.jsp";
	private FormBeanFactory<ChangePwdForm> formBeanFactory;
	private CustomerDAO customerDAO;

	public ChangeCustomerPasswordAction(Model model) {
		formBeanFactory = FormBeanFactory.getInstance(ChangePwdForm.class);
		customerDAO = model.getCustomerDAO();
	}

	@Override
	public String getName() {
		return ACTION_NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		CustomerBean customer = null;
		request.setAttribute("errors", errors);
		try {

			ChangePwdForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			if (request.getSession().getAttribute("customer") == null) {
				errors.add("Missing customer");
				return CHANGE_CUSTOMER_PWD_JSP_NAME;

			} else {
				customer = (CustomerBean) request.getSession().getAttribute("customer");

				if (customer == null) {
					errors.add("Missing customer user name "
					    + request.getParameter("userName"));
					return CHANGE_CUSTOMER_PWD_JSP_NAME;
				}
				HttpSession session = request.getSession();
				session.setAttribute("customer", customer);

			}
			if (!form.isPresent()) {
				return CHANGE_CUSTOMER_PWD_JSP_NAME;
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return CHANGE_CUSTOMER_PWD_JSP_NAME;
			}

			String userName = customer.getUserName();
			customerDAO.setPassword(userName, form.getNewPassword());
			request.getSession().setAttribute("customer",
			    customerDAO.getCustomerByUserName(userName));
			request.setAttribute("message", "Customer " + customer.getUserName()
			    + "'s password is changed successfully");
			return SUCCESS_JSP_NAME;
		} catch (RollbackException e) {
			errors.add(e.getMessage());

			return CHANGE_CUSTOMER_PWD_JSP_NAME;
		} catch (FormBeanException e) {
			errors.add(e.getMessage());

			return CHANGE_CUSTOMER_PWD_JSP_NAME;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
