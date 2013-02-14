import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LogParserImplTest {

	@Test
	public void parser() {

		List<String> logfiles = new ArrayList<>();
		logfiles.add("src/test/resources/p1gp2_log1.txt");
		logfiles.add("src/test/resources/p1gp2_log2.txt");
		logfiles.add("src/test/resources/p1gp2_log3.txt");
		logfiles.add("src/test/resources/p1gp2_log4.txt");
		logfiles.add("src/test/resources/p1gp2_log5.txt");

		List<String> filters = new ArrayList<>();
		filters.add("/maeyes");

		LogParser parser = new LogParserImpl();
		List<Object[]> log = parser.parser(logfiles, filters);

		System.out.println(log.size());
	}

}
