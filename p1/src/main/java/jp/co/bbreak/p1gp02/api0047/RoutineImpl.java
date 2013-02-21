package jp.co.bbreak.p1gp02.api0047;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.bbreak.p1gp02.application.Result;
import jp.co.bbreak.p1gp02.application.Routine;

public class RoutineImpl implements Routine {

	@Override
	public List<Result> analyze(List<File> files, List<String> filters)
			throws Exception {

		Map<String, TmpResult> resultMap = LogReader.read(files,
				filters.toArray(new String[0]));

		List<Entry<String, TmpResult>> accessEntries = new ArrayList<>(
				resultMap.entrySet());

		// トップページ設定

		// ip - cnt(降順) - url(昇順)でソート
		Collections.sort(accessEntries, new Comparator<Object>() {
			public int compare(Object obj1, Object obj2) {
				Map.Entry<String, TmpResult> ent1 = (Map.Entry<String, TmpResult>) obj1;
				Map.Entry<String, TmpResult> ent2 = (Map.Entry<String, TmpResult>) obj2;
				TmpResult val1 = (TmpResult) ent1.getValue();
				TmpResult val2 = (TmpResult) ent2.getValue();

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
		for (Entry<String, TmpResult> entries : accessEntries) {
			TmpResult result = entries.getValue();
			String ip = result.getIp();
			String toppage = topPageMap.get(ip);
			if (toppage == null) {
				topPageMap.put(ip, result.getUrl());
			}
		}
		// ip - 初回アクセス日 - url(昇順)でソート
		Collections.sort(accessEntries, new Comparator<Object>() {
			public int compare(Object obj1, Object obj2) {
				Map.Entry<String, TmpResult> ent1 = (Map.Entry<String, TmpResult>) obj1;
				Map.Entry<String, TmpResult> ent2 = (Map.Entry<String, TmpResult>) obj2;
				TmpResult val1 = (TmpResult) ent1.getValue();
				TmpResult val2 = (TmpResult) ent2.getValue();

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
		Map<String, TmpResult> countMap = new HashMap<>();
		for (Entry<String, TmpResult> entries : accessEntries) {
			TmpResult result = entries.getValue();
			String ip = result.getIp();
			TmpResult countResult = countMap.get(ip);
			if (countResult == null) {
				countResult = result;
			} else {
				countResult
						.setCount(countResult.getCount() + result.getCount());
			}
			countMap.put(ip, countResult);
		}

		// cnt(降順) - アクセス日(昇順) - ip(昇順) でソート
		List<Entry<String, TmpResult>> countEntries = new ArrayList<>(
				countMap.entrySet());
		Collections.sort(countEntries, new Comparator<Object>() {
			public int compare(Object obj1, Object obj2) {
				Map.Entry<String, TmpResult> ent1 = (Map.Entry<String, TmpResult>) obj1;
				Map.Entry<String, TmpResult> ent2 = (Map.Entry<String, TmpResult>) obj2;
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

		int i = 1;
		List<Result> resultList = new ArrayList<>();
		for (Entry<String, TmpResult> entry : countEntries) {

			if (i > 20) {
				break;
			}
			TmpResult tmpResult = entry.getValue();

			if (Arrays.binarySearch(P1Util.RESULT_INDEX, i) >= 0) {

				Result result = new Result();
				result.setIpAddress(tmpResult.getIp());
				result.setAccessCount(tmpResult.getCount());
				result.setEntryPage(topPageMap.get(tmpResult.getIp()));
				result.setAccessDate(tmpResult.getFirstAccessDate());
				result.setRank(i);
				resultList.add(result);
			}

			i++;
		}
		return resultList;
	}

	@Override
	public String getUserName() {
		return "木下喜雄";
	}

}
