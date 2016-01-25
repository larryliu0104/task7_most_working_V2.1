package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databean.PositionBean;

public class PositionDAO extends GenericDAO<PositionBean> {
	public PositionDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(PositionBean.class, tableName, cp);
	}

	public PositionBean[] getPositionsByCustomerId(int customerId)
	    throws RollbackException {
		PositionBean[] positions = match(MatchArg.equals("customerId", customerId));
		if (positions.length == 0) {
			return null;
		}
		return positions;
	}

	public PositionBean getPosition(int customerId, int fundId)
	    throws RollbackException {
		PositionBean[] positions = match(MatchArg.and(
		    MatchArg.equals("customerId", customerId),
		    MatchArg.equals("fundId", fundId)));
		System.out.println("PositionDao 30: " + positions);
		if (positions.length > 1) {
			String errMsg = "more than one position exists";
			throw new RollbackException(errMsg);
		} else if (positions.length == 0) {
			return null;
		} else {
			return positions[0];
		}
	}

}
