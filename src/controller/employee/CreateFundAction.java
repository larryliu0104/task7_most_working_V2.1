package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FundDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Log;
import controller.main.Action;
import databean.FundBean;
import formbean.CreateFundForm;

public class CreateFundAction extends Action {
	private static final String ACTION_NAME = "employee_create_fund.do";
	private static final String CREATE_FUND_JSP_NAME = "create_fund.jsp";
	private static final String SUCCESS_JSP_NAME = "employee_success.jsp";
	private static final String TAG = "Create_Fund_Action";
	private FormBeanFactory<CreateFundForm> formBeanFactory;
	private FundDAO fundDAO;

	public CreateFundAction(Model model) {
		formBeanFactory = FormBeanFactory.getInstance(CreateFundForm.class);
		fundDAO = model.getFundDAO();
	}

	@Override
	public String getName() {
		return ACTION_NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {
		Log.i(TAG, "create fund");

		List<String> errors = new ArrayList<String>();

		request.setAttribute("errors", errors);
		request.getSession().setAttribute("customer", null);
		try {
			CreateFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			if (!form.isPresent()) {
				return CREATE_FUND_JSP_NAME;
			}
			if (fundDAO.isFundExistedByName(form.getName())) {
				errors.add("Fund name " + form.getName() + " exists");
			}
			if (fundDAO.isFundExistedByTicker(form.getTicker())) {
				errors.add("Fund ticker " + form.getTicker() + " exists");
			}

			errors.addAll(form.getValidationErrors());

			if (errors.size() != 0) {
				return CREATE_FUND_JSP_NAME;
			}

			FundBean fund = new FundBean();
			fund.setName(form.getName());
			fund.setTicker(form.getTicker());
			fundDAO.create(fund);

			request.setAttribute("message", "Fund " + fund.getName()
			    + " is created successfully");
			return SUCCESS_JSP_NAME;
		} catch (RollbackException e) {
			errors.add(e.getMessage());

			return CREATE_FUND_JSP_NAME;
		} catch (FormBeanException e) {
			errors.add(e.getMessage());

			return CREATE_FUND_JSP_NAME;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

}
