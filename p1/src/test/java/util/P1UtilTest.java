package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

public class P1UtilTest {

	@Test
	public void checkAccessDate() throws InterruptedException {

		Date d1 = new Date();
		Date d2 = d1;

		d2 = DateUtils.addSeconds(d2, 10);
		assertFalse(P1Util.checkAccessDate(d1, d2));

		d2 = DateUtils.addSeconds(d2, 180);
		assertFalse(P1Util.checkAccessDate(d1, d2));

		d2 = d1;
		d2 = DateUtils.addSeconds(d2, 300);
		assertFalse(P1Util.checkAccessDate(d1, d2));

		d2 = d1;
		d2 = DateUtils.addSeconds(d2, 301);
		assertTrue(P1Util.checkAccessDate(d1, d2));
	}

	@Test
	public void parseUrl() {
		String url = "GET /maeyes/solution/genkakanri.html?lfcpid=2&gclid=CPimhf2n3a8CFUKEpAod0QiA_w HTTP/1.1";
		assertEquals("/maeyes/solution/genkakanri.html", P1Util.parseUrl(url));

		url = "GET /maeyes/solution/genkakanri.htm?lfcpid=2&gclid=CPimhf2n3a8CFUKEpAod0QiA_w HTTP/1.1";
		assertEquals("/maeyes/solution/genkakanri.htm", P1Util.parseUrl(url));

		url = "GET /maeyes/solution/genkakanri.gif?lfcpid=2&gclid=CPimhf2n3a8CFUKEpAod0QiA_w HTTP/1.1";
		assertEquals("", P1Util.parseUrl(url));

		url = "GET /maeyes/support/hrmsc/plugins/jp.co.bbreak.maeyes.client_2.3.0.jar HTTP/1.0";
		assertEquals("", P1Util.parseUrl(url));

		url = "HEAD / HTTP/1.0";
		assertEquals("/", P1Util.parseUrl(url));

		url = "HEAD /hogehoge/ HTTP/1.0";
		assertEquals("/hogehoge/", P1Util.parseUrl(url));

	}

	@Test
	public void sysout() {
		System.out.println(0x493e0L);
	}

	@Test
	public void arraySort() {
		String EXTENSIONS[] = { "cgi", "php", "htm", "html", };
		Arrays.sort(EXTENSIONS);

		for (String s : EXTENSIONS) {
			System.out.println(s);
		}

	}

	@Test
	public void sortLogfiles() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		List<String> logfiles = new ArrayList<String>();
		logfiles.add("src/test/resources/p1gp2_log5.txt");
		logfiles.add("src/test/resources/p1gp2_log4.txt");
		logfiles.add("src/test/resources/p1gp2_log3.txt");
		logfiles.add("src/test/resources/p1gp2_log2.txt");
		logfiles.add("src/test/resources/p1gp2_log1.txt");

		logfiles = P1Util.sortLogfiles(logfiles);

		assertEquals(logfiles.get(0), "src/test/resources/p1gp2_log1.txt");
		assertEquals(logfiles.get(1), "src/test/resources/p1gp2_log2.txt");
		assertEquals(logfiles.get(2), "src/test/resources/p1gp2_log3.txt");
		assertEquals(logfiles.get(3), "src/test/resources/p1gp2_log4.txt");
		assertEquals(logfiles.get(4), "src/test/resources/p1gp2_log5.txt");

	}

	@Test
	public void isFilterd() {

		String logLine = "GET /maeyes/solution/genkakanri.html?lfcpid=2&gclid=CPimhf2n3a8CFUKEpAod0QiA_w HTTP/1.1";

		List<String> filters = new ArrayList<>();
		assertFalse(P1Util.isFilterd(logLine, filters));
	}

}
