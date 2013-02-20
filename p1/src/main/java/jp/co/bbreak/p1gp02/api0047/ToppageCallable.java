package jp.co.bbreak.p1gp02.api0047;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

public class ToppageCallable implements Callable<List<Map<String, Object[]>>> {

	private List<Entry<String, TmpResult>> accessEntries;

	public ToppageCallable(List<Entry<String, TmpResult>> accessEntries) {
		this.accessEntries = accessEntries;
	}

	@Override
	public Hashtable<String, String> call() throws Exception {
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
		Hashtable<String, String> topPageMap = new Hashtable<>();
		for (Entry<String, TmpResult> entries : accessEntries) {
			TmpResult result = entries.getValue();
			String ip = result.getIp();
			String toppage = topPageMap.get(ip);
			if (toppage == null) {
				topPageMap.put(ip, result.getUrl());
			}
		}

		return topPageMap;
	}

}
