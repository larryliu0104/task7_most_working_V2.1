package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import com.google.gson.Gson;

import util.Util;

public class BuyFundForm extends FormBean {
	private String amount;
	private String action;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		getAmountErrors(errors);

		if (!isBuy()) {
			errors.add("Invalid action");
		}

		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isBuy() {
		return "buy".equals(action);
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


	public void getAmountErrors(List<String> errors) {
		if (amount == null) {
			errors.add("amount is required");
			return;
		}

		/*
		if (amount.indexOf(".") != -1
				&& (amount.length() - 1 - amount.indexOf(".")) > 2) {
			errors.add("amount is tracked to 2 decimal places ");
			return;
		}
		*/
		double amountValue = 0;
		try {
			amountValue = Double.valueOf(amount);
		} catch (Exception e) {
			errors.add("Numbers and decimal place only. No commas, letters or symbols.");
			return;
		}
		if(amountValue<=0) {
			errors.add("amount must be greater than zero");
			return;
			
		}
	}
}

