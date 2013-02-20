package jp.co.bbreak.p1gp02.api0047;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class LogReader {

	public static Hashtable<String, Object[]> read(List<File> files,
			String[] filters) {

		Hashtable<String, Object[]> resultMap = new Hashtable<>();
		Hashtable<String, Date> accessMap = new Hashtable<>();

		files = P1Util.sortLogfiles(files);

		for (File file : files) {

			try (BufferedReader reader = new BufferedReader(
					new FileReader(file))) {
				String line;

				while ((line = reader.readLine()) != null) {

					int ipIndex = line.indexOf(P1Util.SPACE);
					int dateStartIndex = ipIndex + P1Util.DATE_START_INDEX;
					int dateEndIndex = dateStartIndex + P1Util.DATE_END_INDEX;

					int methodStartIndex = dateEndIndex
							+ P1Util.METHOD_START_INDEX;
					int methodEndIndex = line.indexOf(P1Util.SPACE,
							methodStartIndex);
					String method = line.substring(methodStartIndex,
							methodEndIndex);

					int urlStartIndex = methodEndIndex
							+ P1Util.METHOD_END_INDEX;
					int urlEndIndexSpace = line.indexOf(P1Util.SPACE,
							urlStartIndex);
					int urlEndIndexQuestion = line.indexOf(P1Util.QUESTION,
							urlStartIndex);

					int urlEndIndex;
					if (urlEndIndexQuestion < P1Util.ZERO) {
						urlEndIndex = urlEndIndexSpace;
					} else if (urlEndIndexQuestion > urlEndIndexSpace) {
						urlEndIndex = urlEndIndexSpace;
					} else {
						urlEndIndex = urlEndIndexQuestion;
					}

					String url = line.substring(urlStartIndex, urlEndIndex);

					if (P1Util.isFilterd(method, url, filters) == false) {

						String ip = line.substring(P1Util.ZERO, ipIndex);
						Date lastAccessDate = accessMap.get(ip);

						Date accessDate = P1Util.parseTime(line.substring(
								dateStartIndex, dateEndIndex));

						if (lastAccessDate == null
								|| P1Util.checkAccessDate(lastAccessDate,
										accessDate)) {
							String key = P1Util.key(ip, url);
							Object[] result = resultMap.get(key);
							if (result == null) {
								result = new Object[4];
								result[0] = ip;
								result[1] = url;
								result[2] = accessDate;
								result[3] = 1;
								resultMap.put(key, result);
							} else {
								result[3] = Integer.valueOf(result[3]
										.toString()) + 1;
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
