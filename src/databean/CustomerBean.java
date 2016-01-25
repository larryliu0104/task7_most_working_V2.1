package databean;

import java.text.DecimalFormat;

import org.genericdao.PrimaryKey;

import util.Log;

@PrimaryKey("id")
public class CustomerBean {
	private int id;
	private String userName = null;
	private String password = null;
	private String firstName = null;
	private String lastName = null;
	private String addrLine1 = null;
	private String addrLine2 = null;
	private String city = null;
	private String state = null;
	private String zip = null;
	private long cash = 10000;
	private static final String TAG = "Customer bean";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public long getCash() {
		return cash;
	}

	public String getCashTwoDecimal() {
		DecimalFormat df = new DecimalFormat("0.00");
		String res = df.format((getCash() * 1.0 / 100.0));
		Log.i(TAG, " CASH " + getCash() + " two deci cash " + res);
		return res;
	}

	public void setCash(long cash) {
		this.cash = cash;
	}

}
