package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.EmployeeDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Log;
import controller.main.Action;
import databean.EmployeeBean;
import formbean.CreateEmployeeForm;

public class CreateEmployeeAccountAction extends Action {
	private static final String ACTION_NAME = "employee_register.do";
	private static final String CREATE_EMPLOYEE_JSP_NAME = "createemployee.jsp";
	private static final String SUCCESS_JSP_NAME = "employee_success.jsp";
	private static final String TAG = "Create_Employee_Account_Action";
	private FormBeanFactory<CreateEmployeeForm> formBeanFactory;
	private EmployeeDAO employeeDAO;

	public CreateEmployeeAccountAction(Model model) {
		formBeanFactory = FormBeanFactory.getInstance(CreateEmployeeForm.class);
		employeeDAO = model.getEmployeeDAO();
	}

	@Override
	public String getName() {
		return ACTION_NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {
		Log.i(TAG, "perform create employee account");
		request.getSession().setAttribute("customer", null);
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {

			CreateEmployeeForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if (!form.isPresent()) {
				return CREATE_EMPLOYEE_JSP_NAME;
			}

			errors.addAll(form.getValidationErrors());
			if (employeeDAO.isEmployExistedByUserName(form.getUserName())) {
				errors.add("employee user name " + form.getUserName() + " exits");
			}
			if (errors.size() != 0) {
				return CREATE_EMPLOYEE_JSP_NAME;
			}

			EmployeeBean employee = new EmployeeBean();
			employee.setUserName(form.getUserName());
			employee.setFirstName(form.getFirstName());
			employee.setLastName(form.getLastName());
			employee.setPassword(form.getPassword());
			employeeDAO.create(employee);
			request.setAttribute("message", "Employee " + employee.getUserName()
			    + " is created successfully.");
			return SUCCESS_JSP_NAME;
		} catch (RollbackException e) {
			errors.add(e.getMessage());

			return CREATE_EMPLOYEE_JSP_NAME;
		} catch (FormBeanException e) {
			errors.add(e.getMessage());

			return CREATE_EMPLOYEE_JSP_NAME;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

}
