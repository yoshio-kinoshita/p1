import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.time.StopWatch;

import entity.Result;

public class Main {

	public static void main(String args[]) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<String> logfiles = new ArrayList<String>();
		logfiles.add("src/test/resources/p1gp2_log1.txt");
		logfiles.add("src/test/resources/p1gp2_log2.txt");
		logfiles.add("src/test/resources/p1gp2_log3.txt");
		logfiles.add("src/test/resources/p1gp2_log4.txt");
		logfiles.add("src/test/resources/p1gp2_log5.txt");

		LogParser logParser = new LogParserImpl();

		Map<String, Result> resultMap = logParser.parser(logfiles,
				new ArrayList<String>());

		stopWatch.stop();
		System.out.println("resultMap:" + stopWatch.getTime());

		stopWatch.reset();
		stopWatch.start();

		List<Entry<String, Result>> resultEntries = new ArrayList<>(
				resultMap.entrySet());

		// ip - cnt - urlでソート
		Collections.sort(resultEntries, new Comparator<Object>() {
			public int compare(Object obj1, Object obj2) {
				Map.Entry<String, Result> ent1 = (Map.Entry<String, Result>) obj1;
				Map.Entry<String, Result> ent2 = (Map.Entry<String, Result>) obj2;
				Result val1 = (Result) ent1.getValue();
				Result val2 = (Result) ent2.getValue();

				int ip = val1.getIp().compareTo(val2.getIp());
				int cnt = val1.getCount().compareTo(val2.getCount());

				if (ip != 0) {
					return ip;
				} else if (cnt != 0) {
					return -1 * cnt;
				} else {
					return val1.getUrl().compareTo(val1.getUrl());
				}
			}
		});

		stopWatch.stop();
		System.out.println("ip - cnt - urlでソート:" + stopWatch.getTime());

		stopWatch.reset();
		stopWatch.start();

		// ipアドレスごとにResult生成
		Map<String, Result> countMap = new HashMap<>();
		for (Entry<String, Result> entries : resultEntries) {
			Result result = entries.getValue();
			String ip = result.getIp();
			Result countResult = countMap.get(ip);
			if (countResult == null) {
				countResult = result;
			} else {
				countResult
						.setCount(countResult.getCount() + result.getCount());
			}
			countMap.put(ip, countResult);
		}

		stopWatch.stop();
		System.out.println("ipアドレスごとにResult生成:" + stopWatch.getTime());

		stopWatch.reset();
		stopWatch.start();

		List<Entry<String, Result>> countEntries = new ArrayList<>(
				countMap.entrySet());
		Collections.sort(countEntries, new Comparator<Object>() {
			public int compare(Object obj1, Object obj2) {
				Map.Entry<String, Result> ent1 = (Map.Entry<String, Result>) obj1;
				Map.Entry<String, Result> ent2 = (Map.Entry<String, Result>) obj2;
				Integer val1 = (Integer) ent1.getValue().getCount();
				Integer val2 = (Integer) ent2.getValue().getCount();

				int cnt = val1.compareTo(val2);

				if (cnt != 0) {
					return -1 * cnt;
				} else {
					return ent1.getValue().getFirstAccessDate()
							.compareTo(ent2.getValue().getFirstAccessDate());
				}
			}
		});

		stopWatch.stop();
		System.out.println("ソート:" + stopWatch.getTime());

		for (Entry<String, Result> entry : countEntries) {
			Result result = entry.getValue();
			System.out.println(result.getIp() + "," + result.getCount() + ","
					+ result.getFirstAccessDate() + "," + result.getUrl());
		}

	}
}
