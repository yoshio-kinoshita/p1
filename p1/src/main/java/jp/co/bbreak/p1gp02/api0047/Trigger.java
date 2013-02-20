package jp.co.bbreak.p1gp02.api0047;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;

import jp.co.bbreak.p1gp02.application.Result;

public class Trigger {

	public static void main(String[] args) {

		List<File> logfiles = new ArrayList<File>();
		// logfiles.add("src/test/resources/p1gp-test-111.txt");
		// logfiles.add("src/test/resources/p1gp-test-112.txt");
		// logfiles.add("src/test/resources/p1gp-test-113.txt");
		// logfiles.add("src/test/resources/p1gp-test-114.txt");
		// logfiles.add("src/test/resources/p1gp-test-115.txt");
		logfiles.add(new File("src/test/resources/p1gp2_log1.txt"));
		logfiles.add(new File("src/test/resources/p1gp2_log2.txt"));
		logfiles.add(new File("src/test/resources/p1gp2_log3.txt"));
		logfiles.add(new File("src/test/resources/p1gp2_log4.txt"));
		logfiles.add(new File("src/test/resources/p1gp2_log5.txt"));

		List<String> filters = new ArrayList<>();
		// filters.add("/webmail/");

		for (int i = 0; i < 1; i++) {
			StopWatch watch = new StopWatch();
			watch.start();
			List<Result> l = Job.start(logfiles, filters);
			watch.stop();
			System.out.println(watch.getTime());
			
		}
	}
}
