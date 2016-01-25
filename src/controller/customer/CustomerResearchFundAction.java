package controller.customer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;
import controller.main.Action;
import databean.CustomerBean;
import databean.FundBean;
import databean.FundPriceBean;
import databean.TransactionBean;
import formbean.BuyFundForm;
import model.CustomerDAO;
import model.FundDAO;
import model.FundPriceDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;
import util.Util;

public class CustomerResearchFundAction extends Action{

	private final String CURRENT_OPERATION_JSP = "Graph.jsp";
	private final String SUCCESS_JSP = "customer_success.jsp";
	
	private FundDAO DAOFund;
	private CustomerDAO DAOCustomer;
	private TransactionDAO DAOTransaction;
	private FormBeanFactory<BuyFundForm> formBeanFactory;
	private FundPriceDAO DAOPrices;
	private Model model;
	public  CustomerResearchFundAction(Model model) {
		this.model = model;
		DAOTransaction = model.getTransactionDAO();	
		DAOFund = model.getFundDAO();
		DAOCustomer = model.getCustomerDAO();
		DAOPrices = model.getFundPriceDAO();
		formBeanFactory = FormBeanFactory.getInstance(BuyFundForm.class);
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "customer_research_fund.do";
	}
	private String converStringDateFormat(String dateStr){
		DateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
		fromFormat.setLenient(false);
		DateFormat toFormat = new SimpleDateFormat("MM/dd/yyyy");
		toFormat.setLenient(false);
		Date date = null;
		try {
			date = fromFormat.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toFormat.format(date);
	}
	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
			String fundIdStr = request.getParameter("fundId");
			//String fundIdStr = "1";
			if (fundIdStr == null 
					|| fundIdStr.length() == 0) {
				errors.add("Please, choose fund to buy it.");
				return "customer-result.jsp";
			}

			int id = Integer.valueOf(fundIdStr);
			FundBean fund = DAOFund.read(id);
			if (fund == null) {
				request.setAttribute("message",
						"No funds");
				return "customer-result.jsp";
			}
			request.setAttribute("fund", fund);
			CustomerBean currentCustomer = (CustomerBean) request.getSession().getAttribute(
					"customer");
			currentCustomer = DAOCustomer.read(currentCustomer.getId());
			request.setAttribute("customer", currentCustomer);

			FundPriceBean[] pricesArr =  DAOPrices.getPrices(id);
			List<FundPriceBean> prices;
			if(pricesArr.length>7) {
				ArrayList<FundPriceBean> arrayList =  new ArrayList<FundPriceBean>(Arrays.asList(pricesArr));
				prices = arrayList.subList(0, 7);				
			}
			else {
				prices = new ArrayList<FundPriceBean>(Arrays.asList(pricesArr));
			}
			/*
			for (FundPriceBean fundPriceBean : prices) {
				fundPriceBean.setPriceDate(converStringDateFormat(fundPriceBean.getPriceDate()));
			}*/
			
			request.setAttribute("prices",prices);
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
