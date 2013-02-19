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
	public void match() {
		assertTrue("GET".matches(P1Util.FILTER_BASE));
	}

	@Test
	public void parseTime() {
		System.out.println(P1Util.parseTime("01/Jan/2012:04:31:20"));
		System.out.println(P1Util.parseTime("01/Feb/2012:04:31:20"));
		System.out.println(P1Util.parseTime("01/Mar/2012:04:31:20"));
		System.out.println(P1Util.parseTime("01/Apr/2012:04:31:20"));
		System.out.println(P1Util.parseTime("01/Jun/2012:04:31:20"));
		System.out.println(P1Util.parseTime("01/May/2012:04:31:20"));
		System.out.println(P1Util.parseTime("02/May/2012:20:34:17"));
	}
}
