package databean;

import java.text.DecimalFormat;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class FundPriceBean {

	private int id;
	private int fundId;
	private long price;
	private String priceDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}

	public long getPrice() {
		return price;
	}

	public String getPriceTwoDecimal() {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(getPrice() * 1.0 / 100);
	}

	public void setPrice(long price) {

		this.price = price;
	}

	public String getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(String priceDate) {
		this.priceDate = priceDate;
	}

}
