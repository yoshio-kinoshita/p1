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

					line = line.replace(P1Util.MARK, "");

					String[] lineArray = line.split(P1Util.SPACE,
							P1Util.MAX_SPLIT);

					String ip = lineArray[0];
					Date accessDate = P1Util.parseTime(lineArray[3]);
					String method = lineArray[5];
					String url = lineArray[6];
					if (P1Util.isFilterd(method, url, filters) == false) {

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
