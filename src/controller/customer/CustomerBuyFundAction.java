package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.FundDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Util;
import controller.main.Action;
import databean.CustomerBean;
import databean.FundBean;
import formbean.BuyFundForm;

public class CustomerBuyFundAction extends Action {

	private final String CURRENT_OPERATION_JSP = "customerbuyfund.jsp";
	private final String SUCCESS_JSP = "customer_success.jsp";

	private FundDAO FundDAO;
	private CustomerDAO customerDAO;

	private FormBeanFactory<BuyFundForm> formBeanFactory;
	private Model model;

	public CustomerBuyFundAction(Model model) {
		this.model = model;
		FundDAO = model.getFundDAO();
		customerDAO = model.getCustomerDAO();
		formBeanFactory = FormBeanFactory.getInstance(BuyFundForm.class);
	}

	@Override
	public String getName() {
		return "customer_buy_fund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
			String fundIdStr = request.getParameter("fundId");
			if (fundIdStr == null || fundIdStr.length() == 0) {
				errors.add("Please, choose fund to buy it.");
				return "customer-result.jsp";
			}

			int id = Integer.valueOf(fundIdStr);
			FundBean fund = FundDAO.read(id);
			if (fund == null) {
				request.setAttribute("message", "No funds");
				return "customer-result.jsp";
			}
			request.setAttribute("fund", fund);
			CustomerBean currentCustomer = (CustomerBean) request.getSession()
			    .getAttribute("customer");
			currentCustomer = customerDAO.read(currentCustomer.getId());
			request.setAttribute("customer", currentCustomer);

			double[] arr = model.getAmount(currentCustomer.getId());
			double currentAmount = arr[0] / 100;
			double validAmount = arr[2] / 100;
			request.setAttribute("currentAmount",
			    Util.formatNumber(currentAmount, "###,###,###,###,###,##0.00"));
			request.setAttribute("validAmount",
			    Util.formatNumber(validAmount, "###,###,###,###,###,##0.00"));
			BuyFundForm form = formBeanFactory.create(request);
			if (!form.isPresent()) {
				return CURRENT_OPERATION_JSP;
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return CURRENT_OPERATION_JSP;
			}

			model.commitBuyTransaction(fund.getId(), form.getAmount(),
			    currentCustomer.getId());
			request.setAttribute("message",
			    "We have recieved your request. Wait until transition day.");
			return SUCCESS_JSP;
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return CURRENT_OPERATION_JSP;
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return CURRENT_OPERATION_JSP;
		} catch (NumberFormatException e) {
			errors.add(e.toString());
			return CURRENT_OPERATION_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}

	}
}
