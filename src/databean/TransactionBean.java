package databean;

import java.text.DecimalFormat;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class TransactionBean {
	private int id;
	private int customerId;
	private int fundId;
	private String executeDate = null;
	private long shares;
	private long amount;
	public String transactionType;

	// not current price but price on that transaction day
	private long price;

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}

	public String getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}

	public long getShares() {
		return shares;
	}

	public String getSharesThreeDecimal() {
		DecimalFormat df = new DecimalFormat("0.000");
		return df.format(getShares() * 1.0 / 1000);

	}

	public void setShares(long shares) {
		this.shares = shares;
	}

	public long getAmount() {
		return amount;
	}

	public String getAmountTwoDecimal() {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(getAmount() * 1.0 / 100);
	}

	public String getPriceTwoDecimal() {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(getPrice() * 1.0 / 100);
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}
