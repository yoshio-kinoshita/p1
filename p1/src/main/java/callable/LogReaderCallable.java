package callable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import util.P1Util;
import entity.Result;

public class LogReaderCallable implements Callable<Map<String, Result>> {

	private String filename;
	private String[] filters;
	private int fileNo;

	public LogReaderCallable(String filename, String[] filters, int fileNo) {
		this.filename = filename;
		this.filters = filters;
		this.fileNo = fileNo;
	}

	@Override
	public Map<String, Result> call() throws Exception {
		Map<String, Result> resultMap = new HashMap<>();
		Map<String, Date> accessMap = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(
				new File(filename)))) {
			String line;

			while ((line = reader.readLine()) != null) {

				int ipIndex = line.indexOf(P1Util.SPACE);
				int dateStartIndex = ipIndex + P1Util.DATE_START_INDEX;
				int dateEndIndex = dateStartIndex + P1Util.DATE_END_INDEX;

				int methodStartIndex = dateEndIndex + P1Util.METHOD_START_INDEX;
				int methodEndIndex = line.indexOf(P1Util.SPACE,
						methodStartIndex);
				String method = line
						.substring(methodStartIndex, methodEndIndex);

				int urlStartIndex = methodEndIndex + P1Util.METHOD_END_INDEX;
				int urlEndIndexSpace = line
						.indexOf(P1Util.SPACE, urlStartIndex);
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
						Result result = resultMap.get(key);
						if (result == null) {
							result = new Result();
							result.setIp(ip);
							result.setUrl(url);
							if (accessDate == null) {
								System.out.println("null");
							}
							result.setFirstAccessDate(accessDate);
							result.setLastAccessDate(accessDate);
							result.setCount(1);
							result.setFileNo(fileNo);
							resultMap.put(key, result);
						} else {
							result.setCount(result.getCount() + 1);
							result.setLastAccessDate(accessDate);
							resultMap.put(key, result);
						}
						accessMap.put(ip, accessDate);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultMap;
	}
}
