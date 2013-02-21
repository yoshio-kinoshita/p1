import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.co.bbreak.p1gp02.api0047.Job;

public class Trigger {

	public static void main(String[] args) {

		List<File> files = new ArrayList<>();
		files.add(new File("C:\\Documents and Settings\\ykinos\\git\\p1\\p1\\src\\test\\resources\\p1gp2_log1.txt"));
		files.add(new File("C:\\Documents and Settings\\ykinos\\git\\p1\\p1\\src\\test\\resources\\p1gp2_log2.txt"));
		files.add(new File("C:\\Documents and Settings\\ykinos\\git\\p1\\p1\\src\\test\\resources\\p1gp2_log3.txt"));
		files.add(new File("C:\\Documents and Settings\\ykinos\\git\\p1\\p1\\src\\test\\resources\\p1gp2_log4.txt"));
		files.add(new File("C:\\Documents and Settings\\ykinos\\git\\p1\\p1\\src\\test\\resources\\p1gp2_log5.txt"));
		
		List<String> filters = new ArrayList<>();
		filters.add("/webmail/");
		for (int i = 0; i < 1; i++) {
			Job.start(files, filters);
		}
	}

}
