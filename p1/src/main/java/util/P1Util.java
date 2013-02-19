package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class P1Util {
	public static final Pattern IP = Pattern
			.compile("\\d+\\.\\d+\\.\\d+\\.\\d+");
	public static final Pattern TIME = Pattern
			.compile("\\d+\\/[A-Z][a-z]{2}\\/\\d+:\\d+:\\d+:\\d+");
	public static final Pattern URL = Pattern
			.compile("[-_.!~*\\'()a-zA-Z0-9;\\/:\\@&=+\\$,%#]+");
	public static final String FILTER_BASE = "GET|HEAD|POST";

	private static String EXTENSIONS[] = { "CGI", "HTM", "PHP", "HTML", "cgi",
			"htm", "html", "php" };

	public static final String SPACE = " ";

	public static final String QUESTION = "?";

	public static final String MARK = "\"";

	public static final String SLASH = "/";

	public static final String COLON = ".";
	public static final String SEMI_COLON = ":";

	private static final int DD_START_INDEX = 0;
	private static final int DD_END_INDEX = DD_START_INDEX + 2;
	private static final int MM_START_INDEX = DD_END_INDEX + 1;
	private static final int MM_END_INDEX = MM_START_INDEX + 3;
	private static final int YYYY_START_INDEX = MM_END_INDEX + 1;
	private static final int YYYY_END_INDEX = YYYY_START_INDEX + 4;
	private static final int HH_START_INDEX = YYYY_END_INDEX + 1;
	private static final int HH_END_INDEX = HH_START_INDEX + 2;
	private static final int MI_START_INDEX = HH_END_INDEX + 1;
	private static final int MI_END_INDEX = MI_START_INDEX + 2;
	private static final int SS_START_INDEX = MI_END_INDEX + 1;
	private static final int SS_END_INDEX = SS_START_INDEX + 2;

	private static int convertMMMType(String value) {
		switch (value) {
		case "Jan":
			return 0;
		case "Feb":
			return 1;
		case "Mar":
			return 2;
		case "Apr":
			return 3;
		case "May":
			return 4;
		case "Jun":
			return 5;
		case "Jul":
			return 6;
		case "Aug":
			return 7;
		case "Sep":
			return 8;
		case "Oct":
			return 9;
		case "Nov":
			return 10;
		default:
			return 11;
		}
	}

	/**
	 * アクセス日付をDateに変換します。 <br>
	 * 処理負荷。GC発生する。
	 * 
	 * @param accessDate
	 * @return
	 */
	public static Date parseTime(String accessDate) {
		Calendar c = Calendar.getInstance();

		String dd = accessDate.substring(DD_START_INDEX, DD_END_INDEX);
		int mm = convertMMMType(accessDate.substring(MM_START_INDEX,
				MM_END_INDEX));
		String yyyy = accessDate.substring(YYYY_START_INDEX, YYYY_END_INDEX);
		String hh = accessDate.substring(HH_START_INDEX, HH_END_INDEX);
		String mi = accessDate.substring(MI_START_INDEX, MI_END_INDEX);
		String ss = accessDate.substring(SS_START_INDEX, SS_END_INDEX);

		c.set(Integer.valueOf(yyyy), mm, Integer.valueOf(dd),
				Integer.valueOf(hh), Integer.valueOf(mi), Integer.valueOf(ss));
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();

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
		c.set(Calendar.MILLISECOND, 0);
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

				int ipIndex = line.indexOf(P1Util.SPACE) - 1;
				int dateStartIndex = ipIndex + 7;
				int dateEndIndex = dateStartIndex + 20;
				Date accessDate = P1Util.parseTime(line.substring(
						dateStartIndex, dateEndIndex));
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

	/**
	 * フィルタ対象化判定します。
	 * 
	 * 処理時間：0
	 * 
	 * @param method
	 * @param url
	 * @param filters
	 * @return
	 */
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

			int lastindexslash = url.lastIndexOf(P1Util.SLASH);

			url = url.substring(lastindexslash);
			int lastindex = url.lastIndexOf(P1Util.COLON);

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
