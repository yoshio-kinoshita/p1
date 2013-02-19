package callable;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import entity.Result;

public class ToppageCallable implements Callable<Map<String, String> >{
	
	private List<Entry<String, Result>> accessEntries;

	public ToppageCallable(List<Entry<String, Result>> accessEntries) {
		this.accessEntries = accessEntries;
	}

	@Override
	public Map<String, String>  call() throws Exception {
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
		
		return topPageMap;
	}

}
