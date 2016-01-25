package model;

import java.util.Arrays;
import java.util.Comparator;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import util.Util;
import databean.FundPriceBean;

public class FundPriceDAO extends GenericDAO<FundPriceBean> {

	public FundPriceDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(FundPriceBean.class, tableName, cp);
	}

	public FundPriceBean[] getPrices(int fundId) throws RollbackException {
		FundPriceBean[] fundPrices = match(MatchArg.equals("fundId", fundId));
		if (fundPrices == null) {
			String errMsg = "fund id " + fundId + " not found";
			throw new RollbackException(errMsg);
		} else {
			Arrays.sort(fundPrices, new Comparator<FundPriceBean>() {

				@Override
				public int compare(FundPriceBean o1, FundPriceBean o2) {
					return Util.compareDateStrings(o1.getPriceDate(), o2.getPriceDate());
				}
			});
			return fundPrices;
		}
	}

	public FundPriceBean[] getAllPrices() throws RollbackException {
		return match();
	}

	public FundPriceBean getCurrentFundPrice(int fundId) throws RollbackException {
		FundPriceBean[] fundPrices = getPrices(fundId);
		if (fundPrices == null) {
			String errMsg = "fund id " + fundId + " not found";
			throw new RollbackException(errMsg);
		} else {
			if (fundPrices.length != 0) {
				return fundPrices[fundPrices.length - 1];
			} else {
				return null;
			}
		}
	}
}
