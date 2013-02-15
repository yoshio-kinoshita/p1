package callable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.P1Util;
import entity.Result;

public class LogReader {

	public static Map<String, Result> read(List<String> filenames,
			List<String> filters) {

		Map<String, Result> resultMap = new HashMap<>();
		Map<String, Date> accessMap = new HashMap<>();

		for (String filename : filenames) {

			try (BufferedReader reader = new BufferedReader(new FileReader(
					new File(filename)))) {
				String line;

				while ((line = reader.readLine()) != null) {
					boolean filtertarget = false;
					for (String filter : filters) {
						Pattern p = Pattern.compile(P1Util.FILTER_BASE.replace(
								"$1", filter));
						Matcher filterMatcher = p.matcher(line);
						if (filterMatcher.find()) {
							filtertarget = true;
							break;
						}
					}

					if (filtertarget == false) {

						String url = P1Util.parseUrl(line);
						if (url.equals("")) {
							continue;
						}

						String ip = P1Util.parseIp(line);
						if (ip.equals("")) {
							continue;
						}

						Date accessDate = P1Util.parseTime(line);
						if (accessDate == null) {
							continue;
						}

						Date lastAccessDate = accessMap.get(ip);

						if (lastAccessDate == null
								|| P1Util.checkAccessDate(lastAccessDate,
										accessDate)) {
							Result result = resultMap.get(P1Util.key(ip, url));
							if (result == null) {
								result = new Result();
								result.setIp(ip);
								result.setUrl(url);
								result.setFirstAccessDate(accessDate);
								result.setCount(1);
								resultMap.put(P1Util.key(ip, url), result);

							} else {
								result.setCount(result.getCount() + 1);
								resultMap.put(P1Util.key(ip, url), result);
							}
							accessMap.put(ip, accessDate);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return resultMap;
	}
}
