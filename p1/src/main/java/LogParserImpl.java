import java.util.List;
import java.util.Map;

import util.P1Util;
import callable.LogReader;
import entity.Result;

public class LogParserImpl implements LogParser {

	@Override
	public Map<String, Result> parser(List<String> logfiles,
			List<String> filters) {

		logfiles = P1Util.sortLogfiles(logfiles);
		Map<String, Result> resultMap = LogReader.read(logfiles, filters);

		return resultMap;

	}

}
