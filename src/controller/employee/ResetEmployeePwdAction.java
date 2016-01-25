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
import formbean.ChangePwdForm;

public class ResetEmployeePwdAction extends Action {
	private static final String ACTION_NAME = "employee_change_pwd.do";
	private static final String CHANGE_EMPLOYEE_PWD_JSP_NAME = "empchangepwd.jsp";
	private static final String SUCCESS_JSP_NAME = "employee_success.jsp";
	private static final String TAG = "Change_Employee_Password_Action";
	private FormBeanFactory<ChangePwdForm> formBeanFactory;
	private EmployeeDAO employeeDAO;

	public ResetEmployeePwdAction(Model model) {
		formBeanFactory = FormBeanFactory.getInstance(ChangePwdForm.class);
		employeeDAO = model.getEmployeeDAO();
	}

	@Override
	public String getName() {

		return ACTION_NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {
		Log.i(TAG, "change employee pwd");

		List<String> errors = new ArrayList<String>();
		EmployeeBean employee = null;
		request.setAttribute("errors", errors);
		request.getSession().setAttribute("customer", null);

		try {

			ChangePwdForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			if (request.getSession().getAttribute("employee") == null) {
				errors.add("Missing employee");
				return CHANGE_EMPLOYEE_PWD_JSP_NAME;
			} else {
				employee = (EmployeeBean) request.getSession().getAttribute("employee");
			}
			if (!form.isPresent()) {
				return CHANGE_EMPLOYEE_PWD_JSP_NAME;
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return CHANGE_EMPLOYEE_PWD_JSP_NAME;
			}

			String userName = employee.getUserName();
			employeeDAO.setPassword(userName, form.getNewPassword());
			request.getSession().setAttribute("employee",
			    employeeDAO.getEmployeeByUserName(userName));
			request.setAttribute("message", "Employee " + employee.getUserName()
			    + "'s password is changed successfully!");
			return SUCCESS_JSP_NAME;
		} catch (RollbackException e) {
			errors.add(e.getMessage());

			return CHANGE_EMPLOYEE_PWD_JSP_NAME;
		} catch (FormBeanException e) {
			errors.add(e.getMessage());

			return CHANGE_EMPLOYEE_PWD_JSP_NAME;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
