package databean;

import java.text.DecimalFormat;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class PositionBean {
	
	private int id;
	private int customerId;
	private int fundId;
	private long shares;

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

	public long getShares() {
		return shares;
	}

	public String getSharesThreeDecimal() {
		DecimalFormat df = new DecimalFormat("#.###");
		return df.format(getShares());

	}

	public void setShares(long shares) {
		this.shares = shares;
	}

}
