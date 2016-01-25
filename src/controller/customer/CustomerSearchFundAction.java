package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FundDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.main.Action;
import databean.FundBean;
import formbean.SearchFundForm;

public class CustomerSearchFundAction extends Action {

	private static final String SEARCH_FUND = "customer-search-fund.jsp";
	public static final String CUSTOMER_NAME = "customer_search_fund.do";
	private FormBeanFactory<SearchFundForm> formBeanFactory;

	private FundDAO fundDAO;

	public CustomerSearchFundAction(Model model) {
		fundDAO = model.getFundDAO();
		formBeanFactory = FormBeanFactory.getInstance(SearchFundForm.class);

	}

	public String getName() {
		return CUSTOMER_NAME;
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		SearchFundForm form;
		try {
			form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if (!form.isPresent()) {
				FundBean[] funds = fundDAO.getFunds();
				request.setAttribute("funds", funds);
				return SEARCH_FUND;
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return SEARCH_FUND;
			}
			return SEARCH_FUND;
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "customer-result.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "customer-result.jsp";
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
