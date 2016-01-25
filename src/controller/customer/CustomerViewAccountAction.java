package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.FundDAO;
import model.FundPriceDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Log;
import util.Util;
import controller.main.Action;
import databean.CustomerBean;
import databean.FundBean;
import databean.FundPriceBean;
import databean.PositionBean;
import databean.ShareInformationBean;

public class CustomerViewAccountAction extends Action {
	private PositionDAO positionDAO;
	private FundDAO fundDAO;
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundPriceDAO priceDAO;

	public CustomerViewAccountAction(Model model) {
		positionDAO = model.getPositionDAO();
		fundDAO = model.getFundDAO();
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		priceDAO = model.getFundPriceDAO();
	}

	public String getName() {
		return "customer_viewaccount.do";
	}

	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {

			CustomerBean customer = (CustomerBean) request.getSession(false)
			    .getAttribute("customer");
			customer = customerDAO.read(customer.getId());
			double currentAmount = Double.parseDouble(customer.getCashTwoDecimal());
			double pendingAmount = (double) transactionDAO.getPendingAmount(customer
			    .getId()) / 100.0;
			double validAmount = currentAmount - pendingAmount;
			Log.i("Customer View Account Action", "valid Amount " + validAmount);
			Log.i("Customer View Account Action", "current Amount " + currentAmount);
			Log.i("Customer View Account Action", "pending Amount " + pendingAmount);
			request.setAttribute("currentAmount",
			    Util.formatNumber(currentAmount, "###,###,###,###,###,###.00"));
			request.setAttribute("validAmount",
			    Util.formatNumber(validAmount, "###,###,###,###,###,###.00"));
			System.out.println("customer id: " + customer.getId());
			PositionBean[] positionList = positionDAO
			    .getPositionsByCustomerId(customer.getId());
			if (positionList == null) {
				return "customerviewaccount.jsp";
			}
			ShareInformationBean[] shareList = new ShareInformationBean[positionList.length];
			String lastTransactionDay = null;
			for (int i = 0; i < positionList.length; i++) {
				FundBean fund = fundDAO.read(positionList[i].getFundId());
				shareList[i] = new ShareInformationBean();
				shareList[i].setFundId(fund.getId());
				shareList[i].setFundName(fund.getName());
				shareList[i].setFundSymbol(fund.getTicker());
				shareList[i].setShare(positionList[i].getShares());
				FundPriceBean price = priceDAO.getCurrentFundPrice(fund.getId());
				if (price != null) {
					shareList[i].setShareAmount(price.getPrice()
					    * positionList[i].getShares());
					if (lastTransactionDay == null) {
						lastTransactionDay = price.getPriceDate();
						request.setAttribute("lastTransactionDay", lastTransactionDay);
					}
				}

			}
			request.setAttribute("shareList", shareList);

			return "customerviewaccount.jsp";
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
