import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;

import jp.co.bbreak.p1gp02.api0047.RoutineImpl;
import jp.co.bbreak.p1gp02.application.Result;

public class Trigger {

	public static void main(String[] args) {

		List<File> files = new ArrayList<>();
		files.add(new File(
				"C:\\Documents and Settings\\ykinos\\git\\p1\\p1\\src\\test\\resources\\p1gp2_log2.txt"));
		files.add(new File(
				"C:\\Documents and Settings\\ykinos\\git\\p1\\p1\\src\\test\\resources\\p1gp2_log1.txt"));
		files.add(new File(
				"C:\\Documents and Settings\\ykinos\\git\\p1\\p1\\src\\test\\resources\\p1gp2_log5.txt"));
		files.add(new File(
				"C:\\Documents and Settings\\ykinos\\git\\p1\\p1\\src\\test\\resources\\p1gp2_log3.txt"));
		files.add(new File(
				"C:\\Documents and Settings\\ykinos\\git\\p1\\p1\\src\\test\\resources\\p1gp2_log4.txt"));

		List<String> filters = new ArrayList<>();
		filters.add("/maeyes/");
		filters.add("/webmail/");

		RoutineImpl impl = new RoutineImpl();
		for (int i = 0; i < 10; i++) {
			try {
				StopWatch watch = new StopWatch();
				watch.start();
				List<Result> l = impl.analyze(files, filters);

				for (Result r : l) {
					Field[] fs = Result.class.getDeclaredFields();

					for (Field f : fs) {
						f.setAccessible(true);

						System.out.print(f.getName() + ":" + f.get(r) + ",");

					}
					System.out.println("");
				}
				watch.stop();
				System.out.println(watch.getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
