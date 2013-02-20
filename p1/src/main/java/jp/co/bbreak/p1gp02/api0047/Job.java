package jp.co.bbreak.p1gp02.api0047;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import jp.co.bbreak.p1gp02.application.Result;

public class Job {

	public static List<Result> start(List<File> logfiles, List<String> filters) {
		
		Map<String, TmpResult> resultMap = LogReader.read(logfiles,
				filters.toArray(new String[0]));

		List<Entry<String, TmpResult>> accessEntries = new ArrayList<>(
				resultMap.entrySet());

		ExecutorService e1 = Executors.newSingleThreadExecutor();
		ToppageCallable task1 = new ToppageCallable(accessEntries);

		Future<Map<String, String>> f1 = e1.submit(task1);

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		CountMapCallable task2 = new CountMapCallable(accessEntries);

		Future<List<Entry<String, TmpResult>>> f2 = executorService
				.submit(task2);
		List<Entry<String, TmpResult>> countEntries = null;
		Map<String, String> topPageMap = null;
		try {
			topPageMap = f1.get();
			countEntries = f2.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		e1.shutdown();
		executorService.shutdown();

		List<Result> resultList = new ArrayList<>();
		int rank = 1;
		for (Entry<String, TmpResult> entry : countEntries) {
			TmpResult tmpresult = entry.getValue();
			Result result = new Result();
			result.setAccessCount(tmpresult.getCount());
			result.setAccessDate(tmpresult.getFirstAccessDate());
			result.setEntryPage(topPageMap.get(tmpresult.getIp()));
			result.setIpAddress(tmpresult.getIp());
			result.setRank(rank);
			rank++;
			resultList.add(result);
		}

		return resultList;

	}
}
