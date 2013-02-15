import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import entity.AccessLog;

public class MemoryTest {

	private static int mb = 1024 * 1024;

	@Test
	public void string() {

		List<String> l = new ArrayList<>();

		String s = "あ";
		int i = 0;
		while (true) {

			if (i > 10000) {
				long fm = Runtime.getRuntime().freeMemory();
				long tm = Runtime.getRuntime().totalMemory();

				System.out.println("list.size:" + l.size() + "件; " + "total: "
						+ tm / mb + " ; free: " + fm / mb + " ; used: "
						+ (tm - fm) / mb);

				i = 0;
			}
			i++;
			l.add(s);
		}
	}

	@Test
	public void accesslog() {

		List<AccessLog> l = new ArrayList<>();

		int i = 0;
		while (true) {

			AccessLog log = new AccessLog();
			log.setIp("192.168.1.100");
			log.setUrl("/maeyes/test/object/test");
			log.setAccessDate("2012101010101010");

			if (i > 10000) {
				long fm = Runtime.getRuntime().freeMemory();
				long tm = Runtime.getRuntime().totalMemory();

				System.out.println("list.size:" + l.size() + "件; " + "total: "
						+ tm / mb + " ; free: " + fm / mb + " ; used: "
						+ (tm - fm) / mb);

				i = 0;
			}
			i++;
			l.add(log);
		}
	}

	@Test
	public void array() {

		List<Object[]> l = new ArrayList<>();

		int i = 0;
		while (true) {

			Object[] o = new Object[] { "192.168.1.100",
					"/maeyes/test/object/test", "2012-May-12-12-12" };

			if (i > 10000) {
				long fm = Runtime.getRuntime().freeMemory();
				long tm = Runtime.getRuntime().totalMemory();

				System.out.println("list.size:" + l.size() + "件; " + "total: "
						+ tm / mb + " ; free: " + fm / mb + " ; used: "
						+ (tm - fm) / mb);

				i = 0;
			}
			i++;
			l.add(o);
		}
	}
	
	@Test
	public void array2() {

		Object[] o2 = new Object[600000];

		int i = 0;
		int index = 0;
		while (true) {

			Object[] o = new Object[] { "192.168.1.100",
					"/maeyes/test/object/test", new Date() };

			if (i > 10000) {
				long fm = Runtime.getRuntime().freeMemory();
				long tm = Runtime.getRuntime().totalMemory();

				System.out.println("o2.size:" + o2.length + "件; " + "total: "
						+ tm / mb + " ; free: " + fm / mb + " ; used: "
						+ (tm - fm) / mb);

				i = 0;
			}
			o2[index] = o;
			i++;
			index++;
			
			if(index >= o2.length) {
				break;
			}
		}
	}
	
	public void read() {
		
	try (BufferedReader reader = new BufferedReader(new FileReader(
			new File(filename)))) {
		String line;

		while ((line = reader.readLine()) != null) {
		}
		}
	}

}
