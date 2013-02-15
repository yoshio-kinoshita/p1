package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class P1Util {
	public static final Pattern IP = Pattern
			.compile("\\d+\\.\\d+\\.\\d+\\.\\d+");
	public static final Pattern TIME = Pattern
			.compile("\\d+\\/[A-Z][a-z]{2}\\/\\d+:\\d+:\\d+:\\d+");
	public static final Pattern URL = Pattern
			.compile("(GET|HEAD|POST)\\s{1}[-_.!~*\\'()a-zA-Z0-9;\\/:\\@&=+\\$,%#]+");
	public static final String FILTER_BASE = "(GET|HEAD|POST)\\s{1}($1){1}";

	private static final String EXTENSIONS[] = { "cgi", "htm", "html", "php" };

	public enum Month {

	}

	public static final String DATE_FOMAT = "dd/MMM/yyyy:HH:mm:ss";

	public static String parseUrl(String line) {
		Matcher urlMater = P1Util.URL.matcher(line);
		if (urlMater.find()) {
			String url = urlMater.group();
			url = url.replaceAll("(GET|HEAD|POST)\\s{1}", "");

			int lastindex = url.lastIndexOf(".");

			if (lastindex > 0) {
				String extension = url.substring(lastindex + 1);
				if (Arrays.binarySearch(EXTENSIONS, extension) >= 0) {
					return url;
				}
			} else {
				return url;
			}
		}
		return "";
	}

	public static String parseIp(String line) {
		Matcher ipMater = P1Util.IP.matcher(line);
		if (ipMater.find()) {
			return ipMater.group();
		}
		return "";
	}

	public static String parseTime2String(String line) {
		Matcher timeMater = P1Util.TIME.matcher(line);
		if (timeMater.find()) {
			return timeMater.group();
		}
		return "";
	}

	public static Date parseTime(String line) {
		Matcher timeMater = P1Util.TIME.matcher(line);
		if (timeMater.find()) {
			SimpleDateFormat sdf = new SimpleDateFormat(P1Util.DATE_FOMAT,
					Locale.US);
			try {
				return sdf.parse(timeMater.group());
			} catch (ParseException e) {
			}
		}
		return null;
	}

	public static void showMem() {

		int mb = 1024 * 1024;

		long fm = Runtime.getRuntime().freeMemory();
		long tm = Runtime.getRuntime().totalMemory();

		System.out.println("total: " + tm / mb + " ; free: " + fm / mb
				+ " ; used: " + (tm - fm) / mb);
	}

	public static boolean checkAccessDate(Date lastAccessDate, Date accessDate) {
		return accessDate.after(addSecond(lastAccessDate, 300));
	}

	/**
	 * 対象時間に指定した秒数を足します。
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	private static Date addSecond(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, amount);
		return c.getTime();
	}

	public static String key(String ip, String url) {
		return ip + url;
	}
}
