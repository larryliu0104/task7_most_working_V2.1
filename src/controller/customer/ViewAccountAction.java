package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.FundDAO;
import model.FundPriceDAO;
import model.Model;
import model.PositionDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import controller.main.Action;
import databean.CustomerBean;
import databean.FundBean;
import databean.FundPriceBean;
import databean.PositionBean;
import databean.ShareInformationBean;

public class ViewAccountAction extends Action {

	private static final String CUSTOMER_VIEW_ACCOUNT_JSP = "customerviewaccount.jsp";
	public static final String NAME = "customer_viewaccount.do";
	private PositionDAO positionDAO;
	private FundDAO fundDAO;
	private CustomerDAO customerDAO;
	private FundPriceDAO priceDAO;

	public ViewAccountAction(Model model) {
		customerDAO = model.getCustomerDAO();
		priceDAO = model.getFundPriceDAO();
		positionDAO = model.getPositionDAO();
		fundDAO = model.getFundDAO();
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			CustomerBean currentCustomer = (CustomerBean) request.getSession(false)
			    .getAttribute("customer");
			currentCustomer = customerDAO.read(currentCustomer.getId());
			PositionBean[] positionList = positionDAO
			    .getPositionsByCustomerId(currentCustomer.getId());
			ShareInformationBean[] shares = new ShareInformationBean[positionList.length];
			for (int i = 0; i < positionList.length; i++) {
				FundBean fund = fundDAO.read(positionList[i].getId());
				shares[i] = new ShareInformationBean();
				shares[i].setFundId(fund.getId());
				shares[i].setFundName(fund.getName());
				shares[i].setFundSymbol(fund.getTicker());
				shares[i].setShare(positionList[i].getShares());
				FundPriceBean price = priceDAO.getCurrentFundPrice(fund.getId());
				if (price != null) {
					shares[i].setShareAmount(price.getPrice()
					    * positionList[i].getShares());
				}
			}
			request.setAttribute("shareList", shares);
			return CUSTOMER_VIEW_ACCOUNT_JSP;
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "properJSP";
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
