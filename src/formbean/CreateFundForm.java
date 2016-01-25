package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Util;

public class CreateFundForm extends FormBean {
	private static final int MAX_FUND_NAME_LENGTH = 20;
	private static final int MAX_FUND_TICKER_LENGTH = 5;
	private String name;
	private String ticker;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		checkMissingInput(errors);
		if (errors.size() > 0) {
			return errors;
		}
		if (name.length() > MAX_FUND_NAME_LENGTH) {
			errors.add("Fund name should be shorter than 20 characters");
		}
		if (ticker.length() > MAX_FUND_TICKER_LENGTH
		    || !ticker.matches("[a-zA-Z]+")) {
			errors
			    .add("Fund ticker should be shorter than 5 characters and only consists of letters");
		}
		if (errors.size() > 0) {
			return errors;
		}
		return errors;
	}

	private void checkMissingInput(List<String> errors) {
		checkEmptyInput(name, errors, "Please input your fund name.");
		checkEmptyInput(ticker, errors, "Please input your fund ticker.");
	}

	private void checkEmptyInput(String input, List<String> errors, String errMsg) {
		if (input == null || input.length() == 0) {
			errors.add(errMsg);
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Util.sanitizeInputString(name);
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = Util.sanitizeInputString(ticker);
	}

}
