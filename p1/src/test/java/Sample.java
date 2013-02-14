//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.apache.commons.lang.time.StopWatch;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import util.P1Util;
//import callable.FileReadCallable;
//
//public class Sample {
//
//	private Logger logger = LoggerFactory.getLogger(Sample.class);
//
//	@Test
//	public void timeb() {
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		for (int i = 0; i < 10000; i++) {
//			int bin = 13;
//			if (bin == 0B00001101) {
//				System.out.println("true");
//			} else {
//				System.out.println("false");
//			}
//		}
//		stopWatch.stop();
//		logger.info("処理タイムは" + stopWatch.getTime());
//	}
//
//	@Test
//	public void timen() {
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		for (int i = 0; i < 10000; i++) {
//			int bin = 13;
//			if (bin == 13) {
//				System.out.println("true");
//			} else {
//				System.out.println("false");
//			}
//		}
//		stopWatch.stop();
//		logger.info("処理タイムは" + stopWatch.getTime());
//	}
//
//	@Test
//	public void regex() {
//
//		String s = "GET /maeyes/solution/genkakanri.html?lfcpid=2&gclid=CPimhf2n3a8CFUKEpAod0QiA_w HTTP/1.1";
//		Pattern p = Pattern
//				.compile("(GET|HEAD|POST)\\s{1}[-_.!~*\\'()a-zA-Z0-9;\\/:\\@&=+\\$,%#]+");
//		Matcher m = p.matcher(s);
//		System.out.println(m.find());
//		System.out.println(m.group());
//
//	}
//
//	@Test
//	public void regex2() {
//
//		String s = "GET /maeyes/solution/genkakanri.html?lfcpid=2&gclid=CPimhf2n3a8CFUKEpAod0QiA_w HTTP/1.1";
//		Pattern p = Pattern.compile("(GET|HEAD|POST)\\s{1}(/maeyes){1}");
//		Matcher m = p.matcher(s);
//		System.out.println(m.find());
//		System.out.println(m.group());
//
//	}
//
//	@Test
//	public void parse() {
//		String s = "27/May/2012:23:10:01";
//		SimpleDateFormat sdf = new SimpleDateFormat(P1Util.DATE_FOMAT,
//				Locale.US);
//		try {
//			System.out.println(sdf.parse(s));
//		} catch (ParseException e) {
//			logger.error("error", e);
//		}
//	}
//
//	@Test
//	public void multiFileRead() {
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//
//		FileReadCallable f1 = new FileReadCallable(
//				"src/test/resources/p1gp2_log1.txt", new ArrayList<String>());
//
//		ExecutorService ex1 = Executors.newSingleThreadExecutor();
//		Future<List<Object[]>> result1 = ex1.submit(f1);
//
//		try {
//			List<Object[]> list = result1.get();
//			System.out.println(list.size());
//		} catch (InterruptedException | ExecutionException e) {
//			logger.info("error", e);
//		}
//
//		stopWatch.stop();
//		System.out.println(stopWatch.getTime());
//		ex1.shutdown();
//	}
//
//	@Test
//	public void ip() {
//		String s = "60.32.3.172 - - [01/May/2012:04:10:03 +0900] ";
//		Matcher ipMater = P1Util.IP.matcher(s);
//		ipMater.find();
//		String ip = ipMater.group();
//
//		System.out.println(ip);
//
//	}
//
//	@Test
//	public void ip2() {
//		String s = "gw01.bbreak.jp - - [01/May/2012:04:10:03 +0900] ";
//		Matcher ipMater = P1Util.IP.matcher(s);
//		ipMater.find();
//		String ip = ipMater.group();
//
//		System.out.println(ip);
//	}
//
//}
