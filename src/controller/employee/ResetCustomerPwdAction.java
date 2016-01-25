package controller.employee;

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

import util.Log;
import controller.main.Action;
import databean.CustomerBean;
import formbean.ChangePwdForm;

public class ResetCustomerPwdAction extends Action {
	private static final String ACTION_NAME = "employee-change-customer-pwd.do";
	private static final String CHANGE_CUSTOMER_PWD_JSP_NAME = "empchangecustpwd.jsp";
	private static final String SUCCESS_JSP_NAME = "employee_success.jsp";
	private static final String TAG = "Change_Customer_Password_Action";
	private FormBeanFactory<ChangePwdForm> formBeanFactory;
	private CustomerDAO customerDAO;

	public ResetCustomerPwdAction(Model model) {
		formBeanFactory = FormBeanFactory.getInstance(ChangePwdForm.class);
		customerDAO = model.getCustomerDAO();
	}

	@Override
	public String getName() {
		return ACTION_NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {

		Log.i(TAG, "change customer pwd");

		List<String> errors = new ArrayList<String>();
		CustomerBean customer = null;
		request.setAttribute("errors", errors);
		try {

			ChangePwdForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			if (request.getSession().getAttribute("employee") == null) {
				errors.add("Missing employee");
				return CHANGE_CUSTOMER_PWD_JSP_NAME;
			}
			if (request.getParameter("userName") == null) {
				errors.add("Missing customer");
				return CHANGE_CUSTOMER_PWD_JSP_NAME;
			} else {
				customer = customerDAO.getCustomerByUserName(request
				    .getParameter("userName"));
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
