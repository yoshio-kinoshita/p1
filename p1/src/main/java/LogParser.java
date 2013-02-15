import java.util.List;

import entity.AccessLog;

public interface LogParser {

	public List<AccessLog> parser(List<String> logfiles, List<String> filters);

}
