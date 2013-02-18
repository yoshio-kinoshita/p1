import java.util.Date;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;

import util.P1Util;

public class Speed {

	private static String log = "60.32.3.172 - - [01/May/2012:04:10:03 +0900] \"HEAD / HTTP/1.0\" 200 392 \"-\" \"Webmin";

	@Test
	public void regix() {

		StopWatch watch = new StopWatch();
		watch.start();

		for (int i = 0; i < 100000; i++) {
			String ip = P1Util.parseIp(log);
			String url = P1Util.parseUrl(log);
			Date d = P1Util.parseTime(log);
		}

		watch.stop();
		System.out.println(watch.getTime());
	}

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

}
