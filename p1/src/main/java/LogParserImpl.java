import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import util.P1Util;
import callable.FileReadCallable;

public class LogParserImpl implements LogParser {

	@Override
	public List<Object[]> parser(List<String> logfiles, List<String> filters) {

		logfiles = sortLogfiles(logfiles);

		ExecutorService ex = Executors.newFixedThreadPool(5);
		List<Object[]> resultList = new ArrayList<>();
		for (String logfile : logfiles) {
			FileReadCallable log = new FileReadCallable(logfile,
					new ArrayList<String>());
			ex.submit(log);
			
			try {
			for(Object[] o : list.get()) {
			String ip = (String)o[0];
			String url = (String)o[1];
			String date = (String )o[2];
			File file = new File();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		return resultList;

	}

	private List<String> sortLogfiles(List<String> logfiles) {

		List<Object[]> list = new ArrayList<>();
		for (String logfile : logfiles) {
			try (BufferedReader reader = new BufferedReader(new FileReader(
					new File(logfile)))) {

				String line = reader.readLine();
				Date date = P1Util.parseTime(line);
				list.add(new Object[] { date, logfile });

			} catch (IOException e) {
			}
		}

		Collections.sort(list, new FileComparator());

		List<String> sorted = new ArrayList<>();
		for (Object[] o : list) {
			sorted.add((String) o[1]);
		}

		return sorted;
	}

	private static class FileComparator implements Comparator<Object[]> {

		@Override
		public int compare(Object[] o1, Object[] o2) {

			Date d1 = (Date) o1[0];
			Date d2 = (Date) o2[0];

			return d1.compareTo(d2);
		}
	}

}
