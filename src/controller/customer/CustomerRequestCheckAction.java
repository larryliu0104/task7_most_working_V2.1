package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Util;
import controller.main.Action;
import databean.CustomerBean;
import databean.TransactionBean;
import formbean.RequestCheckForm;

public class CustomerRequestCheckAction extends Action {

	private FormBeanFactory<RequestCheckForm> formBeanFactory;
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private Model model;

	public CustomerRequestCheckAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		formBeanFactory = FormBeanFactory.getInstance(RequestCheckForm.class);
		this.model = model;
	}

	@Override
	public String getName() {
		return "customer-request-check.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
			RequestCheckForm form = formBeanFactory.create(request);
			CustomerBean customer = (CustomerBean) request.getSession().getAttribute(
			    "customer");
			customer = customerDAO.read(customer.getId());
			request.setAttribute("customer", customer);
			double[] arr = model.getAmount(customer.getId());
			double currentAmount = arr[0] / 100;
			double validAmount = arr[2] / 100;
			request.setAttribute("currentAmount",
			    Util.formatNumber(currentAmount, "###,###,###,###,###,##0.00"));
			request.setAttribute("validAmount",
			    Util.formatNumber(validAmount, "###,###,###,###,###,##0.00"));

			if (!form.isPresent()) {
				return "customer-request-check.jsp";
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "customer-request-check.jsp";
			}

			TransactionBean transactionBean = new TransactionBean();
			transactionBean.setCustomerId(customer.getId());
			transactionBean.setAmount((Long.parseLong(form.getAmount())) * 100);
			transactionBean.setTransactionType(Util.getRequestCheck());
			transactionDAO.create(transactionBean);

			request
			    .setAttribute("message",
			        "Thanks, we have accepted your request. Please wait until the Transition Day");
			return "customer_success.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return "customer-request-check.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "customer-request-check.jsp";
		} catch (NumberFormatException e) {
			errors.add(e.toString());
			return "customer-request-check.jsp";
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
