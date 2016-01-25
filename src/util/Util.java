package util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {
	private static final String DEPOSIT_CHECK = "Deposit Check";
	private static final String REQUEST_CHECK = "Request Check";
	private static final String SELL_FUND = "Sell Fund";
	private static final String BUY_FUND = "Buy Fund";

	public static boolean matchTwoDecimalInput(String input) {
		return input.matches("\\d+(\\.\\d{1,2})?");
	}

	// 前面的人早返回-1
	public static int compareDateStrings(String oldDate, String newDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = null;
		Date end = null;

		try {
			start = sdf.parse(oldDate);
			end = sdf.parse(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return start.compareTo(end);
	}

	public static String formatNumber(double value, String format) {
		DecimalFormat df = new java.text.DecimalFormat(format);
		return df.format(value);
	}

	public static String sanitizeInputString(String input) {
		return input.replace("<", "&lt;").replace(">", "&gt;")
		    .replace("\"", "&quot;").replace("&", "&amp;");
	}

	public static String getDepositCheck() {
		return DEPOSIT_CHECK;
	}

	public static String getRequestCheck() {
		return REQUEST_CHECK;
	}

	public static String getSellFund() {
		return SELL_FUND;
	}

	public static String getBuyFund() {
		return BUY_FUND;
	}

	public static String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}

	public static boolean matchZip(String input) {
		return input.matches("\\b\\d{5}\\b");
	}
}
