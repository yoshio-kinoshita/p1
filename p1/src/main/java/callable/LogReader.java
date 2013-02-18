package callable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import util.P1Util;
import entity.Result;

public class LogReader {

	public static Map<String, Result> read(List<String> filenames,
			List<String> filters) {

		Map<String, Result> resultMap = new HashMap<>();
		Map<String, Date> accessMap = new HashMap<>();

		Pattern ptn = Pattern.compile(P1Util.SPACE);
		for (String filename : filenames) {

			try (BufferedReader reader = new BufferedReader(new FileReader(
					new File(filename)))) {
				String line;

				while ((line = reader.readLine()) != null) {

					String[] lineArray = ptn.split(line, P1Util.MAX_SPLIT);

					String method = lineArray[5];
					String url = lineArray[6];
					if (P1Util.isFilterd(method, url, filters) == false) {

						String ip = lineArray[0];
						Date lastAccessDate = accessMap.get(ip);

						Date accessDate = P1Util.parseTime(lineArray[3]);
						if (lastAccessDate == null
								|| P1Util.checkAccessDate(lastAccessDate,
										accessDate)) {

							String key = P1Util.key(ip, url);
							Result result = resultMap.get(key);
							if (result == null) {
								result = new Result();
								result.setIp(ip);
								result.setUrl(url);
								result.setFirstAccessDate(accessDate);
								result.setCount(1);
								resultMap.put(key, result);

							} else {
								result.setCount(result.getCount() + 1);
								resultMap.put(key, result);
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
