import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.time.StopWatch;

import callable.CountMapCallable;
import callable.LogReader;
import callable.ToppageCallable;
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

		ExecutorService e1 = Executors.newSingleThreadExecutor();
		ToppageCallable task1 = new ToppageCallable(accessEntries);

		Future<Map<String, String>> f1 = e1.submit(task1);

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		CountMapCallable task2 = new CountMapCallable(accessEntries);

		Future<List<Entry<String, Result>>> f2 = executorService.submit(task2);
		List<Entry<String, Result>> countEntries = null;
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
