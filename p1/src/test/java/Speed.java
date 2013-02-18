import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;

import util.P1Util;

public class Speed {

	private static String log = "60.32.3.172 - - [01/May/2012:04:10:03 +0900] \"HEAD / HTTP/1.0\" 200 392 \"-\" \"Webmin";

	@Test
	public void string() {
		StopWatch watch = new StopWatch();

		watch.start();
		for (int i = 0; i < 100000; i++) {
			String[] logs = log.split("\\s");
			String ip = logs[0];
			String date = logs[3];
			String url = logs[6];

		}
		watch.stop();
		System.out.println(watch.getTime());

	}

	@Test
	public void read() {

		List<String> logfiles = new ArrayList<String>();
		logfiles.add("src/test/resources/p1gp2_log1.txt");
		logfiles.add("src/test/resources/p1gp2_log2.txt");
		logfiles.add("src/test/resources/p1gp2_log3.txt");
		logfiles.add("src/test/resources/p1gp2_log4.txt");
		logfiles.add("src/test/resources/p1gp2_log5.txt");

		for (int i = 0; i < 100; i++) {
			StopWatch watch = new StopWatch();
			watch.start();
			for (String filename : logfiles) {

				try (BufferedReader reader = new BufferedReader(new FileReader(
						new File(filename)))) {
					String line;
					while ((line = reader.readLine()) != null) {
						Pattern ptn = Pattern.compile(P1Util.SPACE);
						ptn.split(line, P1Util.MAX_SPLIT);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			watch.stop();
			System.out.println(watch.getTime());

		}

	}

}
