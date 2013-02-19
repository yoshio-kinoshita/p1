package callable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

					int ipIndex = line.indexOf(P1Util.SPACE);
					int dateStartIndex = ipIndex + 6;
					int dateEndIndex = dateStartIndex + 20;

					int methodStartIndex = dateEndIndex + 9;
					int methodEndIndex = line.indexOf(P1Util.SPACE,
							methodStartIndex);
					String method = line.substring(methodStartIndex,
							methodEndIndex);

					int urlStartIndex = methodEndIndex + 1;
					int urlEndIndexSpace = line.indexOf(P1Util.SPACE,
							urlStartIndex);
					int urlEndIndexQuestion = line.indexOf(P1Util.QUESTION,
							urlStartIndex);

					int urlEndIndex;
					if (urlEndIndexQuestion < 0) {
						urlEndIndex = urlEndIndexSpace;
					} else if (urlEndIndexQuestion > urlEndIndexSpace) {
						urlEndIndex = urlEndIndexSpace;
					} else {
						urlEndIndex = urlEndIndexQuestion;
					}

					String url = line.substring(urlStartIndex, urlEndIndex);

					if (P1Util.isFilterd(method, url, filters) == false) {

						String ip = line.substring(0, ipIndex);

						Date lastAccessDate = accessMap.get(ip);

						Date accessDate = P1Util.parseTime(line.substring(
								dateStartIndex, dateEndIndex));

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
