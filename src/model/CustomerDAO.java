package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.CustomerBean;

public class CustomerDAO extends GenericDAO<CustomerBean> {

	public CustomerDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(CustomerBean.class, tableName, cp);
	}

	public CustomerBean[] getAllCustomers() throws RollbackException {
		return match();
	}

	public void create(CustomerBean customer) throws RollbackException {
		try {
			Transaction.begin();

			// customer user name exists
			if (getCustomerByUserName(customer.getUserName()) != null) {
				String errMsg = "Customer user name " + customer.getUserName()
				    + " exists";
				throw new RollbackException(errMsg);
			}

			// create customer
			super.create(customer);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public void setPassword(String userName, String password)
	    throws RollbackException {
		try {
			Transaction.begin();
			CustomerBean customer = getCustomerByUserName(userName);

			if (customer == null) {
				String errMsg = "Customer " + userName + " doesn't exists";
				throw new RollbackException(errMsg);
			} else {
				customer.setPassword(password);
				update(customer);
			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public boolean isCustomerExistedByUserName(String userName)
	    throws RollbackException {
		CustomerBean[] customers = match(MatchArg.equals("userName", userName));
		if (customers != null && customers.length > 0) {
			return true;
		} else {
			return false;
		}
	}

	public CustomerBean getCustomerByUserName(String userName)
	    throws RollbackException {
		CustomerBean[] customers = match(MatchArg.equals("userName", userName));

		if (customers.length == 0) {
			return null;
		} else if (customers.length > 1) {
			String errMsg = "customer user name " + userName
			    + " matches more than one customer account";

			throw new RollbackException(errMsg);
		} else {
			return customers[0];
		}
	}

	public CustomerBean[] getCustomersByKeyword(String keyword)
	    throws RollbackException {
		CustomerBean[] customers = match(MatchArg.or(
		    MatchArg.containsIgnoreCase("firstName", keyword),
		    MatchArg.containsIgnoreCase("lastName", keyword),
		    MatchArg.containsIgnoreCase("userName", keyword)));

		return customers;
	}

	public CustomerBean getCustomerById(int customerId) throws RollbackException {
		CustomerBean[] customers = match(MatchArg.equals("id", customerId));

		if (customers.length == 0) {
			return null;
		} else if (customers.length > 1) {
			String errMsg = "customer customer id " + customerId
			    + " matches more than one customer account";

			throw new RollbackException(errMsg);
		} else {
			return customers[0];
		}
	}
}
