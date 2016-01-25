package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Util;

public class DepositCheckForm extends FormBean {
	private String amount;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		checkMissingInput(errors);
		if (!Util.matchTwoDecimalInput(amount)) {
			errors.add("Deposit check amount should have at most two decimal places");
		}
		if (Double.parseDouble(amount) > 1000000) {
			errors.add("Deposit should be less than 1000000");

		}
		if (errors.size() > 0) {
			return errors;
		}
		return errors;
	}

	private void checkMissingInput(List<String> errors) {
		checkEmptyInput(amount, errors,
		    "Please input the amount you want to deposit.");
	}

	private void checkEmptyInput(String input, List<String> errors, String errMsg) {

		if (input == null || input.length() == 0) {
			errors.add(errMsg);
		}

	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
