package callable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import entity.AccessLog;

public class LogReader {

	private static int mb = 1024 * 1024;

	public static List<AccessLog> read(List<String> filenames,
			List<String> filters) {
		List<AccessLog> accessloglist = new ArrayList<>();
		List<String> lineList = new ArrayList<>();

		ExecutorService ex = Executors.newFixedThreadPool(10);
		List<Future<List<AccessLog>>> submitList = new ArrayList<>();
		for (String filename : filenames) {

			try (BufferedReader reader = new BufferedReader(new FileReader(
					new File(filename)))) {
				String line;

				int i = 0;
				while ((line = reader.readLine()) != null) {

					if (i < 10000) {
						lineList.add(line);
						i++;

					} else {
						LogAnalisysCallable call = new LogAnalisysCallable(
								lineList, filters);
						submitList.add(ex.submit(call));
						lineList = new ArrayList<>();
						i = 0;

						long fm = Runtime.getRuntime().freeMemory();
						long tm = Runtime.getRuntime().totalMemory();
						
						System.out.println("total: " + tm / mb + " ; free: "
								+ fm / mb + " ; used: " + (tm - fm) / mb);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (Future<List<AccessLog>> futures : submitList) {
			try {
				accessloglist.addAll(futures.get());
			} catch (InterruptedException | ExecutionException e) {
			}
		}
		return accessloglist;
	}
}
