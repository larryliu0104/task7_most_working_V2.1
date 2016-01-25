package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class SearchFundForm extends FormBean {
	private String fund;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (fund == null || fund.length() == 0) {
			errors.add("Fund name is required");
		}

		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund.trim();
	}
}
