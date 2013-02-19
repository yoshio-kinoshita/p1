package callable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import entity.Result;

public class CountMapCallable implements Callable<List<Entry<String, Result>>> {

	private List<Entry<String, Result>> accessEntries;

	public CountMapCallable(List<Entry<String, Result>> accessEntries) {
		this.accessEntries = accessEntries;
	}

	@Override
	public List<Entry<String, Result>> call() throws Exception {

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

		return countEntries;
	}

}
