import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.time.StopWatch;

import callable.LogReader;
import entity.Result;

public class Main {

	public static void start(String args[]) {

		StopWatch totalstopWatch = new StopWatch();
		totalstopWatch.start();
		List<String> logfiles = new ArrayList<String>();
		// logfiles.add("src/test/resources/p1gp-test-111.txt");
		// logfiles.add("src/test/resources/p1gp-test-112.txt");
		// logfiles.add("src/test/resources/p1gp-test-113.txt");
		// logfiles.add("src/test/resources/p1gp-test-114.txt");
		// logfiles.add("src/test/resources/p1gp-test-115.txt");
		logfiles.add("src/test/resources/p1gp2_log1.txt");
		logfiles.add("src/test/resources/p1gp2_log2.txt");
		logfiles.add("src/test/resources/p1gp2_log3.txt");
		logfiles.add("src/test/resources/p1gp2_log4.txt");
		logfiles.add("src/test/resources/p1gp2_log5.txt");

		List<String> filters = new ArrayList<>();
		// filters.add("/webmail/");

		Map<String, Result> resultMap = LogReader.read(logfiles,
				filters.toArray(new String[0]));

		List<Entry<String, Result>> accessEntries = new ArrayList<>(
				resultMap.entrySet());

		// トップページ設定

		// ip - cnt(降順) - url(昇順)でソート
		Collections.sort(accessEntries, new Comparator<Object>() {
			public int compare(Object obj1, Object obj2) {
				Map.Entry<String, Result> ent1 = (Map.Entry<String, Result>) obj1;
				Map.Entry<String, Result> ent2 = (Map.Entry<String, Result>) obj2;
				Result val1 = (Result) ent1.getValue();
				Result val2 = (Result) ent2.getValue();

				int ip = val1.getIp().compareTo(val2.getIp());

				if (ip != 0) {
					return ip;
				} else {
					int cnt = val2.getCount() - val1.getCount();
					if (cnt != 0) {
						return cnt;
					} else {
						return val1.getUrl().compareTo(val2.getUrl());
					}
				}
			}
		});

		// ipアドレスごとにトップページmap生成
		Map<String, String> topPageMap = new HashMap<>();
		for (Entry<String, Result> entries : accessEntries) {
			Result result = entries.getValue();
			String ip = result.getIp();
			String toppage = topPageMap.get(ip);
			if (toppage == null) {
				topPageMap.put(ip, result.getUrl());
			}
		}
		// ip - 初回アクセス日 - url(昇順)でソート
		Collections.sort(accessEntries, new Comparator<Object>() {
			public int compare(Object obj1, Object obj2) {
				Map.Entry<String, Result> ent1 = (Map.Entry<String, Result>) obj1;
				Map.Entry<String, Result> ent2 = (Map.Entry<String, Result>) obj2;
				Result val1 = (Result) ent1.getValue();
				Result val2 = (Result) ent2.getValue();

				int ip = val1.getIp().compareTo(val2.getIp());

				if (ip != 0) {
					return ip;
				} else {
					int date = val1.getFirstAccessDate().compareTo(
							val2.getFirstAccessDate());
					if (date != 0) {
						return date;
					} else {
						return val1.getUrl().compareTo(val2.getUrl());
					}
				}
			}
		});

		// ipアドレスごとにカウントmap生成
		Map<String, Result> countMap = new HashMap<>();
		for (Entry<String, Result> entries : accessEntries) {
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

		// cnt(降順) - アクセス日(昇順) - ip(昇順) でソート
		List<Entry<String, Result>> countEntries = new ArrayList<>(
				countMap.entrySet());
		Collections.sort(countEntries, new Comparator<Object>() {
			public int compare(Object obj1, Object obj2) {
				Map.Entry<String, Result> ent1 = (Map.Entry<String, Result>) obj1;
				Map.Entry<String, Result> ent2 = (Map.Entry<String, Result>) obj2;
				int val1 = ent1.getValue().getCount();
				int val2 = ent2.getValue().getCount();

				int cnt = val2 - val1;

				if (cnt != 0) {
					return cnt;
				} else {
					int date = ent1.getValue().getFirstAccessDate()
							.compareTo(ent2.getValue().getFirstAccessDate());
					if (date != 0) {
						return date;
					} else {
						return ent1.getValue().getIp()
								.compareTo(ent2.getValue().getIp());
					}
				}
			}
		});

		totalstopWatch.stop();
		System.out.println("total:" + totalstopWatch.getTime());

		for (Entry<String, Result> entry : countEntries) {
			Result result = entry.getValue();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			System.out.println(result.getIp() + "," + result.getCount() + ","
					+ sdf.format(result.getFirstAccessDate()) + ","
					+ topPageMap.get(result.getIp()));
		}

	}
}
