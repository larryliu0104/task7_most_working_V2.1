package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Log;
import util.Util;
import databean.CustomerBean;
import databean.DetailedFundBean;
import databean.DetailedTransactionBean;
import databean.FundBean;
import databean.FundPriceBean;
import databean.PositionBean;
import databean.TransactionBean;

public class Model {
	private CustomerDAO customerDAO;
	private EmployeeDAO employeeDAO;
	private FundDAO fundDAO;
	private FundPriceDAO fundPriceDAO;
	private PositionDAO positionDAO;
	private TransactionDAO transactionDAO;
	private static final String TAG = "Model";

	public Model(ServletConfig config) throws ServletException, DAOException {
		String jdbcDriver = config.getInitParameter("jdbcDriverName");
		String jdbcURL = config.getInitParameter("jdbcURL");
		ConnectionPool pool = null;
		if ("\\".equals(File.separator)) {
			pool = new ConnectionPool(jdbcDriver, jdbcURL, "root", "");
		} else {
			pool = new ConnectionPool(jdbcDriver, jdbcURL);
		}
		customerDAO = new CustomerDAO(pool, "Customer");
		employeeDAO = new EmployeeDAO(pool, "Employee");
		fundDAO = new FundDAO(pool, "Fund");
		fundPriceDAO = new FundPriceDAO(pool, "Fund_Price_History");
		positionDAO = new PositionDAO(pool, "Position");
		transactionDAO = new TransactionDAO(pool, "Transaction");

	}

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	public FundDAO getFundDAO() {
		return fundDAO;
	}

	public void setFundDAO(FundDAO fundDAO) {
		this.fundDAO = fundDAO;
	}

	public FundPriceDAO getFundPriceDAO() {
		return fundPriceDAO;
	}

	public void setFundPriceDAO(FundPriceDAO fundPriceDAO) {
		this.fundPriceDAO = fundPriceDAO;
	}

	public PositionDAO getPositionDAO() {
		return positionDAO;
	}

	public void setPositionDAO(PositionDAO positionDAO) {
		this.positionDAO = positionDAO;
	}

	public TransactionDAO getTransactionDAO() {
		return transactionDAO;
	}

