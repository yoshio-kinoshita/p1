package util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;

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

}
