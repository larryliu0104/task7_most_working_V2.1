package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Util;

public class CreateCustomerForm extends FormBean {
	private String userName = null;
	private String password = null;
	private String firstName = null;
	private String lastName = null;
	private String addressLine1 = null;
	private String addressLine2 = null;
	private String city = null;
	private String state = null;
	private String zip = null;
	private String confirm;
	private String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		checkMissingInput(errors);
		if (!Util.matchZip(zip)) {
			errors.add("zip should be 5 digit number");
		}
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
			    + " Expected action name: Create Customer Account");
		}
		return errors;

	}

	private void checkMissingInput(List<String> errors) {
		checkEmptyInput(userName, errors, "Please input your user name.");
		checkEmptyInput(firstName, errors, "Please input your first name.");
		checkEmptyInput(lastName, errors, "Please input your last name.");
		checkEmptyInput(password, errors, "Please input your password.");
		checkEmptyInput(confirm, errors, "Please confirm your password.");
		checkEmptyInput(addressLine1, errors, "Please input your address.");
		checkEmptyInput(city, errors, "Please input your city.");
		checkEmptyInput(zip, errors, "Please input your zip.");
		checkEmptyInput(state, errors, "Please input your state.");
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

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = Util.sanitizeInputString(addressLine1.trim());
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = Util.sanitizeInputString(addressLine2.trim());
		;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = Util.sanitizeInputString(city.trim());
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = Util.sanitizeInputString(state.trim());
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = Util.sanitizeInputString(zip.trim());

	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = Util.sanitizeInputString(confirm.trim());
	}

}
