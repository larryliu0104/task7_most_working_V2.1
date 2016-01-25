package databean;

import model.Model;

//TransactionBean Wrapper class
public class DetailedTransactionBean {
	private String userName;
	private String firstName;
	private String lastName;

	private String executeDay = "-";

	private String fundName;
	private String fundTicker;

	private String price = "-";
	private String shares = "-";
	private String amount = "-";

	private String transactionType;
	private Model model;
	private String status;

	public DetailedTransactionBean() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getExecuteDay() {
		return executeDay;
	}

	public void setExecuteDay(String executeDay) {
		this.executeDay = executeDay;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getFundTicker() {
		return fundTicker;
	}

	public void setFundTicker(String fundTicker) {
		this.fundTicker = fundTicker;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getShares() {
		return shares;
	}

	public void setShares(String shares) {
		this.shares = shares;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
