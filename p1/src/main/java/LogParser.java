import java.util.List;
import java.util.Map;

import entity.AccessLog;
import entity.Result;

public interface LogParser {

	public Map<String, Result> parser(List<String> logfiles,
			List<String> filters);

}
