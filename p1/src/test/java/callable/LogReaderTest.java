package callable;

import static org.junit.Assert.*;

import org.junit.Test;

import util.P1Util;

public class LogReaderTest {

	@Test
	public void parse() {

//		String line = "60.32.3.172 - - [01/May/2012:12:48:22 +0900] \"GET /webmail/src/right_main.php?PG_SHOWALL=0&sort=0&startMessage=1&mailbox=INBOX HTTP/1.1\" 200 10192 \"http://www.bbreak.co.jp/webmail/src/left_main.php\" \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.168 Safari/535.19\"";
		// String line =
		// "60.32.3.172 - - [01/May/2012:12:48:22 +0900] \"GET /webmail/src/right_main.php HTTP/1.1\" 200 10192 \"http://www.bbreak.co.jp/webmail/src/left_main.php\" \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.168 Safari/535.19\"";
		// String line =
		// "122.209.68.27 - - [03/May/2012:23:00:56 +0900] \"GET /webmail/src/signout.php HTTP/1.1\" 200 1431 \"http://www.bbreak.co.jp/webmail/src/right_main.php?PG_SHOWALL=0&sort=0&startMessage=1&mailbox=INBOX\" \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 1.1.4322; YTB730)\"";
		// String line =
		// "60.32.3.172 - - [03/May/2012:23:05:02 +0900] \"HEAD / HTTP/1.0\" 200 392 \"-\" \"Webmin\"";
		String line = "61.111.15.80 - - [27/May/2012:16:04:09 +0900] \"GET /maeyes/event/seminar070724.htmlsya_070724.html/jiazaidongbei.html2007/070724HHN_Online_Weber&domain=HHNMAG HTTP/1.0\" 404 887 \"-\" \"http://www.checkprivacy.or.kr:6600/RS/PRIVACY_ENFAQ.jsp\"";


		int ipIndex = line.indexOf(P1Util.SPACE);
		int dateStartIndex = ipIndex + 6;
		int dateEndIndex = dateStartIndex + 20;

		int methodStartIndex = dateEndIndex + 9;
		int methodEndIndex = line.indexOf(P1Util.SPACE, methodStartIndex);
		String method = line.substring(methodStartIndex, methodEndIndex);

		int urlStartIndex = methodEndIndex + 1;

		int urlEndIndexSpace = line.indexOf(P1Util.SPACE, urlStartIndex);
		int urlEndIndexQuestion = line.indexOf(P1Util.QUESTION, urlStartIndex);

		int urlEndIndex;
		if (urlEndIndexQuestion < 0) {
			urlEndIndex = urlEndIndexSpace;
		} else if (urlEndIndexQuestion > urlEndIndexSpace) {
			urlEndIndex = urlEndIndexSpace;
		} else {
			urlEndIndex = urlEndIndexQuestion;
		}

		System.out.println("url:" + line.substring(urlStartIndex, urlEndIndex));
		System.out.println("ip:" + line.substring(0, ipIndex));
		System.out.println("date:"
				+ line.substring(dateStartIndex, dateEndIndex));
		System.out.println("method:" + method);
	}
}
