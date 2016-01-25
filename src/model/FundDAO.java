package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.FundBean;

public class FundDAO extends GenericDAO<FundBean> {
	public FundDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(FundBean.class, tableName, cp);
	}

	public void create(FundBean fund) throws RollbackException {
		try {
			Transaction.begin();

			// ticker exists
			if (getFundByTicker(fund.getTicker()) != null) {
				String errMsg = "Fund ticker " + fund.getTicker() + " exists";
				throw new RollbackException(errMsg);
			}

			// create fund
			super.create(fund);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	private FundBean getFundByTicker(String ticker) throws RollbackException {
		FundBean[] funds = match(MatchArg.equals("ticker", ticker));

		if (funds.length == 0) {
			return null;
		} else if (funds.length > 1) {
			String errMsg = "fund ticker " + ticker + " matches more than one fund";

			throw new RollbackException(errMsg);
		} else {
			return funds[0];
		}
	}

	public FundBean[] getFunds() throws RollbackException {
		return match();
	}

	public FundBean[] getFundsByKeyword(String keyword) throws RollbackException {
		FundBean[] funds = match(MatchArg.or(
		    MatchArg.containsIgnoreCase("name", keyword),
		    MatchArg.containsIgnoreCase("ticker", keyword)));
		return funds;
	}

	public boolean isFundExistedByName(String name) throws RollbackException {
		FundBean[] funds = match(MatchArg.equals("name", name));
		if (funds != null && funds.length > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFundExistedByTicker(String ticker) throws RollbackException {
		FundBean[] funds = match(MatchArg.equals("ticker", ticker));
		if (funds != null && funds.length > 0) {
			return true;
		} else {
			return false;
		}
	}

	public FundBean getFundById(int fundId) throws RollbackException {
		FundBean[] funds = match(MatchArg.equals("id", fundId));

		if (funds.length == 0) {
			return null;
		} else if (funds.length > 1) {
			String errMsg = "fund id " + fundId
			    + " matches more than one customer account";

			throw new RollbackException(errMsg);
		} else {
			return funds[0];
		}
	}

}
