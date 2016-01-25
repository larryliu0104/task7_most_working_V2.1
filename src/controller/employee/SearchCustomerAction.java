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
import formbean.SearchCustomerForm;

public class SearchCustomerAction extends Action {

	private static final String SEARCHCUSTOMER_JSP = "searchcustomer.jsp";
	public static final String NAME = "employee_search_customer.do";

	private FormBeanFactory<SearchCustomerForm> formBeanFactory;
	private CustomerDAO customerDAO;

	public SearchCustomerAction(Model model) {
		customerDAO = model.getCustomerDAO();
		formBeanFactory = FormBeanFactory.getInstance(SearchCustomerForm.class);

	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		SearchCustomerForm form;
		try {
			CustomerBean[] customerBeans = customerDAO.getAllCustomers();
			request.setAttribute("customers", customerBeans);
			form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			if (!form.isPresent()) {
				return SEARCHCUSTOMER_JSP;
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() > 0) {
				return SEARCHCUSTOMER_JSP;
			}

			CustomerBean[] customers = customerDAO.getCustomersByKeyword(form
			    .getKeyword());
			request.setAttribute("customers", customers);
			return SEARCHCUSTOMER_JSP;
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "employee-result.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());

			return "employee-result.jsp";
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}

	}
}
