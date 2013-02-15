import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

		Map<String, Result> map = logParser.parser(logfiles,
				new ArrayList<String>());

		List<Entry<String, Result>> entries = new ArrayList<>(map.entrySet());
		Collections.sort(entries, new Comparator<Object>() {
			public int compare(Object obj1, Object obj2) {
				Map.Entry<String, Result> ent1 = (Map.Entry<String, Result>) obj1;
				Map.Entry<String, Result> ent2 = (Map.Entry<String, Result>) obj2;
				Result val1 = (Result) ent1.getValue();
				Result val2 = (Result) ent2.getValue();

				int cnt = val2.getCount().compareTo(val1.getCount());

				if (cnt != 0) {
					return -cnt;
				} else {
					return val1.getFirstAccessDate().compareTo(
							val1.getFirstAccessDate());
				}
			}
		});

		for (Entry<String, Result> entry : entries) {
			Result result = entry.getValue();
			System.out.println("count:" + result.getCount() + " ip:"
					+ result.getIp() + " url:" + result.getUrl() + " access:"
					+ result.getFirstAccessDate());
		}

		System.out.println(map.size());

		stopWatch.stop();
		System.out.println(stopWatch.getTime());

	}
}
