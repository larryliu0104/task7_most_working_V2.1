package databean;

import java.text.DecimalFormat;

/*
 * This class is non-persistent 
 * object that aggregates  tables(Fund,Price).
 */
public class ShareInformationBean {
	private int fundId;
	private String fundName;
	private String fundSymbol;
	private String share;

	private String shareAmount; // in money equvalent

	public String getSharesThreeDecimal() {
		DecimalFormat df = new DecimalFormat("0.000");
		return df.format(Long.parseLong(getShare()) * 1.0 / 1000);

	}

	public String getAmountTwoDecimal() {
		DecimalFormat df = new DecimalFormat("0.00");
		System.out.println("Share:" + getShareAmount());
		return df.format(Double.parseDouble(getShareAmount()) * 1.0 / 100);
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getFundSymbol() {
		return fundSymbol;
	}

	public void setFundSymbol(String fundSymbol) {
		this.fundSymbol = fundSymbol;
	}

	public String getShare() {
		return share;
	}

	public void setShare(long share) {
		this.share = String.valueOf(share);
	}

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}

	public String getShareAmount() {
		return shareAmount;
	}

	public void setShareAmount(double amount) {
		this.shareAmount = String.valueOf(amount);
	}
}
