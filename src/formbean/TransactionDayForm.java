package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Log;
import util.Util;

public class TransactionDayForm extends FormBean {
	private String[] ids;
	private String[] prices;
	private String date;
	private final static String TAG = "TransactionDayForm";

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		checkPricesErrors(errors);
		checkDateErrors(errors);

		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}

	private void checkDateErrors(List<String> errors) {
		if (getDate() == null || getDate().length() == 0) {
			errors.add("Please input valid transaction day.");
		}
	}

	public String getDate() {
		Log.i(TAG, "date is " + date);
		return date;

	}

	public void setDate(String date) {
		this.date = date;
	}

	public String[] getPrices() {
		return prices;
	}

	public void setPrices(String[] prices) {
		this.prices = prices;
	}

	private void checkPricesErrors(List<String> errors) {
		if (ids == null && prices == null) {
			return;
		}
		Log.i(TAG, "id length " + ids.length + " prices length " + prices.length);
		if ((ids == null && prices != null) || (ids != null && prices == null)) {
			errors.add("Error id # is different from price #");
			return;
		}
		if (ids != null && prices != null && ids.length != prices.length) {
			errors.add("id # is different from price #");
			return;
		}

		for (int i = 0; i < prices.length; i++) {
			if (prices[i] == null) {
				errors.add("Pissing price no." + i);
				return;
			}
			if (!Util.matchTwoDecimalInput(prices[i])) {
				errors
				    .add("Price should have at most two decimal places and positive.");
				return;
			}

			Log.i(TAG, "prices i " + prices[i]);
			if (Double.parseDouble(prices[i]) < 0) {
				errors.add("Price no." + i + " is less than 0");
				return;
			}
			if (Double.parseDouble(prices[i]) > 1000) {
				errors.add("Price no." + i
				    + " is more than 1000. Max price you could set is 1000");
				return;
			}
		}
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

}
