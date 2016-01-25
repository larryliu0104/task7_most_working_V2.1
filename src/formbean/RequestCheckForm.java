package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import com.google.gson.Gson;

public class RequestCheckForm extends FormBean {
	private String amount;
	private String action;

	public List<String> getValidationErrors() {
		List<String> errorList = new ArrayList<String>();

		getAmountErrors(errorList);

		if (!isRequest()) {
			errorList.add("Invalid action");
		}

		if (errorList.size() > 0) {
			return errorList;
		}

		return errorList;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isRequest() {
		return "request".equals(action);
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public double getAmountValue() {
		if (amount == null) {
			return 0;
		}
		double value = 0;
		try {
			value = Double.valueOf(amount);
		} catch (Exception e) {
		}

		return value;
	}

	private void getAmountErrors(List<String> errors) {
		if (amount == null) {
			errors.add("amount is required");
			return;
		}
		if (amount.indexOf(".") != -1
				&& (amount.length() - 1 - amount.indexOf(".")) > 2) {
			errors.add("amount is tracked to 2 decimal places ");
			return;
		}
		double amountValue = 0;
		try {
			amountValue = Double.valueOf(amount);
		} catch (Exception e) {
			errors.add("No letters, commas or symbols. Please enter numbers only");
			return;
		}
		if (amountValue < 0.01
				|| amountValue > 100000000) {
			errors.add("The amount range should lie between "
					+ 0.01 + " and "
					+ 100000000);
			return;
		}
	}
}
