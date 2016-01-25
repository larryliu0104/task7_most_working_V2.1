package model;

import java.util.ArrayList;
import java.util.Comparator;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import util.Log;
import util.Util;
import databean.TransactionBean;

public class TransactionDAO extends GenericDAO<TransactionBean> {
	private static final String TAG = "Transaction DAO";

	public TransactionDAO(ConnectionPool cp, String tableName)
	    throws DAOException {
		super(TransactionBean.class, tableName, cp);
	}

	public TransactionBean[] getPendingTransactions() throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.equals("executeDate", null));
		return transactions;
	}

	public TransactionBean[] getPendingTransactionsByCustomerId(int customerId)
	    throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.and(
		    MatchArg.equals("executeDate", null),
		    MatchArg.equals("customerId", customerId)));
		Log.i(TAG, "pending trans # for customer id " + customerId + " is "
		    + transactions.length);
		return transactions;
	}

	public ArrayList<TransactionBean> getTransactionsByCustomerId(int customerId)
	    throws RollbackException {
		TransactionBean[] allTransactions = match(MatchArg.equals("customerId",
		    customerId));
		Log.i(TAG, "customer id is " + customerId);
		ArrayList<TransactionBean> sortedTransactions = new ArrayList<TransactionBean>();
		if (allTransactions == null || allTransactions.length == 0) {
			return sortedTransactions;
		}
		// add completed transactions
		for (TransactionBean transaction : allTransactions) {
			if (transaction.getExecuteDate() != null) {
				sortedTransactions.add(transaction);
			}
		}

		// sort completed transactions by date
		sortedTransactions.sort(new Comparator<TransactionBean>()

		{
			@Override
			public int compare(TransactionBean o1, TransactionBean o2) {

				return Util.compareDateStrings(o1.getExecuteDate(), o2.getExecuteDate());
			}
		});

		Log.i(TAG, "get complete transaction");
		// add pending transactions to the end
		TransactionBean[] pendingTransactions = getPendingTransactionsByCustomerId(customerId);
		for (TransactionBean transaction : pendingTransactions) {
			sortedTransactions.add(transaction);
		}
		Log.i(TAG, "get pending transaction");
		return sortedTransactions;
	}

	public TransactionBean[] getTransactions() throws RollbackException {
		return match();
	}

	public double getPendingAmount(int customerId) throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.and(MatchArg.equals(
		    "customerId", customerId)));
		long amount = 0;
		if (transactions == null) {
			return amount;
		}
		for (TransactionBean t : transactions) {
			if ((t.getTransactionType() == null)
			    && (t.getTransactionType().equals(Util.getBuyFund())
			    || t.getTransactionType().equals(Util.getRequestCheck()))) {
				amount += t.getAmount();
			}
		}
		Log.i(TAG, "get pending amount " + amount);
		return (double) amount;

	}

	public double getPendingShare(int customerId, int fundId)
	    throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.and(
		    MatchArg.equals("customerId", customerId),
		    MatchArg.equals("fundId", fundId),
		    MatchArg.equals("executeDate", null),
		    MatchArg.equals("transactionType", "Sell Fund")));
		long share = 0;
		if (transactions == null) {
			return share;
		}
		for (TransactionBean transactionBean : transactions) {
			share += transactionBean.getShares();
		}
		return (double) share;
	}

}
