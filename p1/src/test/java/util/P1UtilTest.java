package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.bbreak.p1gp02.api0047.P1Util;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.StopWatch;
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
		String EXTENSIONS[] = { "CGI", "HTM", "PHP", "HTML", "cgi", "htm",
				"html", "php" };
		Arrays.sort(EXTENSIONS);

		for (String s : EXTENSIONS) {
			System.out.println(s);
		}
	}

	@Test
	public void parseTime() {

		for (int y = 0; y < 100; y++) {
			StopWatch watch = new StopWatch();
			watch.start();
			for (int i = 0; i < 1000000; i++) {
				P1Util.parseTime("01/Jan/2012:04:31:20");
			}
			watch.stop();
			System.out.println(watch.getTime());
		}
	}

	@Test
	public void isfilterd() {

		String url = "/maeyes/event/seminar070724.htmll_autoriza.htmtmTES-070724.pdf-q5366827.htmlAA";

		int lastindexslash = url.lastIndexOf(P1Util.SLASH);

		url = url.substring(lastindexslash);
		int lastindex = url.lastIndexOf(P1Util.COLON);

		System.out.println("slash:" + lastindexslash + " colon:" + lastindex);

		// assertFalse(P1Util.isFilterd("GET", url,

	}
}
