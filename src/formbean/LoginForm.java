package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import com.google.gson.Gson;

public class LoginForm extends FormBean {

	private String userName;
	private String password;
	private String action;

	private static final String EMPLOYEE_ACTION = "employee_login";
	private static final String CUSTOMER_ACTION = "customer_login";

	public String getPassword() {
		return password;
	}

	public String getAction() {
		return action;
	}

	public void setPassword(String s) {
		password = s.trim();
	}

	public void setAction(String s) {
		action = s;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		checkMissingInput(errors);
		if (action == null)
			errors.add("Action is required");

		if (errors.size() > 0)
			return errors;

		if (!isEmployee() && !isCustomer())
			errors.add("Invalid login action");
		if (userName.matches(".*[<>\"].*"))
			errors.add("User name may not contain angle brackets or quotes");

		return errors;
	}

	private void checkMissingInput(List<String> errors) {
		checkEmptyInput(userName, errors, "User name is required");
		checkEmptyInput(password, errors, "Password is required");
	}

	private void checkEmptyInput(String input, List<String> errors, String errMsg) {
		if (input == null || input.length() == 0) {
			errors.add(errMsg);
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName.trim();
	}

	public boolean isEmployee() {
		return EMPLOYEE_ACTION.equals(action);
	}

	public boolean isCustomer() {
		return CUSTOMER_ACTION.equals(action);
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
