package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Util;

public class ChangePwdForm extends FormBean {
	private String newPassword;
	private String confirmNewPassword;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		checkMissingInput(errors);

		if (!newPassword.equals(confirmNewPassword)) {
			errors.add("Your confirmed new password is different. New pwd is "
			    + newPassword + " comfirmed pwd is " + confirmNewPassword);
		}

		if (errors.size() > 0) {
			return errors;
		}

		return errors;

	}

	private void checkMissingInput(List<String> errors) {
		checkEmptyInput(newPassword, errors, "Please input your new password.");
		checkEmptyInput(confirmNewPassword, errors,
		    "Please confirm your new password.");

	}

	private void checkEmptyInput(String input, List<String> errors, String errMsg) {

		if (input == null || input.length() == 0) {
			errors.add(errMsg);
		}

	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = Util.sanitizeInputString(newPassword.trim());
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = Util.sanitizeInputString(confirmNewPassword);
	}

}
