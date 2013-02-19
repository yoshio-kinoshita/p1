import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

public class MainTest {

	@Test
	public void stringCompare() {
		String s1 = "/webmail";
		String s2 = "/access";

		List<String> l = new ArrayList<>();
		l.add(s1);
		l.add(s2);

		Collections.sort(l, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});

		System.out.println(l);
	}

	@Test
	public void dateCompare() {
		Date s1 = new Date();
		Date s2 = DateUtils.addMinutes(s1, 5);

		List<Date> l = new ArrayList<>();
		l.add(s1);
		l.add(s2);

		Collections.sort(l, new Comparator<Date>() {

			@Override
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});

		System.out.println(l);
	}

}
