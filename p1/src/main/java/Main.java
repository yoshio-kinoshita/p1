import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;

import service.Service;
import service.ServiceImpl;
import entity.Result;

public class Main {

	public static void main(String args[]) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<String> logfiles = new ArrayList<String>();
		logfiles.add("src/test/resources/p1gp2_log1.txt");

		LogParser logParser = new LogParserImpl();
		
		List<Object[]> list = logParser.parser(logfiles,
				new ArrayList<String>());

		Service service = new ServiceImpl();
		List<Result> result = service.createResult(list);

		for (int i = 0; i < 20; i++) {
			System.out.println(result.get(i));
		}

		stopWatch.stop();
		System.out.println(stopWatch.getTime());
	}
}
