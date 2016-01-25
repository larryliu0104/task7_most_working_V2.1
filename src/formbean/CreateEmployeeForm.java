package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Util;

public class CreateEmployeeForm extends FormBean {
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String confirm;
	private String action;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		checkMissingInput(errors);

		if (!password.equals(confirm)) {
			errors.add("Your confirmed password is different.");
		}
		if (action == null) {
			errors.add("Missing action.");
		}
		if (errors.size() > 0) {
			return errors;
		}
		if (!action.equals("create")) {
			errors.add("Invalid button. Your action name: " + action
			    + " Expected action name: Create Employee Account");
		}
		return errors;

	}

	private void checkMissingInput(List<String> errors) {
		checkEmptyInput(userName, errors, "Please input your user name.");
		checkEmptyInput(firstName, errors, "Please input your first name.");
		checkEmptyInput(lastName, errors, "Please input your last name.");
		checkEmptyInput(password, errors, "Please input your password.");
		checkEmptyInput(confirm, errors, "Please confirm your password.");
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
		this.userName = Util.sanitizeInputString(userName.trim());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = Util.sanitizeInputString(password.trim());

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = Util.sanitizeInputString(firstName.trim());

	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = Util.sanitizeInputString(lastName.trim());
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = Util.sanitizeInputString(confirm.trim());
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
