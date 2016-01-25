package controller.employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Util;
import controller.main.Action;
import databean.DetailedFundBean;
import formbean.TransactionDayForm;

public class TransactionDayAction extends Action {
	private static final String TRANSACTION_DAY_JSP = "transitionday.jsp";
	private static final String NAME = "employee-transition-day.do";
	private static final String TAG = "Trans day action";
	private FormBeanFactory<TransactionDayForm> formBeanFactory;

	private Model model;

	public TransactionDayAction(Model model) {

		this.model = model;
		formBeanFactory = FormBeanFactory.getInstance(TransactionDayForm.class);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		try {
			request.setAttribute("customer", null);
			request.setAttribute("errors", errors);

			TransactionDayForm form = formBeanFactory.create(request);

			if (!form.isPresent()) {
				DetailedFundBean[] detailFundBeans = model.getDetailFunds();
				String lastDate;
				lastDate = model.getLastTransactionDay();
				request.setAttribute("lastTransactionDay", lastDate);
				request.setAttribute("minDate", increment(lastDate));
				request.setAttribute("funds", detailFundBeans);
				return TRANSACTION_DAY_JSP;
			}

			String newDate = form.getDate();
			if (newDate == null || newDate.length() == 0) {
				DetailedFundBean[] detailFundBeans = model.getDetailFunds();
				String lastDate;
				lastDate = model.getLastTransactionDay();
				request.setAttribute("lastTransactionDay", lastDate);
				request.setAttribute("minDate", increment(lastDate));
				request.setAttribute("funds", detailFundBeans);
				errors.add("please input a new date.");
				return TRANSACTION_DAY_JSP;
			}
			String lastDate = model.getLastTransactionDay();
			if (Util.compareDateStrings(lastDate, newDate) > 0) {
				errors
				    .add("Your input put date should be later than last transaction day "
				        + lastDate);
				request.setAttribute("lastTransactionDay", lastDate);
				request.setAttribute("minDate", increment(lastDate));
				request.setAttribute("funds", model.getDetailFunds());
				return TRANSACTION_DAY_JSP;
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				lastDate = model.getLastTransactionDay();
				request.setAttribute("lastTransactionDay", lastDate);
				request.setAttribute("minDate", increment(lastDate));
				request.setAttribute("funds", model.getDetailFunds());
				return TRANSACTION_DAY_JSP;
			}

			String[] ids = form.getIds();
			String[] prices = form.getPrices();

			Map<String, String> map = new HashMap<String, String>();
			if (ids != null) {
				for (int i = 0; i < ids.length; i++) {
					map.put(ids[i], prices[i]);
				}
			}

			model.processTransaction(map, newDate);
			request.setAttribute("message", "Transaction day updated");
			lastDate = model.getLastTransactionDay();
			request.setAttribute("lastTransactionDay", lastDate);
			request.setAttribute("minDate", increment(lastDate));
			request.setAttribute("funds", model.getDetailFunds());
			return TRANSACTION_DAY_JSP;
		} catch (RollbackException e) {
			e.printStackTrace();
			errors.add(e.toString());
		} catch (FormBeanException e) {
			e.printStackTrace();
			errors.add(e.toString());
		}
		return "employee-result.jsp";
	}

	private String increment(String newDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(newDate));
		} catch (ParseException e) {

		}
		c.add(Calendar.DATE, 1);
		String res = sdf.format(c.getTime());
		return res;
	}

	// private void setNewPrice(TransactionDayForm form, FundPriceDAO priceDAO,
	// Map<String, String> map) throws RollbackException {
	// String date = form.getDate();
	// FundBean[] funds = fundDAO.getFunds();
	// for (FundBean fund : funds) {
	// FundPriceBean price = new FundPriceBean();
	// price.setFundId(fund.getId());
	// Log.i(TAG, "fund id " + fund.getId());
	// System.out.println(map);
	// Log.i(
	// TAG,
	// "new price double type "
	// + Double.parseDouble(map.get(Integer.toString(fund.getId()))));
	// price.setPrice((long) (100 * Double.parseDouble(map.get(Integer
	// .toString(fund.getId())))));
	// price.setPriceDate(date);
	// priceDAO.create(price);
	// }
	//
	// }

	// private void setFunds(HttpServletRequest request, FundBean[] funds)
	// throws RollbackException {
	// DetailedFundBean[] detailedFundBeans = new DetailedFundBean[funds.length];
	// String lastTransactionDay = (String) request
	// .getAttribute("lastTransactionDay");
	// for (int i = 0; i < funds.length; i++) {
	// detailedFundBeans[i] = new DetailedFundBean();
	// detailedFundBeans[i].setFundId(funds[i].getId());
	// detailedFundBeans[i].setFundName(funds[i].getName());
	// detailedFundBeans[i].setTicker(funds[i].getTicker());
	// FundPriceBean price = fundPriceDAO.getCurrentFundPrice(funds[i].getId());
	//
	// detailedFundBeans[i].setPrice(price == null ? "-" : price
	// .getPriceTwoDecimal());
	// detailedFundBeans[i].setDate(price == null ? "-" : price.getPriceDate());
	//
	// Log.i(TAG, "getDate() " + detailedFundBeans[i].getDate());
	// Log.i(TAG, "lastTransactionDay " + lastTransactionDay);
	// if (detailedFundBeans[i].getDate() != null
	// && !detailedFundBeans[i].getDate().equals("-")
	// && Util.compareDateStrings(lastTransactionDay,
	// detailedFundBeans[i].getDate()) < 0) {
	// lastTransactionDay = detailedFundBeans[i].getDate();
	// }
	//
	// }
	// TransactionBean[] transactionBeans = transactionDAO.getTransactions();
	// for (int i = 0; i < transactionBeans.length; i++) {
	// if (transactionBeans[i].getExecuteDate() != null
	// && Util.compareDateStrings(lastTransactionDay,
	// transactionBeans[i].getExecuteDate()) < 0) {
	// lastTransactionDay = transactionBeans[i].getExecuteDate();
	// }
	// }
	//
	// request.setAttribute("funds", detailedFundBeans);
	// if (lastTransactionDay == null || lastTransactionDay.length() == 0) {
	// request.setAttribute("lastTransactionDay", "2016-01-01");
	//
	// request.setAttribute("minDate", "2016-01-02");
	// } else {
	// request.setAttribute("lastTransactionDay", lastTransactionDay);
	// request.setAttribute("minDate", increment(lastTransactionDay));
	//
	// }
	// }

}
