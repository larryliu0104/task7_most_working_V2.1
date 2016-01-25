package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Util;

public class SearchCustomerForm extends FormBean {
	private String keyword;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		checkEmptyInput(
		    keyword,
		    errors,
		    "Please input the customer user name/first name/last name.\n We list all customers");

		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = Util.sanitizeInputString(keyword.trim());
	}

	private void checkEmptyInput(String input, List<String> errors, String errMsg) {

		if (input == null || input.length() == 0) {
			errors.add(errMsg);
		}

	}
}
