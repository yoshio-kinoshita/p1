import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;

import service.Service;
import service.ServiceImpl;
import entity.AccessLog;
import entity.Result;

public class Main {

	public static void main(String args[]) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<String> logfiles = new ArrayList<String>();
		logfiles.add("src/test/resources/p1gp2_log1.txt");
		logfiles.add("src/test/resources/p1gp2_log2.txt");
		logfiles.add("src/test/resources/p1gp2_log3.txt");
		logfiles.add("src/test/resources/p1gp2_log4.txt");
		logfiles.add("src/test/resources/p1gp2_log5.txt");

		LogParser logParser = new LogParserImpl();

		List<AccessLog> list = logParser.parser(logfiles,
				new ArrayList<String>());

		System.out.println(list.size());
		Service service = new ServiceImpl();
		List<Result> result = service.createResult(list);

//		System.out.println(result.size());

		stopWatch.stop();
		System.out.println(stopWatch.getTime());
	}
}
