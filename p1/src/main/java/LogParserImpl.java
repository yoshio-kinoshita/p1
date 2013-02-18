import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import util.P1Util;
import callable.LogReader;
import entity.Result;

public class LogParserImpl implements LogParser {

	@Override
	public Map<String, Result> parser(List<String> logfiles,
			List<String> filters) {

		StopWatch watch = new StopWatch();
		watch.start();
		
		logfiles = P1Util.sortLogfiles(logfiles);
		
		watch.stop();
		System.out.println("log-sort:" + watch.getTime());
		
		Map<String, Result> resultMap = LogReader.read(logfiles, filters);

		return resultMap;

	}

}
