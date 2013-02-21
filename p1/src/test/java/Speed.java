import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.bbreak.p1gp02.api0047.P1Util;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;


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
		logfiles.add("src/test/resources/p1gp-test-111.txt");
		logfiles.add("src/test/resources/p1gp-test-112.txt");
		logfiles.add("src/test/resources/p1gp-test-113.txt");
		logfiles.add("src/test/resources/p1gp-test-114.txt");
		logfiles.add("src/test/resources/p1gp-test-115.txt");

		for (int i = 0; i < 10; i++) {
			StopWatch watch = new StopWatch();
			watch.start();
			for (String filename : logfiles) {

				StopWatch one = new StopWatch();
				one.start();

				try (BufferedReader reader = new BufferedReader(new FileReader(
						new File(filename)))) {
					String line;
					while ((line = reader.readLine()) != null) {

						int ipIndex = line.indexOf(" ") - 1;
						String ip = line.substring(0, ipIndex);

						int dateStartIndex = ipIndex + 7;
						int dateEndIndex = dateStartIndex + 20;
						String date = line.substring(dateStartIndex,
								dateEndIndex);

						int methodStartIndex = dateEndIndex + 9;
						int methodEndIndex = line
								.indexOf(" ", methodStartIndex);
						String method = line.substring(methodStartIndex,
								methodEndIndex);

						int urlStartIndex = methodEndIndex + 1;
						int urlEndIndex = line.indexOf(" ", urlStartIndex);
						String url = line.substring(urlStartIndex, urlEndIndex);
						// System.out
						// .println("ip:" + ip + "," + "date:" + date
						// + "," + "method:" + method + ","
						// + "url:" + url);
						// // Pattern ptn = Pattern.compile(P1Util.SPACE);
						// ptn.split(line, P1Util.MAX_SPLIT);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
				one.stop();
				System.out.println("one:" + one.getTime());

			}
			watch.stop();
			System.out.println(watch.getTime());

		}

	}

	@Test
	public void ifvsswitch() {

		StopWatch watch = new StopWatch();
		watch.start();
		String mm = null;
		for (int i = 0; i < 100000; i++) {
			mm = ifconvertMMType("");

		}

		System.out.println(mm);
		watch.stop();
		System.out.println(watch.getTime());

		watch = new StopWatch();
		watch.start();

		for (int i = 0; i < 100000; i++) {
			mm = switchconvertMMType("");
		}
		System.out.println(mm);
		watch.stop();
		System.out.println(watch.getTime());

	}

	private static String ifconvertMMType(String value) {
		if (value.equals("Jan"))
			return "01";
		if (value.equals("Feb"))
			return "02";
		if (value.equals("Mar"))
			return "03";
		if (value.equals("Apr"))
			return "04";
		if (value.equals("May"))
			return "05";
		if (value.equals("Jun"))
			return "06";
		if (value.equals("Jul"))
			return "07";
		if (value.equals("Aug"))
			return "08";
		if (value.equals("Sep"))
			return "09";
		if (value.equals("Oct"))
			return "10";
		if (value.equals("Nov"))
			return "11";
		else
			return "12";
	}

	private static String switchconvertMMType(String value) {
		switch (value) {
		case "Jan":
			return "01";
		case "Feb":
			return "02";
		case "Mar":
			return "03";
		case "Apr":
			return "04";
		case "May":
			return "05";
		case "Jun":
			return "06";
		case "Jul":
			return "07";
		case "Aug":
			return "08";
		case "Sep":
			return "09";
		case "Oct":
			return "10";
		case "Nov":
			return "11";
		default:
			return "12";
		}
	}

	@Test
	public void stringint() {

		String a = "1";

		StopWatch watch = new StopWatch();
		watch.start();
		for (int i = 0; i < 2000000; i++) {
			int x = Integer.parseInt(a);

		}

		watch.stop();
		System.out.println("parseint : " + watch.getTime());

		watch = new StopWatch();
		watch.start();
		for (int i = 0; i < 2000000; i++) {
			int x = Integer.valueOf(a);
		}

		watch.stop();
		System.out.println("valueof : " + watch.getTime());

		watch = new StopWatch();
		watch.start();
		for (int i = 0; i < 2000000; i++) {
			int x = new Integer(a);
		}

		watch.stop();
		System.out.println("constractor : " + watch.getTime());

		System.out.println("------------");

		watch = new StopWatch();
		watch.start();
		for (int i = 0; i < 2000000; i++) {
			byte x = Byte.parseByte(a);
		}

		watch.stop();
		System.out.println("parseint : " + watch.getTime());

		watch = new StopWatch();
		watch.start();
		for (int i = 0; i < 2000000; i++) {
			byte x = Byte.valueOf(a);
		}

		watch.stop();
		System.out.println("valueof : " + watch.getTime());

		watch = new StopWatch();
		watch.start();
		for (int i = 0; i < 2000000; i++) {
			byte x = new Byte(a);
		}

		watch.stop();
		System.out.println("constractor : " + watch.getTime());
		
	}


}
