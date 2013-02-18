package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class P1Util {
	public static final Pattern IP = Pattern
			.compile("\\d+\\.\\d+\\.\\d+\\.\\d+");
	public static final Pattern TIME = Pattern
			.compile("\\d+\\/[A-Z][a-z]{2}\\/\\d+:\\d+:\\d+:\\d+");
	public static final Pattern URL = Pattern
			.compile("[-_.!~*\\'()a-zA-Z0-9;\\/:\\@&=+\\$,%#]+");
	public static final String FILTER_BASE = "\"GET|\"HEAD|\"POST";

	public static final String EXTENSIONS[] = { "cgi", "htm", "html", "php" };

	public static String SPACE = "\\s";

	public static String MARK = "\"";

	public static String SLASH = "/";

	public static String COLON = ".";

	public static int MAX_SPLIT = 8;

	public enum Month {

	}

	public static final String DATE_FOMAT = "[dd/MMM/yyyy:HH:mm:ss";

	public static Date parseTime(String accessDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(P1Util.DATE_FOMAT,
				Locale.US);
		try {
			return sdf.parse(accessDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
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

	public static List<String> sortLogfiles(List<String> logfiles) {

		List<Object[]> list = new ArrayList<>();
		for (String logfile : logfiles) {
			try (BufferedReader reader = new BufferedReader(new FileReader(
					new File(logfile)))) {

				String line = reader.readLine();
				Date accessDate = P1Util.parseTime(line.split(P1Util.SPACE)[3]);
				list.add(new Object[] { accessDate, logfile });

			} catch (IOException e) {
			}
		}

		Collections.sort(list, new FileComparator());

		List<String> sorted = new ArrayList<>();
		for (Object[] o : list) {
			sorted.add((String) o[1]);
		}

		return sorted;
	}

	private static class FileComparator implements Comparator<Object[]> {

		@Override
		public int compare(Object[] o1, Object[] o2) {

			Date d1 = (Date) o1[0];
			Date d2 = (Date) o2[0];

			return d1.compareTo(d2);
		}
	}

	public static boolean isFilterd(String method, String url,
			List<String> filters) {

		if (method.matches(FILTER_BASE)) {

			String urlWithSlash = url;
			if (!urlWithSlash.endsWith(SLASH)) {
				urlWithSlash = urlWithSlash + SLASH;
			}

			for (String filter : filters) {

				if (urlWithSlash.startsWith(filter)) {
					return true;
				} else {
					return false;
				}
			}

			int lastindex = url.lastIndexOf(COLON);
			if (lastindex > 0) {
				String extension = url.substring(lastindex + 1);
				if (Arrays.binarySearch(EXTENSIONS, extension) >= 0) {
					return false;
				} else {
					return true;
				}
			}
		} else {
			return true;
		}

		return false;

	}
}