	public void setTransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	public double[] getShare(int customerId, int fundId) throws RollbackException {
		try {
			Transaction.begin();
			PositionBean position = positionDAO.getPosition(customerId, fundId);
			double currentShare = position == null ? 0 : position.getShares();
			double pendingShare = transactionDAO.getPendingShare(customerId, fundId);
			double validShare = currentShare - pendingShare;
			double[] values = new double[3];
			values[0] = currentShare;
			values[1] = pendingShare;
			values[2] = validShare;
			Transaction.commit();
			return values;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

	public double[] getAmount(int customerId) throws RollbackException {
		try {
			Transaction.begin();
			CustomerBean customer = customerDAO.read(customerId);
			double currentAmount = customer.getCash();
			double pendingAmount = transactionDAO.getPendingAmount(customer.getId());
			double validAmount = currentAmount - pendingAmount;
			double[] values = new double[3];
			values[0] = currentAmount;
			values[1] = pendingAmount;
			values[2] = validAmount;
			Transaction.commit();
			return values;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

	public void processTransaction(Map<String, String> map, String newDate)
	    throws RollbackException {

		try {
			Transaction.begin();
			if (Util.compareDateStrings(newDate, getLastTransactionDay()) <= 0) {
				Transaction.rollback();
				return;
			}
			updatePrices(map, newDate);
			TransactionBean[] transactions = transactionDAO.getTransactions();
			for (int i = 0; i < transactions.length; i++) {
				CustomerBean customerBean = customerDAO.getCustomerById(transactions[i]
				    .getCustomerId());
				if (transactions[i].getTransactionType().equals(Util.getBuyFund())) {
					proccessBuyFund(transactions[i], newDate, customerBean);
				}
				if (transactions[i].getTransactionType().equals(Util.getSellFund())) {
					proccessSellFund(transactions[i], newDate, customerBean);
				}
				if (transactions[i].getTransactionType().equals(Util.getRequestCheck())) {
					proccessRequestCheck(transactions[i], newDate, customerBean);
				}
				if (transactions[i].getTransactionType().equals(Util.getDepositCheck())) {
					Log.i(TAG, "process deposit check");
					proccessDepositCheck(transactions[i], newDate, customerBean);
				}
			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

	private void updatePrices(Map<String, String> map, String date)
	    throws RollbackException {

		FundBean[] funds = fundDAO.getFunds();
		for (FundBean fund : funds) {
			FundPriceBean price = new FundPriceBean();
			price.setFundId(fund.getId());
			price.setPrice((long) (100 * Double.parseDouble(map.get(Integer
			    .toString(fund.getId())))));
			price.setPriceDate(date);
			fundPriceDAO.create(price);
		}
	}

	private void proccessRequestCheck(TransactionBean transaction,
	    String newDate, CustomerBean customer) throws RollbackException {
		customer.setCash(customer.getCash() - transaction.getAmount());
		customerDAO.update(customer);
		transaction.setExecuteDate(newDate);
		transactionDAO.update(transaction);
	}

	private void proccessDepositCheck(TransactionBean transaction,
	    String newDate, CustomerBean customer) throws RollbackException {
		customer.setCash(customer.getCash() + transaction.getAmount());
		customerDAO.update(customer);
		transaction.setExecuteDate(newDate);
		transactionDAO.update(transaction);
	}

	private void proccessSellFund(TransactionBean transaction, String newDate,
	    CustomerBean customer) throws RollbackException {
		int fundId = transaction.getFundId();
		long soldShares = transaction.getShares();
		long myShares = positionDAO.getPosition(customer.getId(), fundId)
		    .getShares();
		long fundPrice = fundPriceDAO.getCurrentFundPrice(fundId).getPrice();
		customer
		    .setCash(customer.getCash() + (transaction.getShares() * fundPrice));
		// update customer cash
		customerDAO.update(customer);

		// update transaction
		int customerId = transaction.getCustomerId();
		transaction.setPrice(fundPrice);
		transaction.setAmount(transaction.getShares() * fundPrice / 1000);
		transaction.setExecuteDate(newDate);
		transactionDAO.update(transaction);

		// update position
		PositionBean positionBean = positionDAO.getPosition(customerId, fundId);
		if (positionBean != null) {
			if (myShares - soldShares != 0) {
				positionBean.setShares(myShares - soldShares);
				positionDAO.update(positionBean);
			} else {
				positionDAO.delete(positionBean.getId());
			}
		}

	}

	private void proccessBuyFund(TransactionBean transaction, String newDate,
	    CustomerBean customerBean) throws RollbackException {

		customerBean.setCash(customerBean.getCash() - transaction.getAmount());
		// update customer cash
		customerDAO.update(customerBean);
		// update transaction
		int customerId = transaction.getCustomerId();
		int fundId = transaction.getFundId();
		long fundPrice = fundPriceDAO.getCurrentFundPrice(fundId).getPrice();
		long shares = transaction.getAmount() / fundPrice * 1000;

		transaction.setPrice(fundPrice);
		transaction.setShares(shares);
		transaction.setExecuteDate(newDate);
		transactionDAO.update(transaction);

		// update position
		PositionBean positionBean = positionDAO.getPosition(customerId, fundId);
		if (positionBean != null) {
			positionBean.setShares(shares + positionBean.getShares());
			positionDAO.update(positionBean);
		} else {
			positionBean = new PositionBean();
			positionBean.setCustomerId(customerId);
			positionBean.setFundId(fundId);
			positionBean.setShares(shares);
			positionDAO.createAutoIncrement(positionBean);
		}
	}

	public void commitBuyTransaction(int fundId, String amount, int customerId)
	    throws RollbackException {
		try {
			Transaction.begin();
			TransactionBean transactionBean = new TransactionBean();
			transactionBean.setFundId(fundId);
			transactionBean.setCustomerId(customerId);
			Long a = Long.valueOf(amount);
			transactionBean.setAmount((long) (a) * 100);
			transactionBean.setTransactionType(Util.getBuyFund());
			Log.i("Model", "Buy");
			CustomerBean customer = customerDAO.read(transactionBean.getCustomerId());
			double currentAmount = customer.getCash();
			double pendingAmount = transactionDAO.getPendingAmount(customer.getId());
			double validAmount = currentAmount - pendingAmount;
			if (validAmount < transactionBean.getAmount()) {
				Log.i(TAG, "NOT VALID AMOUT");
				Transaction.rollback();
				throw new RollbackException("Not enough money");
			}
			Log.i(TAG, "before create transaction");
			transactionDAO.create(transactionBean);
			Log.i(TAG, "after create transaction");
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

	public void createTransaction(TransactionBean transaction)
	    throws RollbackException {
		try {
			Transaction.begin();
			if (Util.getRequestCheck().equals(transaction.getTransactionType())) {
				Log.i("Model", "Hahaha, request check");
				CustomerBean customer = customerDAO.read(transaction.getCustomerId());
				double currentAmount = customer.getCash();
				double pendingAmount = transactionDAO
				    .getPendingAmount(customer.getId());
				double validAmount = currentAmount - pendingAmount;

				if (validAmount < transaction.getAmount()) {
					throw new RollbackException("Not enough money");
				}
				Log.i(TAG, "before create transaction");
				transactionDAO.create(transaction);
			} else if (Util.getSellFund().equals(transaction.getTransactionType())) {

				Log.e("Model", "hahaha, sell fund");
				CustomerBean customer = customerDAO.read(transaction.getCustomerId());
				PositionBean position = positionDAO.getPosition(customer.getId(),
				    transaction.getFundId());
				double currentShare = position.getShares();
				double pendingShare = transactionDAO.getPendingShare(customer.getId(),
				    transaction.getFundId());
				double validShare = currentShare - pendingShare;

				if (validShare < transaction.getShares()) {
					throw new RollbackException("Not enough share");
				}
				transactionDAO.create(transaction);
			} else if (Util.getDepositCheck()
			    .equals(transaction.getTransactionType())) {
				transactionDAO.create(transaction);

			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

	//
	// public void commitTransaction(TransactionBean transaction)
	// throws RollbackException {
	// synchronized (amountLock) {
	// PositionBean position = positionDAO.getPosition(
	// transaction.getCustomerId(), transaction.getFundId());
	// if (position == null) {
	// throw new RollbackException("illegal fund id");
	// }
	// double currentShare = position.getShares();
	// double pendingShare = transactionDAO.getPendingAmount(transaction
	// .getCustomerId());
	// double validShare = currentShare - pendingShare;
	// System.out.println("position:" + position.getId());
	// System.out.println("CurrentShare: " + currentShare + "\npendingShare: "
	// + pendingShare);
	// if (validShare < transaction.getShares() / 1000) {
	// System.out.println("validShare: " + validShare
	// + "\nTransaction shares: " + transaction.getShares());
	// throw new RollbackException(
	// "There is not enough shares in your account.");
	// }
	// transactionDAO.create(transaction);
	// }
	// }

	public String getLastTransactionDay() throws RollbackException {
		String lastTransactionDay = null;
		TransactionBean[] transactions = transactionDAO.getTransactions();
		if (transactions != null) {
			for (int i = 0; i < transactions.length; i++) {
				if (transactions[i].getExecuteDate() != null
				    && !transactions[i].getExecuteDate().equals("-")) {
					if (lastTransactionDay == null) {
						lastTransactionDay = transactions[i].getExecuteDate();
					} else if (Util.compareDateStrings(lastTransactionDay,
					    transactions[i].getExecuteDate()) < 0) {
						lastTransactionDay = transactions[i].getExecuteDate();
					}
				}
			}
		}
		FundPriceBean[] fundPriceBeans = fundPriceDAO.getAllPrices();
		if (fundPriceBeans != null) {
			for (int i = 0; i < fundPriceBeans.length; i++) {
				if (fundPriceBeans[i].getPriceDate() != null
				    && !fundPriceBeans[i].getPriceDate().equals("-")) {
					if (lastTransactionDay == null) {
						lastTransactionDay = fundPriceBeans[i].getPriceDate();
					} else if (Util.compareDateStrings(lastTransactionDay,
					    fundPriceBeans[i].getPriceDate()) < 0) {
						lastTransactionDay = fundPriceBeans[i].getPriceDate();
					}
				}
			}
		}

		if (lastTransactionDay == null) {
			lastTransactionDay = "2016-01-01";
		}
		return lastTransactionDay;

	}

	public void commitDepositCheckTransaction(String userName, String amount)
	    throws RollbackException {
		try {
			Transaction.begin();

			CustomerBean customer = customerDAO.getCustomerByUserName(userName);
			if (customer == null) {
				Transaction.rollback();

			}
			TransactionBean transactionBean = new TransactionBean();
			transactionBean.setCustomerId(customer.getId());
			transactionBean.setAmount(((long) (100 * Double.parseDouble(amount))));
			transactionBean.setTransactionType(Util.getDepositCheck());
			transactionDAO.create(transactionBean);
			Transaction.commit();

		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}

	public DetailedTransactionBean[] getCustomerTransactionHistory(String userName)
	    throws RollbackException {
		DetailedTransactionBean[] detailedTransactionBeans = null;
		try {
			Transaction.begin();
			CustomerBean customer = customerDAO.getCustomerByUserName(userName);
			ArrayList<TransactionBean> transactions = transactionDAO
			    .getTransactionsByCustomerId(customer.getId());
			detailedTransactionBeans = new DetailedTransactionBean[transactions
			    .size()];
			Log.i(TAG, "transaction length " + transactions.size());
			for (int i = 0; i < transactions.size(); i++) {
				DetailedTransactionBean detail = new DetailedTransactionBean();
				TransactionBean transaction = transactions.get(i);
				detail.setTransactionType(transaction.getTransactionType());

				// customer info
				detail.setUserName(customer.getUserName());
				detail.setFirstName(customer.getFirstName());
				detail.setLastName(customer.getLastName());

				// status
				if (transaction.getExecuteDate() == null) {
					detail.setStatus("Pending");

				} else {
					detail.setStatus("Completed " + detail.getTransactionType());
					detail.setExecuteDay(transaction.getExecuteDate());
				}

				// deposit & request check
				if (transaction.getTransactionType().equals(Util.getDepositCheck())
				    || transaction.getTransactionType().equals(Util.getRequestCheck())) {

					detail.setAmount(transaction.getAmountTwoDecimal());

					// buy & sell fund
				} else if (transaction.getTransactionType().equals(Util.getBuyFund())
				    || transaction.getTransactionType().equals(Util.getSellFund())) {

					// buy fund
					if (transaction.getTransactionType().equals(Util.getBuyFund())) {
						if (transaction.getExecuteDate() != null) {
							detail.setShares(transaction.getSharesThreeDecimal());
							detail.setPrice(transaction.getPriceTwoDecimal());
						}
						// if not executed, only has amount
						detail.setAmount(transaction.getAmountTwoDecimal());

						// sell fund
					} else if (transaction.getTransactionType()
					    .equals(Util.getSellFund())) {
						if (transaction.getExecuteDate() != null) {
							detail.setAmount(transaction.getAmountTwoDecimal());
							detail.setPrice(transaction.getPriceTwoDecimal());
						}
						// if not executed, only has shares
						detail.setShares(transaction.getSharesThreeDecimal());
					}
					// set fund
					FundBean fund = fundDAO.getFundById(transaction.getFundId());
					detail.setFundName(fund.getName());
					detail.setFundTicker(fund.getTicker());

				}
				detailedTransactionBeans[i] = detail;
			}

			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
		return detailedTransactionBeans;
	}

	public DetailedFundBean[] getDetailFunds() throws RollbackException {
		DetailedFundBean[] detailedFundBeans = null;
		try {
			Transaction.begin();
			FundBean[] fundBeans = fundDAO.getFunds();
			detailedFundBeans = new DetailedFundBean[fundBeans.length];
			for (int i = 0; i < fundBeans.length; i++) {
				detailedFundBeans[i] = new DetailedFundBean();
				detailedFundBeans[i].setFundName(fundBeans[i].getName());
				int id = fundBeans[i].getId();
				detailedFundBeans[i].setFundId(id);
				detailedFundBeans[i].setTicker(fundBeans[i].getTicker());
				FundPriceBean currentFundPriceBean = fundPriceDAO
				    .getCurrentFundPrice(id);
				if (currentFundPriceBean != null) {
					detailedFundBeans[i].setPrice(currentFundPriceBean
					    .getPriceTwoDecimal());
					detailedFundBeans[i].setDate(currentFundPriceBean.getPriceDate());
				} else {
					detailedFundBeans[i].setPrice("-");
					detailedFundBeans[i].setDate("-");
				}
			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
		return detailedFundBeans;
	}

}
