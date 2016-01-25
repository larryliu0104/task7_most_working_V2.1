package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.EmployeeBean;

public class EmployeeDAO extends GenericDAO<EmployeeBean> {
	public EmployeeDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(EmployeeBean.class, tableName, cp);
	}

	public void create(EmployeeBean employee) throws RollbackException {
		try {
			Transaction.begin();

			// employee user name exists
			if (getEmployeeByUserName(employee.getUserName()) != null) {
				String errMsg = "Employee user name " + employee.getUserName()
				    + " exists";
				throw new RollbackException(errMsg);
			}
			// create customer
			super.create(employee);
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
			EmployeeBean employee = getEmployeeByUserName(userName);

			if (employee == null) {
				String errMsg = "Customer " + userName + " doesn't exists";
				throw new RollbackException(errMsg);
			} else {
				employee.setPassword(password);
				update(employee);
			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public boolean isEmployExistedByUserName(String userName)
	    throws RollbackException {
		EmployeeBean[] employees = match(MatchArg.equals("userName", userName));
		if (employees != null && employees.length > 0) {
			return true;
		} else {
			return false;
		}
	}

	public EmployeeBean getEmployeeByUserName(String userName)
	    throws RollbackException {
		EmployeeBean[] employees = match(MatchArg.equals("userName", userName));

		if (employees.length == 0) {
			return null;
		} else if (employees.length > 1) {
			String errMsg = "employee user name " + userName
			    + " matches more than one employee account";

			throw new RollbackException(errMsg);
		} else {
			return employees[0];
		}
	}

}
