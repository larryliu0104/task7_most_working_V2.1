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
import formbean.CreateCustomerForm;

public class CreateCustomerAccountAction extends Action {
	private static final String ACTION_NAME = "employee_create_customer.do";
	private static final String CREATE_CUSTOMER_JSP_NAME = "createcustomer.jsp";
	private static final String SUCCESS_JSP_NAME = "employee_success.jsp";
	private static final String TAG = "Create_Customer_Account_Action";
	private FormBeanFactory<CreateCustomerForm> formBeanFactory;
	private CustomerDAO customerDAO;

	public CreateCustomerAccountAction(Model model) {
		formBeanFactory = FormBeanFactory.getInstance(CreateCustomerForm.class);
		customerDAO = model.getCustomerDAO();
	}

	@Override
	public String getName() {
		return ACTION_NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {

		Log.i(TAG, "perform create customer account");

		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession();
		session.setAttribute("customer", null);
		request.setAttribute("errors", errors);

		try {
			CreateCustomerForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if (!form.isPresent()) {
				return CREATE_CUSTOMER_JSP_NAME;
			}

			errors.addAll(form.getValidationErrors());

			if (customerDAO.isCustomerExistedByUserName(form.getUserName())) {
				errors.add("customer user name " + form.getUserName() + " exists");
			}

			if (errors.size() != 0) {
				return CREATE_CUSTOMER_JSP_NAME;
			}

			CustomerBean customer = new CustomerBean();
			customer.setUserName(form.getUserName());
			customer.setFirstName(form.getFirstName());
			customer.setLastName(form.getLastName());
			customer.setAddrLine1(form.getAddressLine1());
			customer.setAddrLine2(form.getAddressLine2());
			customer.setCity(form.getCity());
			customer.setState(form.getState());
			customer.setPassword(form.getPassword());
			customer.setZip(form.getZip());
			customerDAO.create(customer);
			request.setAttribute("message", "Customer " + customer.getUserName()
			    + " is created successfully.");
			return SUCCESS_JSP_NAME;
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return CREATE_CUSTOMER_JSP_NAME;
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return CREATE_CUSTOMER_JSP_NAME;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
