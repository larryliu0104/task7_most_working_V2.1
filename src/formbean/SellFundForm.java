package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import com.google.gson.Gson;

public class SellFundForm extends FormBean {
	private String shares;
	private String action;

	public List<String> getValidationErrors() {
		List<String> errorList = new ArrayList<String>();

		getShareErrors(errorList);

		if (!isSell()) {
			errorList.add("Action is invalid");
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

	public boolean isSell() {
		return "sell".equals(action);
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public double getShareValue() {
		if (shares == null) {
			return -1;
		}
		double shareValue = -1;
		try {
			shareValue = Double.valueOf(shares);
		} catch (Exception e) {
		}

		return shareValue;
	}

	public String getShare() {
		return shares;
	}

	public void setShare(String shares) {
		this.shares = shares;
	}

	private void getShareErrors(List<String> errors) {
		if (shares == null) {
			errors.add("share is required");
			return;
		}
		if (shares.indexOf(".") != -1
		    && (shares.length() - 1 - shares.indexOf(".")) > 3) {
			errors.add("Shares are tracked upto 3 decimal places only");
			return;
		}
		double shareValue = 0;
		try {
			shareValue = Double.valueOf(shares);
		} catch (Exception e) {
			errors.add("No letters, symbols or commas. Only numbers allowed");
			return;
		}
		if (shareValue < 0.001 || shareValue > 100000000) {
			errors.add("The share range should lies between " + 0.001 + " and "
			    + 100000000);
			return;
		}
	}

	public String getShares() {
		return shares;
	}

	public void setShares(String shares) {
		this.shares = shares;
	}
}
